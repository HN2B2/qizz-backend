# Base image
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy only the Maven configuration files to leverage Docker layer caching
COPY pom.xml .
COPY .mvn .mvn

# Download the Maven dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the project files
COPY src src

# Build the application
RUN mvn package -DskipTests

# Expose the port the app runs on
EXPOSE 8080

# Run the application using Maven
CMD ["mvn", "spring-boot:run"]