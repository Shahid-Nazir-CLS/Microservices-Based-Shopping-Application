FROM eclipse-temurin:latest
EXPOSE 8080
COPY target/shoppy-0.0.1-SNAPSHOT.war app.war
ENTRYPOINT ["java", "-jar", "app.war"]