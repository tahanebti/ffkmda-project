FROM openjdk:8-jdk-alpine

EXPOSE 9007
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Copy the project source
COPY ./src ./src
COPY ./pom.xml ./pom.xml

RUN chmod 755 /app/mvnw

#RUN ./mvnw dependency:go-offline -B

RUN ./mvnw clean install
RUN ./mvnw spring-boot:run -DskipTests

RUN ls -al
ENTRYPOINT ["java","-jar","target/ffkmda-0.0.1-SNAPSHOT.jar"]

HEALTHCHECK --interval=1m --timeout=3s CMD wget -q -T 3 -s http://localhost:9007/actuator/health/ || exit 1
    