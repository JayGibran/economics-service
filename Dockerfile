FROM openjdk:8u181-jdk-alpine3.8

MAINTAINER Jay Gibran "jaygibran@gmail.com"

EXPOSE 8080

WORKDIR /usr/local/bin

COPY ./target/economics-app-0.0.1-SNAPSHOT.jar webapp.jar

CMD ["java", "-jar", "webapp.jar"]


