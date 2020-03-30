# The Spring-data-mongodb-csfle Project

## About

Describe your project here.

## Technical Stack:

- Java 11+
- Maven 3.6+
- Spring boot 2.2.5.RELEASE
    - Spring Boot Actuator for exposing management endopoints
- Spring cloud Hoxton.SR3
    - Spring Cloud Sleuth for distributed tracing
- Lombok abstraction
- Mapstruct for bean mapping
- Open API documentation (available at /swagger-ui.html)
- REST API model validation 
- Cucumber and Spring Boot test for test
- Logback for logging
    - Log patterns for local and cloud SPRING profiles includes Sleuth headers

## Installation
This application is configured with two SPRING profiles:
- "local": For local dev environments.
- "cloud": For dockerized environments, where application properties are set by ENV variables.

Test on the browser via SWAGGER
-------------------

```sh
http://localhost:8080/swagger-ui.html
```
