# Recommended: https://snyk.io/blog/best-practices-to-build-java-containers-with-docker/

# https://hub.docker.com/_/gradle
FROM gradle:8.2.1-jdk17-alpine AS build
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN gradle build --no-daemon

# https://hub.docker.com/r/bellsoft/liberica-runtime-container
FROM bellsoft/liberica-runtime-container:jre-17-slim-musl
RUN mkdir /app
RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
COPY --from=build /project/build/libs/kotlincrud-0.0.1-SNAPSHOT.jar /app/application.jar
WORKDIR /app
RUN chown -R javauser:javauser /app
USER javauser
CMD "java" "-jar" "application.jar"