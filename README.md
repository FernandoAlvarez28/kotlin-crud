# kotlin-crud
Small project to experiment and learn Kotlin and other libraries.

## Requirements
- **Java 17**.
- A [Kotlin/Gradle capable IDE](https://kotlinlang.org/docs/kotlin-ide.html).
- [docker-compose](https://docs.docker.com/compose/).
  - You can run without it, but some functions depends on services made available via Docker.

## Contains/Uses
- **Java 17**.
- Basic REST API endpoints with **Kotlin** and **Spring Boot 3.1.1**.
  - Check/Import the [Postman collection](docs/Fernando's Kotlin CRUD.postman_collection.json)+[environment](docs/Fernando's Kotlin CRUD.postman_environment.json).
- **H2 in-memory database** with **Flyway** migration.
  - Console available at http://localhost:8080/h2-console.
- **Spring Data**, **JPA** and **Hibernate**.
- **Spring Security** with **JWT** and **Spring WebFlux**.
  - Based on [soasada's guide](https://github.com/soasada/kotlin-coroutines-webflux-security).
- Fake "Users API" with **Mockoon** via Docker.

## How to run
1. Clone this repository.
2. Import this project on your [Kotlin capable IDE](https://kotlinlang.org/docs/kotlin-ide.html), like [IntelliJ](https://www.jetbrains.com/idea/).
3. Run the [dockerized Mockoon](docker/README.md).
4. Run [KotlincrudApplication.kt](src/main/kotlin/alvarez/fernando/kotlincrud/KotlincrudApplication.kt).
5. Access the endpoints exposed at http://localhost:8080.
   1. Use the available [Postman collection](docs/Fernando's Kotlin CRUD.postman_collection.json)+[environment](docs/Fernando's Kotlin CRUD.postman_environment.json).
   2. Use the login endpoint to generate a JWT token; it will be save on your active environment and used by the other endpoints.

