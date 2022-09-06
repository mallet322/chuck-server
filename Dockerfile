FROM openjdk:11.0.9-jre
WORKDIR chuck-server
ADD target/chuck-server-1.0.jar chuck-server.jar
ENTRYPOINT java -jar chuck-server.jar