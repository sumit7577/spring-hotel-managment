# Use Maven base image for the build stage
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
# Run Maven build, skipping tests
RUN mvn clean package -DskipTests

# Use OpenJDK image for the runtime stage
FROM openjdk:17-jdk-alpine
WORKDIR /app
# Copy the built JAR from the build stage
COPY --from=build /app/target/jorvik-1.0.jar /app/
EXPOSE 8080
CMD ["java", "-jar", "jorvik-1.0.jar"]