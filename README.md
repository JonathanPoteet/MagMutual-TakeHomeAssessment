# MagMutual-TakeHomeAssessment

## Setup

### Prerequisites
- Java 21
- Maven
- Node.js 20+
- Docker Desktop or Docker Engine (if running with docker)

### Run locally without Docker
From the backend folder:

```bash
cd assessment-backend
./mvnw spring-boot:run
```

From the frontend folder in a separate terminal:

```bash
cd assessment-frontend
npm install
npm start
```

The frontend expects the backend to be running on port 8080.

### Run with Docker Compose
From the project root:

```bash
docker compose up --build
```

This is configured for local development only. The backend is kept local to the machine and is intended to be accessed via `localhost`.

This will build and start:
- backend on http://localhost:8080
- frontend on http://localhost:3000
- Swagger UI for the backend at http://localhost:8080/swagger-ui/index.html

---

## Requirements
In the accompanying CSV file, you will find a list of Users. We want you to build a simple microservice that exposes a couple endpoints and a frontend to display the returned information.

Tech Stack:

· The microservice has been built using Java Spring Boot

· The frontend has been built using ReactJS in Typescript

Base features:

· Endpoint to return all users (backend - Done, Frontend - Done)

· Endpoint to return a specific user (backend - Done, Frontend - Done)

· User information displayed on the frontend in a visually appealing way (Done)

Optional features (choose at least 2 of the 4):

1. Ability to create and delete users (backend - Done, Frontend - Done)

2. Ability to filter, sort, and search users (Done)

3. Create a custom endpoint of your own to transform or visualize the data in a different way. This should also include a corresponding frontend component/display. (Done)

4. Create an ERD that shows how you would go about accommodating the following user request: (WIP)

    - Request: HR needs to associate users with various Companies. Can you model what the data would look like?

    -- We know a Company will need the following information:

    -- 1. name

    -- 2. location

    -- 3. phone number

    -- 4. email

    -- 5. employees

Deliverables:

· A link to the source code and any additional artifacts. Please send this over at least 24hrs prior to next interview round.

· Be prepared to demo & discuss your work



Out of Scope
- Caching
- Authentication
- Backend sorting/filtering
- Elaborate unit tests
- Elaborate input filtering
- Deployment environments
- Additional UI/UX polish