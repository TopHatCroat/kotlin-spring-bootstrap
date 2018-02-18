FROM openjdk:9.0.1-11
VOLUME /tmp
VOLUME /app
ARG JAR_FILE
ARG DB_URL
COPY ${JAR_FILE} /app/app.jar
ENV DB_URL ${DB_URL}
EXPOSE 8080

ENTRYPOINT "java" "-Djava.security.egd=file:/dev/./urandom" "-jar" "/app/app.jar" "--spring.datasource.url=$DB_URL"