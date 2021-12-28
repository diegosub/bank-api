#
# Build stage
#
FROM maven:3.8.3-openjdk-16 AS build
WORKDIR /app
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

#
# Package stage
#
FROM openjdk:16
COPY --from=build /app/target/bank-api.jar /usr/local/lib/bank-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/bank-api.jar"]