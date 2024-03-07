FROM eclipse-temurin:21-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the Maven executable to the container
COPY mvnw .
COPY .mvn .mvn

# Copy the project object model file
COPY pom.xml .

# Build all the dependencies in preparation to go offline. This is a separate step
# so the dependencies will be cached unless the pom.xml file has changed.
RUN ./mvnw dependency:go-offline -B

# Package the application
COPY src src

RUN ./mvnw compile -DskipTests
RUN ./mvnw package -DskipTests

# Copy the JAR file
COPY target/license_plate_api-1.0.jar target/license_plate_api-1.0.jar

# Expose the port the app runs on
EXPOSE 8030

# Run the jar file
ENTRYPOINT ["java", "-jar", "target/license_plate_api-1.0.jar"]
