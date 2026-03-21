# Ordering By QR Service

Spring Boot backend APIs for user onboarding, order creation, and customer order history.

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- H2 Database
- Maven

## Features Implemented

- First-time user creation API
- Existing user lookup on duplicate create request
- Product seed data for local testing
- Create order API
- Ordered products linked to products and customers
- Past orders API by customer
- Postman collection and environment

## Project Structure

- `src/main/java/com/user/visitorbackend/user`
  User APIs and user persistence
- `src/main/java/com/user/visitorbackend/product`
  Product model and seed data
- `src/main/java/com/user/visitorbackend/order`
  Order header, ordered items, and order APIs
- `src/main/java/com/user/visitorbackend/common`
  Shared exception and error response handling
- `postman`
  Postman collection and environment files

## Run Locally

### Prerequisites

- Java 17 or above
- Maven

### Start the application

```powershell
mvn spring-boot:run
```

If Maven is not available globally, use the local Maven path:

```powershell
.\tools\apache-maven-3.9.9\bin\mvn.cmd spring-boot:run
```

Application URL:

- `http://localhost:8080`

H2 console:

- `http://localhost:8080/h2-console`

JDBC URL:

- `jdbc:h2:mem:visitordb`

## APIs

### 1. Create First Time User

- `POST /api/users/first-time`

Request:

```json
{
  "firstName": "Sai",
  "lastName": "Kumar",
  "email": "sai@example.com",
  "phoneNumber": "9876543210",
  "country": "India",
  "state": "Telangana",
  "city": "Hyderabad",
  "postalCode": "500001",
  "addressLine": "Madhapur",
  "signupSource": "website"
}
```

Notes:

- If the email or phone number already exists, the API returns the existing user details instead of failing.

### 2. Create Order

- `POST /api/orders`

Request:

```json
{
  "customerId": 1,
  "items": [
    {
      "productName": "iPhone 15",
      "quantity": 2,
      "priceFromUi": 70000
    },
    {
      "productName": "AirPods Pro",
      "quantity": 1,
      "priceFromUi": 22000
    }
  ]
}
```

Notes:

- `customerId` must exist in the `users` table.
- Product names must exist in the `products` table.
- Sample products are auto-seeded at application startup for local testing.

### 3. Get Past Orders By Customer

- `GET /api/orders/customer/{customerId}`

Example:

```bash
curl --location "http://localhost:8080/api/orders/customer/1"
```

## Seeded Products For Local Testing

These products are inserted automatically if the `products` table is empty:

- `iPhone 15`
- `AirPods Pro`
- `MacBook Air`

## Postman

Import these files into Postman:

- `postman/visitor-backend.postman_collection.json`
- `postman/visitor-backend-local.postman_environment.json`

## Build

```powershell
mvn compile
```

## Current Domain Design

- One user can have many orders
- One order can have many ordered products
- One product can belong to many ordered products

## Next Possible APIs

- Product CRUD from CRM
- Get order by order id
- Update order status
- Cancel order
- Reorder API
- Customer profile APIs
