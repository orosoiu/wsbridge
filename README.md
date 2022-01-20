# Web Service Bridge

This project shows PoC on running RESTful web services using Apache CXF and Spring MVC at the same time in the same application

## Why?

TBD

## How it works?

TBD

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