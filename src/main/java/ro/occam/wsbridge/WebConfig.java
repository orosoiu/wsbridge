package ro.occam.wsbridge;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.occam.wsbridge.spring.ApiRegistryBuilderBeanPostProcessor;
import ro.occam.wsbridge.spring.servlet.ApiDispatcherServlet;

import javax.servlet.http.HttpServlet;

@Configuration
public class WebConfig {

    @Bean
    public ApiRegistryBuilderBeanPostProcessor apiRegistryBuilder() {
        return new ApiRegistryBuilderBeanPostProcessor();
    }

    @Bean
    public ServletRegistrationBean<HttpServlet> apiDispatcherServlet() {
        ServletRegistrationBean<HttpServlet> apiDispatcherRegistrationBean = new ServletRegistrationBean<>();
        apiDispatcherRegistrationBean.setServlet(new ApiDispatcherServlet());
        apiDispatcherRegistrationBean.addUrlMappings("/api/*");
        apiDispatcherRegistrationBean.setLoadOnStartup(1);
        return apiDispatcherRegistrationBean;
    }
}
