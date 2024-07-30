FROM openjdk:17-alpine
EXPOSE 8080
ADD src/main/resources/application.properties application.properties
ADD target/ms-contas-pagar.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.config.location=application.properties","-DDB_HOST=ms-contas-pagar-postgres","/app.jar"]