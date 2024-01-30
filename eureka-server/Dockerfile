FROM gradle:8.5.0-jdk21-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM amazoncorretto:21-alpine-jdk
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/eureka-server.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=test", "eureka-server.jar"]