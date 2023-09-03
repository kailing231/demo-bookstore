FROM openjdk:17-jdk-alpine as buildstage
COPY target/*.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]