FROM openjdk:8-jdk-alpine 
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

RUN ./mvnw dependency:go-offline -B

RUN ./mvnw package 

ADD ./target/ffkmda-0.0.1-SNAPSHOT.jar ffkmda-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ffkmda-0.0.1-SNAPSHOT.jar"]