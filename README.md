# CoffeeBaseAPI

## Project info
CoffeeBaseAPI is a REST API for Android app CoffeeBase, it uses CRUD operations to serve as backend of the app.
It enables to store coffee information in database and handles user requests made from the Android app such as:
* Create new coffee
* Retrieve all coffees and information about it
* Add coffee to favourites
* Delete coffee
* Edit coffee
* Assign coffees to certain groups

Data persistence is done by databases: H2 (local - test DB) and MySQL (production) and JPA. Flyway migrations are present.
App has auditing like @PrePersist and @PreUpdate methods and loggers with Interceptors.
There is also a little of Spring events used in Warmup class.
Test are made in JUnit5 and MockMvc.

## Technology
* Java
* Spring Boot
* JPA
* Flyway
* H2 Database
* MySQL
* Tests (unit tests with JUnit5 and integration tests)



