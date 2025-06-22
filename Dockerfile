FROM gradle:8.7-jdk17 AS build

WORKDIR '/'

COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

RUN ./gradlew bootJar -x test --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /

COPY --from=build /build/libs/*.jar app.jar
EXPOSE 8080
#ENV ACTIVE_PROFILE=stag
CMD ["java", "-jar", "app.jar"]
#CMD ["java", "-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "app.jar"]