# Use a minimal base image with JDK 17
FROM openjdk:17-jdk-alpine

LABEL authors="imranbhat"

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/intent-analyzer-0.0.1-SNAPSHOT.jar /app/intent-analyzer.jar

# Expose the port your application will run on
EXPOSE 8031

# Command to run the application
CMD ["java", "-jar", "/app/intent-analyzer.jar"]