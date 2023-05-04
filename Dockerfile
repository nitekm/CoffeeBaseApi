FROM eclipse-temurin:17-jdk-jammy
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} coffeebaseapi-1.4.jar
ENTRYPOINT ["java", "-jar", "coffeebaseapi-1.4.jar"]
