#Maven Build
FROM maven:3.8.3-openjdk-8-slim AS builder
COPY pom.xml /app/
COPY src /app/src
RUN --mount=type=cache,target=/root/.m2 mvn -f /app/pom.xml clean package -DskipTests


ADD ./target/ffkmda-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9007
ENTRYPOINT ["java", "-jar", "app.jar"]
