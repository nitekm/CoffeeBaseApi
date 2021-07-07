# CoffeeBaseAPI

## Project info
CoffeeBaseAPI is a REST API for Android app CoffeeBase, it uses CRUD operations to serve as backend of the app.
It enables to store coffee information in database and handles user requests made from the Android app such as:
* Create new coffee
* Retrieve all coffees and information about them
* Add coffee to favourites
* Delete coffee
* Edit coffee
* Assign coffees to certain groups

## Application Preview
User can create coffee with information like name, origin, roaster, rate coffee and provide imageUrl for pretty display 
in Android app. There is also option to mark coffee as favourite.
User can create various coffee groups and assign coffees to them to keep it all organised.
Coffee can belong to many groups (but only one of given type) and every group can have multiple coffees.

For client side of this project please see: <a href="https://github.com/nitekm/CoffeeBase">CoffeeBase Android App</a>

## Application Overview
### Main Technology Stack:
* Java 11
* Spring Boot 2.4.3
* MySql 8.0

### Dependencies
* Spring Web
* Validation
* Spring Data JPA
* H2 Database
* MySql Driver
* Flyway Migration

### Data Persistence
App uses MySql hosted on local PC in prod env and H2 database in local env 
for tests and new feature development purposes. Connection between app and database achieved 
by Hibernate and JPA which I find most convenient way to do it.
On application start app updates database schema if needed.  
Flyway does persist our database schemas in case of any emergency and help us keep consistency in both local and prod
environments.

#### Database
Many-to-Many relationship with 2 main tables: coffees and coffee_groups, also 3rd table (coffee_coffee_group) to provide 
connection between 2 main ones.

### Auditing
I've made an audit class marked as @Embeddable to allow JPA include audits in database to track when data has been
created and updated. These fields have been marked with @PrePersist(createdOn) and @PreUpdate(updatedOn).

### Logging
Logging done via interceptors. LoggerInterceptor intercepts every call to API and logs it, and it's status on console
before execution (preHandle) and after completion (completed)

### Tests
Methods in CoffeeService have been tested using JUnit5 and Mockito.  
Integration tests in safe environment tested various api calls and data persistence. 

## API Requests
Below are all requests that API handles. API returns JSON and different HTTP codes with adequate messages depending on request success

#### Coffee:
* GET: /coffees - returns all coffees
* GET: /coffees/{id} - returns single coffee
* POST: /coffees - adds coffee to database with following data:
    - name (String, required)
    - origin (String, optional)
    - roaster (String, optional)
    - rating (int, optional)
    - imageUrl (String, optional)
    
* PUT: /coffees/{id} - updates existing coffee
* DELETE: /coffees/{id} - deletes coffee based on id
* PATCH : /coffees/{id} - changes "favourite" field value (true/false) on given id
* PATCH: /coffees/{id}/{groupName} - adds coffee to group "groupName"

#### Coffee Group:
* GET: /groups - returns all groups
* GET: /groups/{id} - returns single group
* POST: /groups - adds new group with following data:
    - name (String, required),
    - groupType (enum: METHOD, ORIGIN, ROASTER; optional)
    
* DELETE: /groups/{id} - deletes group based on id



