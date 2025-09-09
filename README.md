# Bank Ops Multi-Module (Spring Boot)

This repository is a complete **multi‑module Spring Boot** project with a **single controller** that routes to the correct **SDK** based on `header.operation` in the request body.

## Modules
- **common-model**: Request/Response envelopes and block DTOs
- **common-spi**: Operation SPI (`OperationService`, `RequestContext`, `OperationDescriptor`)
- **sdk-qualification-starter**: Qualification SDK (4 optional sub-steps; null => SKIPPED)
- **sdk-posting-starter**: Posting (memo post) SDK
- **sdk-fraud-starter**: Fraud check SDK
- **sdk-alert-starter**: Customer alert SDK
- **api-server**: Thin REST API with one endpoint `/v1/operation`

## Build & Run
```bash
mvn -q clean package
cd api-server
mvn -q spring-boot:run
```

## Sample Request (Qualification)
```json
{
  "header": { "sourceId": "PE-Zelle", "operation": "Qualification", "requestId": "REQ-QUAL-0001" },
  "qualification": {
    "accountValidation": { "side": "D" },
    "balanceInquiry":    { "side": "C" },
    "enrichment":        { "side": "B" },
    "preAuthorization":  { "side": "D", "description": "Pre-auth for debit side" }
  }
}
```

## Notes
- Each SDK returns a uniform `CommonResponse` with `operationStatus` and per-step results.
- Add/remove operations by adding/removing SDK starter dependencies in `api-server/pom.xml`.
