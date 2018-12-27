FROM java:8-jre-alpine
COPY target/duck-ledger-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
EXPOSE 8080/tcp
CMD java -jar app.jar
