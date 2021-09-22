# Simple API

This is a simple REST API for technical test.
It is made using Java 11 and the Spring framework.

## How to run
You can run with maven `mvn spring-boot:run`, or by compiling the application with `mvn package` and then
run it with `java -jar target/technicaltest-0.0.1-SNAPSHOT.jar`.

The application will be listening on port *8080*, but you can change it in the first line of *application.properties* file.

## Documentation
The API documentation is available at [http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config).
A postman collection with few examples of api call is available in the *postman* directory.


## Logging
This application uses the Spring AOP module for adding logs information. You can disable it in *application.properties*.