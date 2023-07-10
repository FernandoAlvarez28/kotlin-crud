# Docker containers
Some services that are used by this application are served via [Docker](https://www.docker.com/) to make easy
running without having to install everything locally.

## [Mockoon](https://mockoon.com/)
It's a small server that mocks REST APIs with custom data and configurations from a YAML file.
It has a GUI software to guide building this configuration file and also a CLI and Docker image.

### How it is used here
To serve fake generated users to be used on login.

### How to run
- Run [./start-mockoon.sh](./start-mockoon.sh) from your terminal.
  - Or start manually via [docker-compose](https://docs.docker.com/compose/): `sudo docker-compose up mockoon`
    - Feel free to use `-d` or any other command that does the same thing.
- The [Postman collection](../docs/Fernando's Kotlin CRUD.postman_collection.json)+[environment](../docs/Fernando's Kotlin CRUD.postman_environment.json) also have the mocked endpoints.
