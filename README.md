# Kotlin CRUD w/ PostgreSQL
Small project to experiment and learn Kotlin and other libraries.

There is also a CRUD with MongoDB: https://github.com/FernandoAlvarez28/kotlin-crud-mongodb

## Requirements
- **Java 17** and a [Kotlin/Gradle capable IDE](https://kotlinlang.org/docs/kotlin-ide.html).
- Or [Docker](https://www.docker.com/) and [docker-compose](https://docs.docker.com/compose/).

## Contains/Uses
- **Java 17**.
- [Dockerfile](Dockerfile) and GitHub Actions to publish on Docker Hub.
- Basic REST API endpoints with **Kotlin**, **Spring Boot 3.1.1** and **Spring WebFlux**.
  - Check/Import the [Postman collection](docs/fernandos-kotlin-crud.postman_collection.json) + [environment](docs/fernandos-kotlin-crud.postman_environment.json).
- **PostgreSQL database** via Docker with **Flyway** migration.
  - Connection informations [here](docker/README.md#postgres).
- **Spring Data R2DBC** (reactive driver for relational databases, similar to JPA).
- **Spring Security** with **JWT** and **Spring WebFlux**.
  - Based on [soasada's guide](https://github.com/soasada/kotlin-coroutines-webflux-security).
- Fake "Users API" with **Mockoon** via Docker.
- Load test with **K6** via Docker.

## How to run
### Locally
1. Clone this repository.
2. Import this project on your [Kotlin capable IDE](https://kotlinlang.org/docs/kotlin-ide.html), like [IntelliJ](https://www.jetbrains.com/idea/).
3. Run the [dockerized Postgres](docker/README.md#postgres) database (or you can use your own Postgres instance).
4. Run the [dockerized Mockoon](docker/README.md#mockoon).
5. Run [KotlincrudApplication.kt](src/main/kotlin/alvarez/fernando/kotlincrud/KotlincrudApplication.kt).
6. Access the endpoints exposed at http://localhost:8080.
   1. Use the available [Postman collection](docs/fernandos-kotlin-crud.postman_collection.json) + [environment](docs/fernandos-kotlin-crud.postman_environment.json).
   2. Use the login endpoint to generate a JWT token; it will be save on your active environment and used by the other endpoints.

### Via Docker
1. Clone this repository.
2. Build the [Dockerfile](Dockerfile) and run the Docker image with its dependencies using one of the methods:
   - [Makefile](Makefile):
      ```shell
      $ make
      ```
   - [Docker compose](docker/docker-compose.yml):
      ```shell
      ./docker$ docker-compose up --build kotlincrud-postgres
      ```
   - More details at [docker/README.md#kotlincrud-postgres](docker/README.md#kotlincrud-postgres).
6. Run [KotlincrudApplication.kt](src/main/kotlin/alvarez/fernando/kotlincrud/KotlincrudApplication.kt).
7. Access the endpoints exposed at http://localhost:8080.
    1. Use the available [Postman collection](docs/fernandos-kotlin-crud.postman_collection.json) + [environment](docs/fernandos-kotlin-crud.postman_environment.json).
    2. Use the login endpoint to generate a JWT token; it will be save on your active environment and used by the other endpoints.

