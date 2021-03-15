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
App has auditing like @PrePersist and @PreUpdate methods.

## Technology
* Java
* Spring Boot
* Spring JPA
* Flyway
* H2 Database
* MySQL

