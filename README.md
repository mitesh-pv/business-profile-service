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


![BusinessProfile-HLD-4](https://user-images.githubusercontent.com/28482024/147825827-a9646f38-5d07-44cc-af8e-cf4ca0ccdb43.jpg)


### Output

1. Create Entitlement for taxID : STK5098

<img width="1253" alt="4 create-entitlement1" src="https://user-images.githubusercontent.com/28482024/147877846-5ff74e09-58bb-4bb2-bb03-588d51c8c790.png">


2. Create Profile

<img width="1336" alt="7 create-profile1" src="https://user-images.githubusercontent.com/28482024/147877878-c28a2a3c-0289-4909-8e9d-ee93ebc4e843.png">

3. DynamoDB Profile Datastore

<img width="979" alt="7 1 dynamodb-profile-before-udpate" src="https://user-images.githubusercontent.com/28482024/147877907-ccf8a0ce-0215-48a2-bc07-be8b061c0d9c.png">

4. Business Profile not found exception response

<img width="1255" alt="8 business-propfile-not-found" src="https://user-images.githubusercontent.com/28482024/147877936-edea0d1a-6606-4bd8-ad0f-fa868a51e4f8.png">

5. Get Business Profile success response

<img width="1242" alt="9 business-profile-found" src="https://user-images.githubusercontent.com/28482024/147878029-b0efb295-c3cc-44d5-a54f-5afece40bbb8.png">

6. Business Profile update request

<img width="1197" alt="10 profile-update-request" src="https://user-images.githubusercontent.com/28482024/147878061-ae826230-7d99-4bdd-8245-b6acd3da1719.png">

7. Notification LOG received at apps (QB-ACC app and AB-PRL app)

<img width="1596" alt="11  notification-at-qb-acc" src="https://user-images.githubusercontent.com/28482024/147878089-19152ec2-8b30-4bbd-b8cd-e0e19a6aec75.png">

<img width="1586" alt="12  notification-at-qb-prl" src="https://user-images.githubusercontent.com/28482024/147878099-35e33e35-8ceb-45bf-bd99-8cc79e51ce2f.png">

8. Approval sent from AB-ACC and AB-PRL

<img width="1196" alt="13  approval-from-qb-acc" src="https://user-images.githubusercontent.com/28482024/147878101-1ca3f1b2-5760-4a7d-98ff-f72206cc8dd0.png">
<img width="1192" alt="14  approval-from-qb-prl" src="https://user-images.githubusercontent.com/28482024/147878111-38d59767-7ec3-46cf-b8b5-9dad225f75a2.png">

9. Response received at BusinessProfile service

<img width="1611" alt="15  update-applied-in-db" src="https://user-images.githubusercontent.com/28482024/147878125-c887fe40-707f-422b-bf5f-f1ade08fba3c.png">

10. Get Profile to validate if update applied

<img width="1196" alt="16  update-applied-to-db-response" src="https://user-images.githubusercontent.com/28482024/147878133-786d3cae-532d-4111-bd6e-2c1e789a0ad0.png">

11. Dynamodb updated record

<img width="986" alt="17  update-applied-to-db" src="https://user-images.githubusercontent.com/28482024/147878152-388ee8b4-190c-467e-8ff6-5ca505d0ff33.png">
