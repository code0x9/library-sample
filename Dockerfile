# base
FROM openjdk:8 AS base
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY gradle gradle
COPY gradlew gradlew
COPY *.gradle .
RUN ./gradlew -Dorg.gradle.daemon=false clean

# build
FROM base AS build
WORKDIR /usr/src/app
COPY . .
RUN ./gradlew -Dorg.gradle.daemon=false build

# release
FROM openjdk:8 AS release
WORKDIR /usr/src/app
COPY --from=build /usr/src/app/build/libs/*.jar .
EXPOSE 8080
CMD ["java", "-jar", "app-0.0.1-SNAPSHOT.jar"]
