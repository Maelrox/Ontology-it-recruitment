# Use the official OpenJDK 21 image as the base image
FROM openjdk:21-jdk as build

VOLUME /tmp
ARG JAR_FILE
COPY it-ontology.rdf it-ontology.rdf
COPY build/libs/ontologia-it-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]