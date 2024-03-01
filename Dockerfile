FROM openjdk:17
MAINTAINER Honnur
COPY target/sbjwtapp.jar sbjwtapp.jar
ENTRYPOINT [ "java", "-jar","/sbjwtapp.jar" ]