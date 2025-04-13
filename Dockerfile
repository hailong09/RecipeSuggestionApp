FROM eclipse-temurin:21-jdk-alpine

# Install CA certificates (needed for Supabase SSL)
RUN apk add --no-cache ca-certificates

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]