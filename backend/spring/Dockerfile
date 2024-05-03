FROM eclipse-temurin:21-jdk-alpine as build

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# create storage folder to store license plate images
RUN mkdir -p /workspace/app/storage

RUN chmod -R 777 /workspace/app/storage

RUN addgroup -S spring && adduser -S spring -G spring

user spring:spring

# Expose the port the app runs on
EXPOSE 8030

ENTRYPOINT ["java","-jar", "target/license_plate_api-1.0.jar"]