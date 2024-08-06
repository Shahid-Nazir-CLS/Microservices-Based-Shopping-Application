FROM eclipse-temurin:latest
EXPOSE 8082
COPY target/customer-0.0.1-SNAPSHOT.war app.war
ENTRYPOINT ["java", "-jar", "app.war"]