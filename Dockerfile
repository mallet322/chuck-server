FROM openjdk:11.0.9-jre
ADD /target/chuck-server-1.0.jar chuck.jar
ENTRYPOINT ["java", "-jar", "chuck.jar"]