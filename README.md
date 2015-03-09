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
  
### add request (returns new request ID)
  ```
  $ curl -X POST "http://127.0.0.1:8000/blue/request" -H"title: some title" -H"description: some description"
  ```
 
### get request
  ```
  $ curl -X GET "http://127.0.0.1:8000/blue/request/ID"
  {"id":13,"title":"some title","description":"some description","reason":null,"state":"CREATED","stateHistory":[]}
  ```
### change state
  ```
  $ curl -X PUT "http://127.0.0.1:8000/blue/request/ID/verify"
  $ curl -X PUT "http://127.0.0.1:8000/blue/request/ID/accept"
  $ curl -X PUT "http://127.0.0.1:8000/blue/request/ID/publish"
  $ curl -X PUT "http://127.0.0.1:8000/blue/request/ID/delete" -H"reason: some reason"
  $ curl -X PUT "http://127.0.0.1:8000/blue/request/ID/reject" -H"reason: some reason"
  ```

### change request content (only for CREATED and VERIFIED states)
  ```
  $ curl -X PUT "http://127.0.0.1:8000/blue/request/ID/content" -H"title: title2" -H"description: description2"
  ```

### find requests
  ```
  $ curl -X GET "http://127.0.0.1:8000/blue/request?title=title2&page=0"
  $ curl -X GET "http://127.0.0.1:8000/blue/request?page=1"
  $ curl -X GET "http://127.0.0.1:8000/blue/request?state=CREATED&page=0"
  $ curl -X GET "http://127.0.0.1:8000/blue/request?state=CREATED&title=title123&page=0"
  ```

