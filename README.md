# Todo List Service

## 1. Service Description
This project is a backend service for managing a **to-do list**, implemented using **Java** and **Spring Boot**.  
It provides a REST API that allows clients to create to-do items, update their status and/or description, and retrieve them based on specific conditions.

Key features:
- Add new items with description and due date.
- Update an item's description.
- Update an item's status, the available values are **DONE** and **NOT_DONE**.
- Retrieve a single item by ID.
- Retrieve all items.
- Retrieve all items filtering by status.
- Automatic status update: items that pass their due date are automatically marked as **PAST_DUE**.
- Immutable past-due tasks (no modifications allowed once status is **PAST_DUE**).

Assumptions:
- No user authentication/authorization is required.
- Service is runnable via Docker.
- Persistence uses an in-memory **H2 database** for simplicity.

---

## 2. Technical Stack
- **Java 21**
- **Spring Boot 3.5.5**
    - `spring-boot-starter-web` (REST API)
    - `spring-boot-starter-validation` (input validation)
    - `spring-boot-starter-data-jpa` (ORM & persistence)
    - `spring-boot-starter-test` (testing framework)
- **H2 Database 2.2.224** (in-memory persistence)
- **Lombok 1.18.38** (boilerplate reduction)
- **JUnit Platform Suite 1.13.4** (test suite aggregation)

---

## 3. How-To Guide
Make sure you have **Java 21**, **Maven 3.9+** and **docker** installed.

### Build the Service
```bash
mvn clean install
```

### Run the Test Suite
```bash
mvn test
```

### Start your Application via Docker
```bash
docker compose up --build
```
Your application will be available at http://localhost:8080.


## 4. REST API Reference

###  Add a new To-Do item
**POST** `/api/v1/items`

**Request body**
```json
{
  "description":"Buy groceries",
  "due_datetime": "2025-02-01T00:00:00"
}
```
**Response**
```json
{
  "id": "3278fb20-d3bb-4e9b-97a8-09435fb33fd9",
  "description": "Buy groceries",
  "status": "NOT_DONE",
  "creation_datetime": "2025-01-01T00:00:00",
  "due_datetime": "2025-02-01T00:00:00",
  "done_datetime": null
}
```
**Validations**
- **description**
    - Required (must not be blank).
    - Maximum length: 255 characters.
  
- **due_datetime**
  - Must not be null.
  - Must be in the future or present.
  


###  Update an existing To-Do item
**PATCH** `/api/v1/items/{id}`

This method can be used to update the description and to mark the item as DONE/NOT_DONE.

**Request body**
```json
{
  "description":"Buy groceries",
  "status": "DONE"
}
```
**Response**
```json
{
  "id": "14cf6a7e-befb-43a2-b132-9784ef10dfa5",
  "description": "Buy groceries",
  "status": "DONE",
  "creation_datetime": "2025-01-01T00:00:00",
  "due_datetime": "2025-02-01T00:00:00",
  "done_datetime": "2025-01-10T00:00:00"
}
```
**Validations**

- **description**
    - Maximum length: 255 characters.

- **due_datetime**
    - Allowed values **DONE** and **NOT_DONE**.
- **At least one of the fields, description or due_datetime, must be not null** 



###  Get an existing To-Do item
**GET** `/api/v1/items/{id}`

**Response**
```json
{
  "id": "14cf6a7e-befb-43a2-b132-9784ef10dfa5",
  "description": "Buy groceries",
  "status": "DONE",
  "creation_datetime": "2025-01-01T00:00:00",
  "due_datetime": "2025-02-01T00:00:00",
  "done_datetime": "2025-01-10T00:00:00"
}
```


###  Get To-Do items
**GET** `/api/v1/items` 

**GET** `/api/v1/items?status=NOT_DONE`

**GET** `/api/v1/items?status=size=10&page=0`

This method can be used to get all the items, as well as get all items by status. Besides of implementing pagination.

**Response**
```json
{
  "content": [
    {
      "id": "a7ef021b-1c11-4461-b1ec-a1d8048a14ae",
      "description": "Buy groceries",
      "status": "DONE",
      "creation_datetime": "2025-01-01T00:00:00",
      "due_datetime": "2025-02-01T00:00:00",
      "done_datetime": "2025-01-10T00:00:00"
    }
  ],
  "page": {
    "size": 1,
    "number": 0,
    "totalElements": 4,
    "totalPages": 4
  }
}
```

  
