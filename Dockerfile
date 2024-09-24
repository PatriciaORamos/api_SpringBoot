FROM openjdk:17-jdk-alpine
RUN mkdir /app
WORKDIR /app
COPY target/*.jar /app/springboot.jar
CMD ["java", "-jar", "/app/springboot.jar"]
