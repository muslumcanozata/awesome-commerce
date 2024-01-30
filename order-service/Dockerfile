FROM gradle:8.5.0-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM amazoncorretto:21-alpine-jdk
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/order-service.jar
WORKDIR /app
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=test", "order-service.jar"]