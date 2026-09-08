## HR Data Model Architecture (ERD)

Request: HR needs to associate users with various Companies. Can you model what the data would look like?

- We know a Company will need the following information:

- 1. `name`

- 2. `location`

- 3. `phone number`

- 4. `email`

- 5. `employees`


### Direct Many-to-Many Relationship (This is the situation I recommend for this specific scenario)
* **Use Case:** A single user can belong to many companies at a time. A single user may be associated with multiple companies, and a company may have multiple users.

```mermaid
erDiagram
    USERS ||--o{ EMPLOYEES : "has"
    COMPANIES ||--o{ EMPLOYEES : "employs"

    USERS {
        int id PK
        string firstname
        string lastname
        string email
        string profession
        string dateCreated
        string country
        string city
    }

    COMPANIES {
        int id PK
        string name
        string location
        string phone_number
        string email
    }

    EMPLOYEES {
        int id PK
        int user_id FK
        int company_id FK
        date start_date
        date end_date
    }
    ```

    Additional attributes may be added to the EMPLOYEES association table as needed, such as `manager_id`, `company_role`, and `pay_level`