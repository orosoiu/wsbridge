package ro.occam.wsbridge.spring.servlet;

import ro.occam.wsbridge.spring.util.SpringManagedApiEndpointsRegistry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A simple servlet that checks whether the current API URL is managed by Spring or CXF and forwards the call accordingly
 */
public class ApiDispatcherServlet extends HttpServlet {

    private final String SPRING_MANAGED_CONTEXT_PATH_PREFIX = "/spring-rest-api";
    private final String CXF_MANAGED_CONTEXT_PATH_PREFIX = "/cxf-rest-api";

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String canonicalUrl = addManagerContextPath(request.getRequestURI());
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(canonicalUrl);
        dispatcher.forward(request, response);
    }

    private String addManagerContextPath(String requestUrl) {
        if (SpringManagedApiEndpointsRegistry.contains(requestUrl)) {
            return SPRING_MANAGED_CONTEXT_PATH_PREFIX + requestUrl;
        }
        return CXF_MANAGED_CONTEXT_PATH_PREFIX + requestUrl;
    }
}
