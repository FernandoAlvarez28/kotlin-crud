all: build-docker-image run-docker-image

build-docker-image:
	docker build -t kotlincrud-postgres:latest .

build-docker-image-multiplatform:
	docker buildx create --use;
	docker buildx build --platform linux/arm64/v8,linux/amd64 -t kotlincrud-postgres:latest .

run-docker-image:
	cd docker; \
	docker-compose up kotlincrud-postgres
