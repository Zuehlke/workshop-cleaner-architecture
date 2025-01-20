# Colossus Backend

## Description
The Colossus-Backend is a SpringBoot-service which provides REST-endpoints for its clients - like for example for a potential JS-based application running in a browser.
In order to be as easily testable as possible, the service does not need any special infrastructure services. The underyling database being used is defined as a H2-in-memory database.

## Technology-Stack
* Java 17
* SpringBoot 3.4.1
* With extensions: data-jpa, jackson/json
* H2 in-memory database
* Gradle 8.11


