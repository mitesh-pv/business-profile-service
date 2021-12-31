# Business Profile System

This repository contains a project to demonstrate a **Business Profile System** that maintains a common platform to store, maintain and manage business profiles of the customers. The main purpose of the project is to develop a system that can provide a common service to create and manage profile. Once the profile is created, the user can modify the profile details, but for that they must approve it (the update request) from each of their entitled product.

### Tech-Stack Used

* Spring Boot
* AWS DynamoDB
* AWS Simple Notification Service
* AWS Simple Queuing Service


### Components

The repository contains 3 modules:
1. BizProfile
2. QBAccounting
3. QBPayroll

**BizProfile**

This is the main service that managese the profiles.
It provides following set of functionalities:
* Create profile
* Update profile
* Show all entitled products

Following shows the API Design for the requirement

**Managing Business Profile**

1. Create Profile<br>


```groovy
POST: /api/business-profile/v1/create-profile
```
<details>
  <summary>Request Payload</summary>
  
  ```json
{
    "legalName" : "string",
    "companyName" : "string",
    "email": "string",
    "taxId" : "string",
    "website": "string",
    "legalAddress" : "string",
    "businessAddress" : {
        "line1" : "string",
        "line2" : "string",
        "city" : "string",
        "state" : "string",
        "zip" : "string",
        "country" : "string"
    }
}
  ```
</details>

2. Update Profile<br>


```groovy
PUT: /api/business-profile/v1/update-profile/{taxId}
```
<details>
  <summary>Request Payload</summary>
  
  ```json
{
    "legalName" : "string",
    "companyName" : "string",
    "email": "string",
    "taxId" : "string",
    "website": "string",
    "legalAddress" : "string",
    "businessAddress" : {
        "line1" : "string",
        "line2" : "string",
        "city" : "string",
        "state" : "string",
        "zip" : "string",
        "country" : "string"
    }
}
  ```
</details>

**Managing Product Entitlement**

1. Create Entitlement<br>


```groovy
POST: /api/product-entitlement/v1/create-entitlement
```
<details>
  <summary>Request Payload</summary>
  
  ```json
{
    "entitledProdCode": "string",
    "personTaxId": "string"
}
  ```
</details>

2. Get Entitled Products<br>


```groovy
GET: /v1/create-entitlement/v1/get-entitled-products/{personTaxId}
```

### High Level Design

The Below design leverages the fan-out option provided by AWS SQS-SNS architecture for asynchronous parallel message processing. The same has been mentioned in following steps.
* The BusinessProfile Service is the main application server that handles the create and update request.
* The update request is first received by business-profile service via http request.
* Business profile server then publishes request(to the bnusiness-profile-req sns topic) which is received by the products subscribed by the user w.r.t. which the update request was received (using their respective sqs queues)
* Each of the products can approve/reject the request (by publishing their respone to bnusiness-profile-res topic) which is received by the the business-profile service.
* Based on the number of approvals received from the bnusiness-profile-res sns topic, business-profile service makes decision to persist the update request to dynamodb.


![BusinessProfile-HLD-2](https://user-images.githubusercontent.com/28482024/147813620-6a9ea864-e3ae-491e-9e57-3d0b69bfe409.jpg)








