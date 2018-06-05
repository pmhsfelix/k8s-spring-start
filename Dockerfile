from openjdk:8-jre-alpine
add build/libs/k8s-0.0.1-SNAPSHOT.jar /app.jar
entrypoint ["java", "-jar", "/app.jar"]
