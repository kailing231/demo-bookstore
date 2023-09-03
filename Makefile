run:
	mvn spring-boot:run

java:
	mvn clean package -DskipTests

docker:
	docker compose up

.PHONY: run java docker