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


1) Qualification (mixed results: 2 successes, 1 error, 1 skipped)

Request:
header.operation = "Qualification"

{
  "header": {
    "requestId": "REQ-QUAL-0001",
    "sourceId": "PE-Zelle",
    "operation": "QUALIFICATION",
    "timestamp": "2025-09-08T15:30:00Z"
  },
  "operationStatus": "PARTIAL_SUCCESS",
  "results": {
    "accountValidation": {
      "status": "SUCCESS",
      "data": {
        "accountExists": true,
        "accountType": "D",
        "provider": "CIF"
      },
      "latencyMs": 20
    },
    "balanceInquiry": {
      "status": "ERROR",
      "error": {
        "errorCode": "QF-BI-001",
        "errorDescription": "Balance provider unavailable"
      },
      "latencyMs": 450
    },
    "enrichment": {
      "status": "SUCCESS",
      "data": {
        "customerSegment": "GOLD",
        "kycLevel": "L2"
      },
      "latencyMs": 15
    },
    "preAuthorization": {
      "status": "SKIPPED"
    }
  }
}

2) Posting (memo post success)

Request:
header.operation = "Posting"

{
  "header": {
    "requestId": "REQ-POST-0001",
    "sourceId": "PE-Zelle",
    "operation": "POSTING",
    "timestamp": "2025-09-08T15:30:05Z"
  },
  "operationStatus": "SUCCESS",
  "results": {
    "posting": {
      "status": "SUCCESS",
      "data": {
        "tranRef": "ZELLE-REF-987654",
        "posted": true,
        "debitTranCode": "D123",
        "creditTranCode": "C456"
      },
      "latencyMs": 55
    }
  }
}

3) FraudCheck (success)

Request:
header.operation = "FraudCheck"

{
  "header": {
    "requestId": "REQ-FRD-0001",
    "sourceId": "PE-Zelle",
    "operation": "FRAUD_CHECK",
    "timestamp": "2025-09-08T15:30:10Z"
  },
  "operationStatus": "SUCCESS",
  "results": {
    "fraudCheck": {
      "status": "SUCCESS",
      "data": {
        "score": "0.08",
        "decision": "ALLOW"
      },
      "latencyMs": 40
    }
  }
}

4) CustomerAlert (success)

Request:
header.operation = "CustomerAlert"

{
  "header": {
    "requestId": "REQ-ALERT-0001",
    "sourceId": "PE-Zelle",
    "operation": "CUSTOMER_ALERT",
    "timestamp": "2025-09-08T15:30:15Z"
  },
  "operationStatus": "SUCCESS",
  "results": {
    "customerAlert": {
      "status": "SUCCESS",
      "data": {
        "alertId": "AL-5566",
        "channel": "SMS",
        "template": "Zelle-Success",
        "delivered": true
      },
      "latencyMs": 30
    }
  }
}

