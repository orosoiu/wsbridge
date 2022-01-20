package ro.occam.wsbridge.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ro.occam.wsbridge.spring.util.SpringManagedApiEndpointsRegistry;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class ApiRegistryBuilderBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware, Ordered {

    private final static String[] EMPTY_PATH_ARRAY = new String[0];

    private BeanFactory beanFactory;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        if (!isSpringManaged(clazz)) {
            // nothing to do
            return bean;
        }

        String baseUrl = getNormalizedBaseUrlForClass(clazz);
        Arrays.stream(clazz.getMethods()).filter(this::isApiMethod).forEach(method -> {
            String[] relativeUrls = extractRelativeUrls(method);
            if (relativeUrls.length == 0) {
                // if no relative URLs are specified on the method, we use a default empty URL that will map on the base URL
                relativeUrls = new String[] {""};
            }
            Arrays.stream(relativeUrls)
                    .map(relativeUrl -> sanitizeUrl(baseUrl, relativeUrl))
                    .forEach(SpringManagedApiEndpointsRegistry::addEndpointUrl);
        });
        return bean;
    }

    private boolean isSpringManaged(Class<?> clazz) {
        return clazz.isAnnotationPresent(RestController.class);
    }

    private boolean isApiMethod(Method method) {
        return Stream.of(RequestMapping.class, GetMapping.class, PostMapping.class, PutMapping.class, DeleteMapping.class, PatchMapping.class)
                .anyMatch(method::isAnnotationPresent);
    }

    /**
     * Returns the base API path of a class managed by Spring MVC, as specified in the {@link RequestMapping} annotation
     */
    private String getNormalizedBaseUrlForClass(Class<?> clazz) {
        String[] paths = Stream.of(
                Optional.ofNullable(clazz.getAnnotation(RequestMapping.class)).map(RequestMapping::path).orElse(EMPTY_PATH_ARRAY),
                Optional.ofNullable(clazz.getAnnotation(RequestMapping.class)).map(RequestMapping::value).orElse(EMPTY_PATH_ARRAY)
        ).flatMap(Stream::of).toArray(String[]::new);
        return paths.length > 0 ? StringUtils.trimTrailingCharacter(paths[0], '/') : "";
    }

    /**
     * Return a list of relative URLs as specified in any of the Spring REST annotations, if present
     */
    private String[] extractRelativeUrls(Method method) {
        return Stream.of(
                Optional.ofNullable(method.getAnnotation(RequestMapping.class)).map(RequestMapping::path).orElse(EMPTY_PATH_ARRAY),
                Optional.ofNullable(method.getAnnotation(RequestMapping.class)).map(RequestMapping::value).orElse(EMPTY_PATH_ARRAY),
                Optional.ofNullable(method.getAnnotation(GetMapping.class)).map(GetMapping::path).orElse(EMPTY_PATH_ARRAY),
                Optional.ofNullable(method.getAnnotation(GetMapping.class)).map(GetMapping::value).orElse(EMPTY_PATH_ARRAY),
                Optional.ofNullable(method.getAnnotation(PostMapping.class)).map(PostMapping::path).orElse(EMPTY_PATH_ARRAY),
                Optional.ofNullable(method.getAnnotation(PostMapping.class)).map(PostMapping::value).orElse(EMPTY_PATH_ARRAY),
                Optional.ofNullable(method.getAnnotation(PutMapping.class)).map(PutMapping::path).orElse(EMPTY_PATH_ARRAY),
                Optional.ofNullable(method.getAnnotation(PutMapping.class)).map(PutMapping::value).orElse(EMPTY_PATH_ARRAY),
                Optional.ofNullable(method.getAnnotation(DeleteMapping.class)).map(DeleteMapping::path).orElse(EMPTY_PATH_ARRAY),
                Optional.ofNullable(method.getAnnotation(DeleteMapping.class)).map(DeleteMapping::value).orElse(EMPTY_PATH_ARRAY),
                Optional.ofNullable(method.getAnnotation(PatchMapping.class)).map(PatchMapping::path).orElse(EMPTY_PATH_ARRAY),
                Optional.ofNullable(method.getAnnotation(PatchMapping.class)).map(PatchMapping::value).orElse(EMPTY_PATH_ARRAY)
        ).flatMap(Stream::of).toArray(String[]::new);
    }

    private String sanitizeUrl(String baseUrl, String relativeUrl) {
        return StringUtils.hasLength(relativeUrl)
                ? String.join("(\\/)+", baseUrl, relativeUrl.replaceFirst("/+", ""))
                : baseUrl;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
