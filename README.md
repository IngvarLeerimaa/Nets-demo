# Nets Demo Project

This is a simple RESTful web service application built using Spring Boot. It provides endpoints for managing lists of numbers, 
storing them in an H2 in-memory database, and includes unit and integration tests to ensure functionality and quality.

Task description for given work can be accessed [here.](springboot_task.md)

## Features

- CRUD operations on lists of numbers via REST endpoints.
- H2 in-memory storage.
- Layered architecture following Domain-Driven Design principles.
- Unit and integration tests using JUnit, Mockito, and Spring Boot Test.

## Table of Contents
- [Prerequisites](#Prerequisites)
- [Installation](#Installation)
- [API Endpoints](#API-Endpoints)
- [Testing](#Testing)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)

### Prerequisites

To run this project, you need:
- Java 17 or higher
- Maven 3.6 or higher
- Spring Boot 3.3.5 or higher

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/IngvarLeerimaa/Nets-demo
   cd Nets-demo
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
The application will start on `http://localhost:8080`.

Port can be changed by navigating to `src/main/resources/application.properties` 
and inserting your desired port number `server.port=<Port_Number>`.

The application uses an H2 in-memory database. 

To access the H2 ui, navigate to `http://localhost:8080/h2-ui/`.

## API Endpoints

- **POST /numbers** - Stores each number in the database as a new entity.
  - Request Body:
    ```json
    {
      "numbers": [54, 43, 21]
    }
    ```
    - **Response**: JSON object of the saved Nums entity.
    - **Status Codes**:
      - `200 OK`: Successfully added numbers.
      - `400 BAD REQUEST`: Invalid request body.
      ![numbers](assets/images/numbers.png)

- **GET /get**
    - Retrieve all lists of numbers.
    - **Response**: JSON array of all `Nums` entities.
    - **Status Codes**:
      - `200 OK`: Successfully retrieved numbers.
      - `204 NO CONTENT`: No numbers found.
![get](assets/images/get.png)

- **PUT /updateById/{id}** - Update an existing list of numbers by ID.
  - Request Body:
    ```json
    {
      "numbers": [111]
    }
    ```
  - **Response**: JSON object of the updated Nums entity.
      - **Status Codes**:
        - `200 OK`: Successfully updated numbers.
        - `404 NOT FOUND`: ID not found.
        - `400 BAD REQUEST`: Invalid request body.
        ![post](assets/images/post.png)
        ![getNew](assets/images/getNew.png)

- **DELETE /deleteNumById/{id}** - Delete a list of numbers by ID.
- **Response**: JSON array of all `Nums` entities using service.get();
  - **Path Variable**: id - ID of the Nums entity to delete.
  - **Status Codes:**
    - `200 OK:` Successfully deleted numbers.
    - `204 NO CONTENT`: No numbers found.
    
## Testing

These tests cover a variety of scenarios to ensure that the NumController and NumService components function correctly. They check for successful operations, validate proper responses to invalid inputs, and confirm that exceptions are thrown in appropriate cases.

- **Run all tests**:
  ```bash 
    mvn clean test
  ```

The tests include:
### 1. `NumControllerIntegrationTest`

This class contains integration tests for the `NumController`, verifying the behavior of the controller endpoints when performing operations like adding, retrieving, and deleting numbers. It uses `MockMvc` to simulate HTTP requests and responses.

- **`testAddNums_SavesEachNumberSeparately`**:
    - Tests the `/numbers` endpoint with a valid JSON payload containing a list of numbers `[1, 2, 3, 4, 5]`.
    - Confirms that each number is saved separately and that the response contains an array of size 5, indicating all numbers were saved successfully.

- **`testPostingWrongValue`**:
    - Tests the `/numbers` endpoint with invalid JSON payloads that include incorrect values (like `null` or an invalid character).
    - Verifies that the response returns a `400 Bad Request` status for these cases, ensuring proper error handling for invalid input data.

- **`testGetNums_ReturnsAllStoredNumbers`**:
    - Checks the `/get` endpoint to verify that initially, it returns `204 No Content` when no numbers are stored.
    - Saves a list of numbers `[1, 2, 3]` to the database, and then verifies that the `/get` endpoint returns these numbers in the correct order.

- **`testDeleteNums_DeletesStoredNumber`**:
    - Saves a number in the database, confirms its presence using a GET request, then deletes it with a DELETE request to `/deleteNumById/{id}`.
    - Ensures that the number is deleted successfully and returns a `204 No Content` response status after deletion.
### 2. `NumServiceTest`
This class contains unit tests for the `NumService` class, which is responsible for business logic related to managing numbers. It uses Mockito to mock dependencies and verify the behavior of the service methods.

- **`testGetNums_ShouldReturnNumsList`**:
    - Mocks the `NumDAO` to return a list of numbers `[1, 2]`.
    - Verifies that `getNums()` correctly returns this list, ensuring proper retrieval of numbers from the database.

- **`testGetNums_ShouldThrowNoContentException`**:
    - Mocks the `NumDAO` to return an empty list.
    - Confirms that `getNums()` throws a `NoContentException` when the database is empty, ensuring the service handles this case appropriately.

- **`testPostNums_ShouldSaveNums`**:
    - Mocks the `NumDAO` to save each number provided in a `NumDTO` object.
    - Verifies that each number in the list is saved individually and that the size of the returned list matches the input, ensuring proper handling of batch saving.

- **`testDeleteNums_ShouldThrowNumNotFoundException`**:
    - Mocks the `NumDAO` to indicate that a specific ID does not exist.
    - Verifies that `deleteNums()` throws a `NumNotFoundException` when trying to delete a non-existent number, ensuring error handling for invalid delete requests.

## Project Structure

```plaintext
Nets-demo
├── .idea
├── .mvn
├── assets
│   └── images
│       ├── get.png
│       ├── getNew.png
│       ├── numbers.png
│       └── post.png
├── src
│   ├── main
│   │   └── java
│   │       └── com.example.Nets_demo
│   │           ├── controller
│   │           │   └── NumController.java
│   │           ├── DAO
│   │           │   └── NumDAO.java
│   │           ├── DTO
│   │           │   └── NumDTO.java
│   │           ├── Exceptions
│   │           │   ├── NoContentException.java
│   │           │   ├── NumNotFoundException.java
│   │           │   └── WrongDataTypeException.java
│   │           ├── model
│   │           │   └── Nums.java
│   │           └── service
│   │               ├── NumService.java
│   │               └── NetsDemoApplication.java
│   └── resources
├── test
│   └── java
│       └── com.example.Nets_demo
│           ├── controller
│           │   └── NumControllerIntegrationTest.java
│           └── service
│               └── NumServiceTest.java
├── .gitattributes
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── springboot_task.md
```

## Technologies Used
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- JUnit and Mockito for testing
