# Recommended: https://snyk.io/blog/best-practices-to-build-java-containers-with-docker/

# https://hub.docker.com/_/gradle
FROM gradle:8.2.1-jdk17 AS build
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN gradle build --no-daemon

# I wanted to use https://hub.docker.com/r/bellsoft/liberica-runtime-container but it doesn't have arm64
# https://hub.docker.com/_/eclipse-temurin
FROM eclipse-temurin:17.0.8_7-jre
RUN mkdir /app
RUN addgroup --system javauser && adduser --system --shell /bin/false --ingroup javauser javauser
COPY --from=build /project/build/libs/kotlincrud-0.0.1-SNAPSHOT.jar /app/application.jar
WORKDIR /app
RUN chown -R javauser:javauser /app
USER javauser
CMD "java" "-jar" "application.jar"