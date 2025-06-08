# Use an appropriate base image with JDK 17
FROM eclipse-temurin:17-jdk-jammy

# Set a working directory
WORKDIR /app

# Copy the executable JAR file from the build output
# The JAR file name might need adjustment if it's different
COPY build/libs/product-api-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
