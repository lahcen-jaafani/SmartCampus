FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/SmartCampus-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8088
ENTRYPOINT ["java","-jar","app.jar"]
