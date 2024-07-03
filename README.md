# Shopping platform (prices and discounts)

A part of a shopping platform that calculates the price of products based on configurable discount policies. Each product in the system is identified by a UUID. Discounts can be applied either based
on the number of items ordered (count-based discounts) or as a percentage of the total price (percentage-based discounts).

### Build and run

1. Run `./gradlew bootBuildImage` from the project root. That will build `image 'docker.io/library/shopping-platform:0.0.1-SNAPSHOT'`.
2. Run docker image with:
   1. _docker_ via `docker run -p 8080:8080 shopping-platform:latest`
    2. _docker compose_ via `docker compose up -d` from the project root.
3. Access a Swagger via `http://localhost:8080/swagger-ui/index.html`
