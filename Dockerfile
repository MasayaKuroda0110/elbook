FROM maven:3.9.8-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean package

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/ELBOOK.jar"]
