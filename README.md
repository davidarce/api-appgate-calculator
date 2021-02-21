# Appgate calculator API
A RESTful API for calculate operations like ADD, SUBTRACT, MULTIPLY, DIVIDE and EXP

### Technologies
- Java 11
- Spring framework
- Spring Boot
- Mongodb
- JUnit 5 & Karate for unit and Integration test 
- Docker for deploying

## Running
### Prerequisites
- Java 11
- Maven 3+
- Mongodb installed in your local machine -> localhost:27017
- Docker since you want to run the app in a container

## How to use locally

```
git clone https://github.com/davidarce/api-appgate-calculator.git   

cd api-appgate-calculator

mvn clean package

java -jar target/api-appgate-calculator-0.0.1-SNAPSHOT.jar
```
## Running with docker

```
docker-compose -d up --build 
```

Now you can access the API with base-path: http://localhost:8080/api/calculator

# Endpoints

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/77a27fdf5f9392b53810)

```
Create Session:

- POST    /api/calculator/session - Create a new session

Operands:

- POST     /api/calculator/:sessionId/operands - Send a operand

Result:

- GET     /api/calculator/:sessionId/result?operator=:operator

Auditory transactions

- GET     /api/calculator/:sessionId/transactions

```

## Http Status Code Summary

```
200 OK - Everything worked as expected
201 OK - Resource created
202 Accepter - Request has been accepted for further processing.
400 Bad request - The request due to something that is perceived to be a client error 
404 Not Found - The requested resource does not exist
500 Internal Server error - The server has encountered a situation it doesn't know how to handle.
```
# Examples

## Create Session

###### Request

```
POST /api/calculator/session
```
##### Response
`Ok 201 created`
```
{
    "sessionId": "a43dddcf-c931-4845-8662-b2eca2847bc4"
}
```
## Add Operands

###### Request

```
POST /api/calculator/a43dddcf-c931-4845-8662-b2eca2847bc4/operands
```

Body

```
{
	"number": "10",
}
```

##### Response
```
202 Accepted
```

## Result operation
###### Request

```
GET /api/calculator/a43dddcf-c931-4845-8662-b2eca2847bc4/result?operator=ADD
```

##### Response
```
{
    "result": "30"
}
```

## Auditory transactions 
###### Request

```
GET /api/calculator/a43dddcf-c931-4845-8662-b2eca2847bc4/transactions
```

##### Response
```
[
    {
        "id": "6032d6418d96ba5b3ff31ee7",
        "userSessionId": "1da66947-277c-4592-8c29-53ab5fafce2b",
        "action": "ADD_OPERAND",
        "success": true,
        "stackTrace": null,
        "createdAt": "2021-02-21T16:53:05.482"
    },
    {
        "id": "6032d63c8d96ba5b3ff31ee6",
        "userSessionId": "1da66947-277c-4592-8c29-53ab5fafce2b",
        "action": "CREATE_SESSION",
        "success": true,
        "stackTrace": null,
        "createdAt": "2021-02-21T16:53:00.989"
    }
]
```

# Documentation API

Open the follow link to get the API documentation [URL](http://localhost:8080/api/calculator/documentation)
