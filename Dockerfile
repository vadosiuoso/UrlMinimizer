# Use the official OpenJDK image for Java 17
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app


# Run the application
ENTRYPOINT ["java", "-jar", "build/libs/demo-0.0.1-SNAPSHOT.jar"]
