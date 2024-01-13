FROM amazoncorretto:21-alpine-jdk
RUN mkdir /app
COPY app.jar /app/app.jar
WORKDIR /app
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]