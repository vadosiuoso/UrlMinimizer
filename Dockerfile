# Use the official OpenJDK image for Java 17
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle executable to the image
COPY gradlew .
COPY gradle gradle

# Copy the build.gradle files and the Gradle settings file
COPY build.gradle settings.gradle ./

# Copy the source code
COPY src src

# Grant execution rights on the gradlew
RUN chmod +x ./gradlew

# Install required utilities
RUN apt-get update && apt-get install -y findutils && apt-get clean

# Build the application
RUN ./gradlew build -x test

# Run the application
ENTRYPOINT ["java", "-jar", "build/libs/demo-0.0.1-SNAPSHOT.jar"]
