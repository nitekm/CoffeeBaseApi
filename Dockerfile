FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} coffeebaseapi-1.0.jar
ENTRYPOINT ["java", "-jar", "coffeebaseapi-1.0.jar"]