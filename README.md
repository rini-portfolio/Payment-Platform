# 🚀 Event-Driven Payment & Order Orchestration Platform

## 📌 Overview

This project demonstrates a **scalable, secure, and event-driven payment processing architecture** designed for a modern retail checkout experience. The system supports **Credit Card, Google Pay, Apple Pay, and PayPal payments via Braintree**, and follows real-world enterprise integration patterns using:

- **OAuth 2.0 (User-to-Service – U2S) authentication**
- **AWS API Gateway for secure access control**
- **Synchronous REST communication for orchestration**
- **Asynchronous processing via AWS SQS**
- **Event streaming via Apache Kafka**
- **Microservices architecture using Java Spring Boot**

The design intentionally separates:
- **Real-time payment orchestration (Payments-A & Payments-B)**  
from  
- **Downstream business processing (Order Service, Fulfillment, Analytics, etc.)**

This makes the system resilient, scalable, loosely coupled, and interview-ready.

---

## 🎯 Supported Payment Capabilities

The platform supports three key payment flows:

### ✅ **1) Approval Flow (Authorization)**
Used during checkout to authorize a payment.

### ✅ **2) Deposit Flow (Capture)**
Used to capture funds after order confirmation.

### ✅ **3) Reverse / Refund Flow**
Used to reverse or refund payments in case of cancellations, returns, or failures.

For all three flows, the system integrates with **Braintree Gateway** for:
- Credit Card (CC)
- Google Pay  
- Apple Pay  
- PayPal  

---

 USER (Web / Mobile)
                              |
                      (OAuth 2.0 - U2S)
                              |
                        API GATEWAY
                              |
                        Payments-A
                    (Edge / Facade)
                              |
                         REST (S2S)
                              v
                        Payments-B
                    (Payment Orchestrator)
                              |
              ----------------------------------
              |                                |
      REST call to Wallet Service        Publish to SQS
              |                                |
      (fetch payment method)             payment-requests-queue
              |                                |
              v                                v
        Wallet Service                Payment-Platform-Service
   (add/get/update/delete)          (Payment Execution Layer)
                                              |
                                         Braintree Gateway
                                      (CC / PayPal / Apple Pay /
                                        Google Pay)
                                              |
                                       Kafka Event Stream
                                  (payment-approved / deposited /
                                   reversed / refunded)
                                              |
                                      Order Service consumes
                                      updates order lifecycle

---

### **User-to-Service (U2S) – OAuth 2.0**

1. User logs into retail account
2. Identity provider (Keycloak) issues OAuth token
3. UI sends token to API Gateway → Payments-A
4. API Gateway validates token before allowing access
---

## 🔁 Service Responsibilities 

### **Payments-A (Edge / Facade Service)**
- First backend entry point from UI  
- Validates request schema  
- Passes security context  
- Calls Payments-B synchronously  

### **Payments-B (Orchestrator)**
- Core decision-making service
- Calls **Wallet Service** to fetch payment method (add/get/update/delete/list)
- Decides which flow to execute (approve / deposit / reverseApproval / refund)
- Pushes request to **SQS** for async payment execution
- Publishes Kafka event after payment completes



## 🧩 Order Lifecycle Using Kafka

| Kafka Event         | Order State Change      |
|---------------------|------------------------|
| payment-approved    | CREATED → AUTHORIZED   |
| payment-deposited   | AUTHORIZED → PAID      |
| payment-reversed    | PAID → REVERSED        |
| payment-refunded    | PAID → REFUNDED        |



## 🛠️ Tech Stack

| Layer | Technology |
|------|------------|
| API Gateway | AWS API Gateway |
| Auth | OAuth 2.0 (U2S) |
| Payments-A | Java Spring Boot |
| Payments-B | Java Spring Boot |
| Wallet Service | Java Spring Boot |
| Payment-Platform | Java Spring Boot |
| Messaging | AWS SQS |
| Streaming | Apache Kafka |
| Payment Gateway | Braintree |
| Cloud | AWS (EC2, RDS, SQS) |

---