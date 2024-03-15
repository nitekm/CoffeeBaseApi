# CoffeeBaseAPI

## Project info
CoffeeBaseAPI is a REST API for Android app CoffeeBase.
It is responsible for all the application logic.
The app's main functionalities include saving all possible information and coffees with a photo, tagging them, creating custom brewing methods, etc.

## Application Preview
The app's main functionalities include saving all possible information and coffees with a photo, tagging them, creating custom brewing methods, etc.
The user can also add coffees to favorites and their browsing is facilitated by searching, appropriate sorting and filters. 
The application also has clear error handling and is secured with OAuth2, using Google tokens for authentication. 
The whole thing has the ability to be built from a docker image, with its own data volume. For easy deployment in production, it uses the Maven plugin for Google Cloud Platform, which automatically tests, builds and exposes a new version of the application in one command with the appropriate Maven profile.

For client side of this project please see: <a href="https://github.com/nitekm/CoffeeBase">CoffeeBase Android App</a> OR <a href="https://github.com/nitekm/CoffeeBaseNew">New Android App written in Kotlin with Compose!</a>

## Application Overview
### Technology Stack:
#### General:
 * Java 17
 * Spring Boot 3.0.0
 * AOP
 * Lombok
 * Mapstruct
#### Data layer:
 * Hibernate
 * MySql 8.0
 * Jakarta Validation
#### Security:
 * Spring Security
 * OAuth2
 * JWT
#### Monitoring:
 * Actuator
 * Prometheus
 * Grafana
#### Testing:
 * Junit5
 * Mockito
 * H2 Database
#### Development:
 * Docker

## Details
### Data Layer
The data layer is based on MySQL connected to the application using Hibernate. Used both by default and using native queries and JPQL/HQL queries to get a little better query optimization.
Tables has appropriate constraints to not allow user to do nonsense.
Also basic auditing is done by automatically saved information like who created/updated given record as well as time of creation or last update.

### Monitoring
App has setup actuator with prometheus. It's configured to be able to display metrics via tool like Grafana.

### Testing
App has test coverage both unit and integration for business logic and service layer to data layer communication.
For testing purposes H2 database is used.

### Security
Security is handled via Google API for authentication Google users. When user first login app checks with Google if this is valid person with Google Account. 
After that app generates JWT token with all informations needed which is then used in every request to authenticate author who is making given call.

### AOP
AOP is used for things like:
Logging - performs log outputs on every Controller call with result and time of execution
Security - app checks specified calls to make sure no someone else's data will be exposed to unauthorized user.
Auditing - adding auditing fields like createdBy.

### Dev environment
Dev environment is setup by using only one docker command. Dockerfile is declared to build application and create image.
Docker compose is configured to launch both app image and database image with it's own volume in single container.

### Prod environment
For production environment Google Cloud Platform is used. Thanks to maven profile and gcp plugin app can be tested, built and deployed in single command.
It uses GCPs: App Engine, Cloud SQL, Logging, Monitoring (with allerts) and cost control.




