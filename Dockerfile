# Step 1: Use an official Java runtime as a base
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file from target folder into the container
COPY target/*.jar app.jar

# Step 4: Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
