# blue-exam

## The project provides
* REST interface
* Domain model based on Domain-Driven Design 
* JPA repositories for domain persistence

## Requisites
* Java 7
* Maven 3

## Usage
* Run spring-boot
```
cd blue/core
mvn clean spring-boot:run
```

* Wait for fully prepared Spring context
```
jetty-8.1.14.v20131031
Started SelectChannelConnector@0.0.0.0:8000
```
* Next you can execute interface's methods
  * add request
  ```
  curl -X POST "http://127.0.0.1:8000/blue/request" -H"title: some title" -H"description: some description"
  ```


