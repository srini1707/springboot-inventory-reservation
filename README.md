# Inventory Reservation Service

    Multi-warehouse stock control with atomic reservations, releases, adjustments, low-stock visibility and concurrency-safe updates.

    ## Technology
    - Java 21
    - Spring Boot 3.4.4
    - Maven
    - Spring Web, Bean Validation, Actuator
    - In-memory storage using Java collections (no database)

    ## Important database note
    This project has **no database dependency and no database configuration**. Data is held in application memory and is lost when the process/container restarts. This is intentional for a database-free DevOps lab.

    ## Run locally
    ```bash
    mvn clean verify
    mvn spring-boot:run
    ```
    Application port: `8082`

    Health check:
    ```bash
    curl http://localhost:8082/actuator/health
    ```

    ## Docker
    ```bash
    docker build -t springboot-inventory-reservation:1.0 .
    docker run --rm -p 8082:8082 springboot-inventory-reservation:1.0
    ```

    ## API endpoints
    ```text
    PUT    /api/v1/stock/{warehouse}/{sku}?quantity=100
GET    /api/v1/stock/{warehouse}/{sku}
POST   /api/v1/reservations
POST   /api/v1/reservations/{id}/release
GET    /api/v1/reservations/{id}
    ```

    ## Example requests
    ```bash
    curl -X PUT 'http://localhost:8082/api/v1/stock/WH-01/LAPTOP-01?quantity=100'
curl -X POST http://localhost:8082/api/v1/reservations -H 'Content-Type: application/json' -d '{"warehouse":"WH-01","sku":"LAPTOP-01","quantity":5,"reference":"ORDER-100"}'
curl http://localhost:8082/api/v1/stock/WH-01/LAPTOP-01
    ```

    ## DevOps practice tasks
    1. Build the JAR with Maven and inspect the `target/` output.
    2. Build and run the Docker image; inspect logs and container health.
    3. Change the application port through an environment variable or JVM property.
    4. Add a GitHub Actions branch protection rule and inspect CI failures.
    5. Add structured logging and a correlation ID filter.
    6. Add API integration tests with MockMvc.
    7. Create Kubernetes Deployment and Service manifests; configure readiness and liveness probes.
    8. Add resource requests/limits and run a rolling update.
    9. Introduce a deliberate validation or business-rule bug and troubleshoot it.
    10. Document the impact of in-memory data loss during pod restart and propose a persistence strategy.

    ## Repository structure
    ```text
    src/main/java/   application, controller, service, records, exception handler
    src/main/resources/application.properties
    src/test/java/   service unit tests
    .github/workflows/ci.yml
    Dockerfile
    pom.xml
    ```
