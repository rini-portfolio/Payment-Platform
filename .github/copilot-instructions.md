# GitHub Copilot instructions for payment-platform

## Big picture
- This is a Maven multi-module Spring Boot project with three modules:
  - `payments-a` (HTTP entrypoint) → receives payment requests and forwards to orchestration
  - `payments-b` (orchestration) → coordinates calls to `wallet-service` to perform debit/credit
  - `wallet-service` → simple in-memory wallet API exposing `/wallet/debit` and `/wallet/credit`

## How to run locally
- Start services in separate terminals using Maven:
  - `mvn -pl wallet-service spring-boot:run` (port 8083)
  - `mvn -pl payments-b spring-boot:run` (port 8082)
  - `mvn -pl payments-a spring-boot:run` (port 8081)
- Example flow: POST to `http://localhost:8081/payments` with JSON:
  ```json
  {"fromAccount":"A-1","toAccount":"B-1","amount":100}
  ```
  The orchestration service will call wallet-service to debit and credit.

## Project-specific conventions
- Each module runs on a fixed port for local dev (see `application.properties` in each module).
- Inter-service calls are simple `RestTemplate` POSTs to fixed URLs defined in module `application.properties`.
- `wallet-service` keeps state in an in-memory `Map<String, BigDecimal>` (reset on restart).

## Key files to inspect
- `payments-a/src/main/java/com/example/paymentsa/PaymentController.java` — entrypoint
- `payments-b/src/main/java/com/example/paymentb/OrchestrationService.java` — orchestration logic
- `wallet-service/src/main/java/com/example/wallet/WalletService.java` — balance management

## Testing & debugging notes
- Services return HTTP 4xx on business errors (insufficient funds) and 5xx for unexpected failures.
- To simulate failures, adjust `wallet-service` to throw an exception or return 400 and verify orchestration compensates.

## When modifying orchestration
- Keep the sequence explicit: debit payer → credit payee → on credit failure attempt to refund payer.
- All calls are synchronous and idempotency is not implemented (be explicit when adding it).

## Example snippets
- POST `/payments` payload: `{fromAccount, toAccount, amount}`
- `/wallet/debit` payload: `{accountId, amount}`; returns `{success, balance}`

If any part of these instructions is unclear or you'd like me to add CI / Dockerfile / health checks, tell me which area to expand.