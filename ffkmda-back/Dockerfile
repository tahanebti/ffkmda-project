FROM openjdk:8-jdk-alpine as compile


# Build the jar using maven 
#RUN apk add maven
WORKDIR /app
#COPY . /app/
#RUN mvn -f pom.xml clean package -DskipTests

#FROM openjdk:8-jre-alpine

# Copy the packaged jar app file to a smaller JRE base image
#COPY --from=compile "/target/ffkmda-0.0.1-SNAPSHOT.jar" app.jar


COPY ./target/ffkmda-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]