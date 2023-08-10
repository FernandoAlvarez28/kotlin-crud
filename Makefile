all: build-docker-image run-docker-image

build-docker-image:
	docker build -t kotlincrud-postgres:latest .

run-docker-image:
	cd docker; \
	docker-compose up kotlincrud-postgres
