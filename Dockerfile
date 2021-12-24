FROM openjdk:16

WORKDIR /app

ARG JAR_FILE

COPY target/${JAR_FILE} /app/bank-api.jar

EXPOSE 8080

CMD ["java", "-jar", "bank-api.jar"]