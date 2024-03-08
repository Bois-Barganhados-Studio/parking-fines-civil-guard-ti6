FROM eclipse-temurin:21-jdk-alpine as build

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Expose the port the app runs on
EXPOSE 8030

ENTRYPOINT ["java","-jar", "target/license_plate_api-1.0.jar"]