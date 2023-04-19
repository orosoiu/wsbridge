# Web Service Bridge

This project is a PoC showing RESTful Apache CXF and Spring MVC web services running side-by-side in the same container.

## Why?

Software is not static, but an ever changing construct of different components and frameworks. New parts are constantly being added, old parts are being re-written or discarded. Sometimes entire frameworks are being replaced, whether for cost, performance, security or other considerations.

Many times - and particularly for enterprise and other very large applications - replacing a framework with another proves to be a very delicate, complex and time-consuming task. To enable a smooth migration, both frameworks need to be kept in operation throughout the duration of the migration in a way that is transparent to both end clients and developers.

## How it works?

The PoC architecture is very simple: since we cannot map both frameworks on the same client context path, each one is mapped on a different internal context path and the external API root context is mapped on a dispatcher servlet which forwards the API request to the correct implementation's internal path.

![image](https://user-images.githubusercontent.com/3442410/233149665-086165e7-18f6-46fb-b2fe-0c7161b5cc8a.png)

The two main components enabling this process are:

- The bootstrap component `ApiRegistryBuilderBeanPostProcessor` is an implementation of Spring's `BeanPostProcessor` which runs for every bean registered with Spring's context and checks whether that bean is a Spring REST service implementation (based on the existence of `RestController` annotation) in which case it will register it with the static `SpringManagedApiEndpointsRegistry` dictionary using the service URI as a key

- The runtime component `ApiDispatcherServlet` is a standard servlet implementation mapped on the external API context path which acts as a dispatcher on all API calls; it checks whether the current request is served by Spring by checking the request URI against `SpringManagedApiEndpointsRegistry` and forwards the request internally to the Spring/Apache CXF context path.

## How to build and run the application?

The project is a standard Spring Boot application using Gradle as a build manager. Run the standard steps in the project root directory in order to build an run it:
```shell
$ ./gradlew clean build
$ java -jar build/libs/wsbridge-0.0.1-SNAPSHOT.jar
```

The services are exposed on the default Tomcat port:
```shell
$ curl -H "Accept: application/json" http://localhost:8080/api/cxf/status
Running
$ curl -H "Accept: application/json" http://localhost:8080/api/jobs
...
$ curl -H "Accept: application/json" http://localhost:8080/api/spring/status
Running
$ curl -H "Accept: application/json" http://localhost:8080/api/employees
...
```
