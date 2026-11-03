IMAGE = smartcampus:1.0
PORT  = 8088

up:
	docker compose up -d --build

build:
	./mvnw -B clean package -DskipTests

test:
	./mvnw -B test

run: build
	java -jar target/SmartCampus-0.0.1-SNAPSHOT.jar

docker:
	docker build -t $(IMAGE) .

down:
	docker compose down

logs:
	docker compose logs -f

clean:
	./mvnw -B clean

.PHONY: build test run docker up down logs clean