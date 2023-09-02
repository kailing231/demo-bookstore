FROM openjdk:17-jdk-alpine as buildstage
COPY target/*.jar java-app-docker.jar

FROM openjdk:17
COPY --from=buildstage java-app-docker.jar .
ENTRYPOINT [ "java", "-jar", "java-app-docker.jar" ]