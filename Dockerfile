FROM openjdk:17-jre-slim

WORKDIR /app

COPY target/myapp.jar /app/myapp.jar

ENTRYPOINT ["java", "-jar", "myapp.jar"]

EXPOSE 8080