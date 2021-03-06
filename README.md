# blue-exam

## This project provides
* REST interface
* Simple Domain model based on Domain-Driven Design 
* JPA repositories persisting aggregate roots

## Requisites
* Java 7
* Maven 3

## Usage
#### Run spring-boot
```
$ git clone https://github.com/dembol/blue-exam.git
$ cd blue-exam/core
$ mvn clean spring-boot:run
```

#### Wait for fully prepared Spring context
```
jetty-8.1.14.v20131031
Started SelectChannelConnector@0.0.0.0:8000
```
#### Next you can execute REST methods
  
* add request (this method returns new ID)
  ```
  $ curl -X POST "http://127.0.0.1:8000/blue/request" -H"title: some title" -H"description: some description"
  ```
 
* get request by ID
  ```
  $ curl -X GET "http://127.0.0.1:8000/blue/request/ID"
  {"id":13,"title":"some title","description":"some description","reason":null,"state":"CREATED","stateHistory":[]}
  ```
* change request's state
  ```
  $ curl -X PUT "http://127.0.0.1:8000/blue/request/ID/verify"
  $ curl -X PUT "http://127.0.0.1:8000/blue/request/ID/accept"
  $ curl -X PUT "http://127.0.0.1:8000/blue/request/ID/publish"
  $ curl -X PUT "http://127.0.0.1:8000/blue/request/ID/delete" -H"reason: some reason"
  $ curl -X PUT "http://127.0.0.1:8000/blue/request/ID/reject" -H"reason: some reason"
  ```

* change request's content (only for CREATED and VERIFIED states)
  ```
  $ curl -X PUT "http://127.0.0.1:8000/blue/request/ID/content" -H"title: title2" -H"description: description2"
  ```

* find some requests
  ```
  $ curl -X GET "http://127.0.0.1:8000/blue/request
  $ curl -X GET "http://127.0.0.1:8000/blue/request?title=title2&page=0"
  $ curl -X GET "http://127.0.0.1:8000/blue/request?page=1"
  $ curl -X GET "http://127.0.0.1:8000/blue/request?state=CREATED&page=0"
  $ curl -X GET "http://127.0.0.1:8000/blue/request?state=CREATED&title=title123&page=0"
  $ curl -X GET "http://127.0.0.1:8000/blue/request?state=CREATED&title=title123&description=ala123&page=0"
  ```

