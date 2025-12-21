# ==========================
# Stage 1: Build the JAR
# ==========================
FROM maven:3.9.2-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy Maven configuration
COPY pom.xml .

# Copy source code
COPY src ./src

# Build the JAR (skip tests for faster build)
RUN mvn clean package -DskipTests

# ==========================
# Stage 2: Run the JAR
# ==========================
FROM eclipse-temurin:21-jdk-alpine


# Set working directory
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port (optional, but good practice)
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
