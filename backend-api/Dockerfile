FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 8081

ENTRYPOINT ["/wait-for-it.sh", "postgres:5432", "--", "java", "-jar", "app.jar"]
