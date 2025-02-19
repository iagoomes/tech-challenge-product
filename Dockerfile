# Use the official Maven image with Eclipse Temurin JDK 21 to build the application
FROM maven:3.9.4-eclipse-temurin-21 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the project into the container
COPY . .

# Run 'mvn generate-sources' to generate code from OpenAPI specifications
RUN mvn generate-sources

# Build the application artifact
RUN mvn clean package -DskipTests

# Use a minimal Eclipse Temurin JDK 21 image to run the application
FROM eclipse-temurin:21-jre

# Set the working directory
WORKDIR /app

# Copy the JAR generated in the build stage
COPY --from=builder /app/target/products-0.0.1-SNAPSHOT.jar /app/products.jar

# Create and mount a volume for the CSV files
VOLUME /app/batch-files

# Set the environment variable to point to the volume
ENV DIR_FILE=/app/batch-files

# Expose ports 8080 and 8081
EXPOSE 8080 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "products.jar"]

# Set the image name
LABEL name="products"
LABEL authors="iagoomes"