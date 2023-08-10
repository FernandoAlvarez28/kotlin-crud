# Docker containers
Some services that are used by this application are served via [Docker](https://www.docker.com/) to make easy
running without having to install everything locally.

## kotlincrud-postgres
The image of this application itself. There is a [Dockerfile](../Dockerfile) to build it, and it's defined in the [docker-compose](docker-compose.yml) along with its dependencies ([Mockoon](#mockoon) and [Postgres](#postgres)).

### How to run
1. Build and run the image with one of the following commands:
   - [./build-start-application.sh](./build-start-application.sh) script from your terminal.
     - Or manually via [docker-compose](https://docs.docker.com/compose/):
       ```shell
       $ docker-compose up --build kotlincrud-postgres
        ```
     - It will also run [Mockoon](#mockoon) and [Postgres](#postgres) that are explained below.
   - [Makefile](../Makefile) that basically does the same thing as above.

## Mockoon
It's a small server that mocks REST APIs with custom data and configurations from a YAML file.
It has a GUI software to guide building this configuration file and also a CLI and Docker image.

https://mockoon.com/

### How it is used here
To serve fake generated users to be used on login.

### How to run
1. Run the [./start-mockoon.sh](./start-mockoon.sh) script from your terminal.
   - Or start manually via [docker-compose](https://docs.docker.com/compose/):
     ```bash
     $ docker-compose up mockoon
     ```
     - Feel free to use `-d` or any other command that does the same thing.
- The [Postman collection](../docs/fernandos-kotlin-crud.postman_collection.json) + [environment](../docs/fernandos-kotlin-crud.postman_environment.json) also have the mocked endpoints.

## Postgres
I's one of the most used relational databases.

https://www.postgresql.org/

### How it is used here
It is the main database for the data used and stored by the application

### How to run
1. Run the [./start-postgres.sh](./start-postgres.sh) script from your terminal.
   - Or start manually via [docker-compose](https://docs.docker.com/compose/):
     ```bash
     $ docker-compose up postgres
     ```
     - Feel free to use `-d` or any other command that does the same thing.
2. As the application uses [Flyway migrations](https://flywaydb.org/), it will initialize the schema on the first run.

- You can connect to it with an DBMS of your choice (like [DBeaver](https://dbeaver.io/)):
  - Host: `localhost`
  - Port: `65432`
  - User: `postgres`
  - Password: `blue-elephant`
  - Schema: `kotlin_crud`
- You can reset the stored data by running the [./reset-postgres.sh](./reset-postgres.sh) script from your terminal.
  - Or by running manually via [docker-compose](https://docs.docker.com/compose/) ([source](https://stackoverflow.com/a/71796529)):
    ```bash
    $ docker-compose rm -s -f -v postgres
    ```

## InfluxDB, Grafana and K6
K6 is a load test tool, and it uses InfluxDB to store the results and Grafana to show them in dashboards.

https://k6.io/

### How to run
1. Run the [./start-k6-dependencies.sh](./start-k6-dependencies.sh) script from your terminal.
   - Or start manually via [docker-compose](https://docs.docker.com/compose/):
     ```bash
     $ docker-compose up influxdb grafana
     ```
2. Start the application (see the [root README.md](./../README.md#how-to-run)).
3. Run the [./start-k6-tests.sh](./start-k6-tests.sh) script from your terminal.
   - Or start manually via [docker-compose](https://docs.docker.com/compose/):
     ```bash
     $ docker-compose up k6
     ```
4. See and wait the tests execution in the terminal.
5. Check the results in the terminal or in the [Grafana dashboard](http://localhost:3000/d/XKhgaUpik/k6-load-testing-results-by-groups):

- You can reset the stored data by running the [./reset-k6.sh](./reset-k6.sh) script from your terminal.
  - Or by running manually via [docker-compose](https://docs.docker.com/compose/) ([source](https://stackoverflow.com/a/71796529)):
    ```bash
    $ docker-compose rm -s -f -v influxdb grafana k6
    ```
