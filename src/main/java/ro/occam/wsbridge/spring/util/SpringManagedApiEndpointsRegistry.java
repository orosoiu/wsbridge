package ro.occam.wsbridge.spring.util;

import ro.occam.wsbridge.spring.ApiRegistryBuilderBeanPostProcessor;
import ro.occam.wsbridge.spring.servlet.ApiDispatcherServlet;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * This class holds a registry of API endpoints managed by Spring.
 * {@link ApiRegistryBuilderBeanPostProcessor} builds this registry during Spring context initialization
 * {@link ApiDispatcherServlet} uses this registry to dispatch API calls to the appropriate manager
 */
public class SpringManagedApiEndpointsRegistry {

    private static final Set<Pattern> endpointUrls = new HashSet<>();

    /**
     * Add an endpoint URL to the registry
     */
    public static void addEndpointUrl(String url) {
        // replace Spring specific path parameters in "{param-name}" format with a generic matcher
        String urlRegex = url.replaceAll("\\{[^}]+}", "[^/]+");
        Pattern urlPattern = Pattern.compile(urlRegex);
        endpointUrls.add(urlPattern);
    }

    /**
     * Check whether an URL is present in the registry
     */
    public static boolean contains(String candidate) {
        return endpointUrls.stream().anyMatch(pattern -> pattern.matcher(candidate).matches());
    }
}
