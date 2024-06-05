# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

ENV IMG_PATH = /imgs

# Set the working directory inside the container
WORKDIR /app


# Expose the port your application runs on
EXPOSE 8080

RUN mkdir -p /imgs

ADD ./target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

# Copy the jar file from your local machine to the container
# COPY target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
