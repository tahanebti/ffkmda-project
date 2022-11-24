# Build stage
FROM maven:3.6.3-jdk-8-slim AS build
COPY src ./src
COPY pom.xml .
RUN mvn -f ./pom.xml clean package




# Package stage
FROM openjdk:8-jdk-alpine
COPY --from=build ./target/ffkmda-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9007
ENTRYPOINT ["java","-jar","./app.jar"]