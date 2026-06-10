# Enviro365 Investor Withdrawal System

A full-stack investor withdrawal management system built with Spring Boot, React, H2 Database, and REST APIs.

## Developer

Surprise Ramatlhare

## Project Overview

This system allows investors to view their investment portfolio, create withdrawal notices, validate withdrawal requests against business rules, view withdrawal history, and export withdrawal statements as CSV files.

## Technology Stack

### Backend

* Java 21
* Spring Boot
* Spring Data JPA
* H2 Database
* Maven
* Bean Validation
* JUnit 5
* Mockito
* OpenAPI

### Frontend

* React
* Vite
* Axios
* CSS

## Core Features

* View investor portfolio
* Create withdrawal notices
* Validate withdrawal rules
* View withdrawal history
* Export withdrawal history as CSV
* Global exception handling
* DTO layer
* Unit tests
* Professional React dashboard

## Business Rules

* Retirement withdrawals are only allowed if the investor is older than 65.
* Withdrawal amount cannot exceed the available product balance.
* Withdrawal amount cannot exceed 90% of the product balance.
* A product must belong to the selected investor.

## API Endpoints

| Method | Endpoint                        | Description               |
| ------ | ------------------------------- | ------------------------- |
| GET    | `/api/investors/{id}/portfolio` | Get investor portfolio    |
| POST   | `/api/withdrawals`              | Create withdrawal notice  |
| GET    | `/api/withdrawals`              | Get withdrawal history    |
| GET    | `/api/withdrawals/export`       | Export withdrawals as CSV |
| GET    | `/v3/api-docs`                  | OpenAPI documentation     |

## How to Run Backend

```bash
cd backend/enviro365-backend
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

H2 Console:

```text
http://localhost:8080/h2-console
```

## How to Run Frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend runs on:

```text
http://localhost:5173
```

## Test Credentials / Sample Investors

| Investor            | Age | Retirement Withdrawal |
| ------------------- | --: | --------------------- |
| Surprise Ramatlhare |  67 | Allowed               |
| Kabelo Madisha      |  69 | Allowed               |
| Thato Singo         |  45 | Restricted            |

## Testing

Run backend tests:

```bash
mvn test
```

The project includes service-layer unit tests for investor portfolio retrieval and withdrawal business rules.

## Screenshots

Add screenshots inside the `screenshots` folder and reference them here.

Example:

```markdown
## Screenshots

### Dashboard Overview

The main dashboard displaying investor statistics, portfolio value, navigation menu, and CSV export functionality.

![Dashboard Overview](screenshots/01-dashboard-overview.png)

---

### Investor Portfolio

Displays investor information, eligibility status, and available investment products.

![Investor Portfolio](screenshots/02-investor-portfolio.png)

---

### Withdrawal Notice Form

Allows investors to create withdrawal notices while enforcing business validation rules.

![Withdrawal Notice Form](screenshots/03-withdrawal-form.png)

---

### Withdrawal History

Displays all approved withdrawal notices and provides CSV export functionality.

![Withdrawal History](screenshots/04-withdrawal-history.png)

---

### Investor Selection

Demonstrates support for multiple investors within the system.

![Investor Selection](screenshots/05-investor-selection.png)

---

### Product Selection

Shows available investment products linked to the selected investor.

![Product Selection](screenshots/06-product-selection.png)

---

### Withdrawal Success Confirmation

Confirmation dialog displayed after a withdrawal notice is successfully created.

![Withdrawal Success](screenshots/07-withdrawal-success.png)

---

### CSV Export Output

Example of the generated withdrawal statement exported in CSV format and opened in Microsoft Excel.

![CSV Export](screenshots/08-csv-export.png)

```
## AI Usage

AI assistance was used during development for guidance, debugging support, code review, and documentation improvement. All code was reviewed, tested, and understood before submission.