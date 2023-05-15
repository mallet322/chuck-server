FROM openjdk:11.0.9-jre
ADD /target/chuck-server-1.0.jar chuck.jar
ADD ssl/chucknorris.io.crt chuck.crt
ADD ssl/oauth.google.crt google.crt
RUN $JAVA_HOME/bin/keytool -import -file ./chuck.crt -alias chuck -keystore $JAVA_HOME/lib/security/cacerts -trustcacerts -storepass changeit -noprompt
RUN $JAVA_HOME/bin/keytool -import -file ./google.crt -alias google -keystore $JAVA_HOME/lib/security/cacerts -trustcacerts -storepass changeit -noprompt
ENTRYPOINT ["java", "-jar", "chuck.jar"]