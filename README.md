# CoffeeBaseAPI

## Project info
CoffeeBaseAPI is a REST API for Android app CoffeeBase, it uses CRUD operations to serve as backend of the app.
It enables to store coffee information in database and handles user requests made from the Android app such as:
* Create new coffee in database
* Retrieve all coffees and informations
* Add coffee to favourites
* Delete coffee
* Edit coffee

Data persistance is done by h2 and JPA. Flyway migrations are present.
App has some auditing like createdOn and updatedOn methods.

## Technology
* Java
* Spring Boot
* Spring JPA
* Flyway
* H2 Database

