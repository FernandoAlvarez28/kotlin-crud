# Docker containers
Some services that are used by this application are served via [Docker](https://www.docker.com/) to make easy
running without having to install everything locally.

## Mockoon
It's a small server that mocks REST APIs with custom data and configurations from a YAML file.
It has a GUI software to guide building this configuration file and also a CLI and Docker image.

https://mockoon.com/

### How it is used here
To serve fake generated users to be used on login.

### How to run
- Run the [./start-mockoon.sh](./start-mockoon.sh) script from your terminal.
  - Or start manually via [docker-compose](https://docs.docker.com/compose/): `sudo docker-compose up mockoon`
    - Feel free to use `-d` or any other command that does the same thing.
- The [Postman collection](../docs/Fernando's Kotlin CRUD.postman_collection.json)+[environment](../docs/Fernando's Kotlin CRUD.postman_environment.json) also have the mocked endpoints.

## Postgres
I's one of the most used relational databases.

https://www.postgresql.org/

### How it is used here
It is the main database for the data used and stored by the application

### How to run
- Run the [./start-postgres.sh](./start-postgres.sh) script from your terminal.
  - Or start manually via [docker-compose](https://docs.docker.com/compose/): `sudo docker-compose up postgres`
    - Feel free to use `-d` or any other command that does the same thing.
- You can connect to it with an DBMS of your choice (like [DBeaver](https://dbeaver.io/)):
  - Host: `localhost`
  - Port: `65432`
  - User: `postgres`
  - Password: `blue-elephant`
  - Schema: `kotlin_crud`
- As the application uses [Flyway migrations](https://flywaydb.org/), it will initialize the schema on the first run.
- You can reset the stored data by running the [./reset-postgres.sh](./reset-postgres.sh) script from your terminal.
  - Or by running manually via [docker-compose](https://docs.docker.com/compose/): `docker-compose rm -s -f -v postgres` ([source](https://stackoverflow.com/a/71796529))
