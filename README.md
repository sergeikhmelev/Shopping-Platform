# Shopping platform (prices and discounts)

A part of a shopping platform that calculates the price of products based on configurable discount policies. Each product in the system is identified by a UUID. Discounts can be applied either based
on the number of items ordered (count-based discounts) or as a percentage of the total price (percentage-based discounts).

## How to use

It's possible to access the endpoint via Swagger. The service starts with some predefined data.

#### To get product information

```
curl -X 'GET' \
  'http://localhost:8080/products/c00ff428-40ed-45c0-af28-04f83fde7aa2' \
  -H 'accept: application/json'
```

will respond with

```json
{
   "id": "c00ff428-40ed-45c0-af28-04f83fde7aa2",
   "name": "Duck",
   "price": 100,
   "description": "made of rubber"
}
```

#### To calculate price

```
curl -X 'GET' \
  'http://localhost:8080/products/a24df3b0-8f18-4403-b470-ecb5cd4a1ad2/calculate-price?quantity=50&discountPolicyType=COUNT_BASED' \
  -H 'accept: application/json'
```

will respond with

```json
2250
```

### Build and run

1. Run `./gradlew bootBuildImage` from the project root. That will build `image 'docker.io/library/shopping-platform:0.0.1-SNAPSHOT'`.
2. Run docker image with:
   1. _docker_ via `docker run -p 8080:8080 shopping-platform:latest`
    2. _docker compose_ via `docker compose up -d` from the project root.
3. Access a Swagger via `http://localhost:8080/swagger-ui/index.html` (if docker host is set to `localhost`).

---

### Improvements

#### Problem with persistance layer

Initially I used https://github.com/vladmihalcea/hypersistence-utils to persist [CountBasedDiscountPolicy.discounts] as a JSON array. 
But it didn't work with H2 and to save time and don't do major modification of API I decided to replace the DB with list. 
In retrospective, I should've keep both commits 'perstising layer' and 'revert'. But I've decided to remove them altogether. 
Now, I do think it wasn't the best decision.

#### Domain

1. Clarify with PO if the product price calculation endpoint is expected to receive a discount policy type as an argument.
2. Specify the proper boundaries for the policies fields.
3. Decide on rounding.
4. It's not clear if this service should be aware of available quantity of the products.
5. Add integration tests with use-cases.

#### Tech

1. Use a proper DB (not used here for simplicity)
2. Spring Security is missing
3. Check why `@Positive` is not visible on the SwaggerUI
4. Double-check and remove unnecessary dependencies (the project was generated from IntelliJ)
5. Allow product modification
