# Use an official Gradle image as a build stage
FROM gradle:8.4.0-jdk17 as builder

# Set the working directory
WORKDIR /app


# Copy only the build.gradle and settings.gradle files to cache dependencies
COPY build.gradle settings.gradle /app/

# Copy the source code into the container
COPY src /app/src

# Build the application using Gradle
RUN gradle build --no-daemon

# Create a minimal runtime image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the Gradle build stage to the runtime image
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# Expose the port that your Spring Boot application will run on (change this to the correct port)
EXPOSE 8080

# Define the command to run your Spring Boot application
CMD ["sh", "-c", "java -jar app.jar"]
