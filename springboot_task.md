# Task: Create a REST Web Service using Spring Boot

## Objective:
Create a simple REST web service using Spring Boot and Spring Initializr. The service should accept a list of numbers via an endpoint, store them in an H2 in-memory database, and provide proper unit and integration tests. Ensure that the project adheres to Domain-Driven Design principles with a layered architecture and follows best practices.

## Requirements:
Start by generating a new Spring Boot project from Spring Initializr with the necessary dependencies. 

### REST Endpoint:
Create a POST endpoint: /numbers
The endpoint should accept a JSON request body containing a list of integers, for example:
{
  "numbers": [1, 2, 3, 4, 5]
}

### In-Memory Database:
Use H2 as an in-memory database.
Store each number in the database as a new entity.

### Architecture:
Adhere to Domain-Driven Design with layered architecture. 

### Testing:
Provide unit tests for the layers you think are necessary to test using mocking (e.g., with Mockito).
Provide integration tests for the layers you think are necessary using Spring Boot Test.

### Documentation:
Provide a clear README.md with the following:
- Instructions to set up and run the application.
- How to run the tests.
- Any relevant details (such as endpoints, request body format).

## Submission:
Push the code to a GitHub repository.
Include a README.md with setup instructions.
Make sure the code adheres to good practices and follows clean code principles.