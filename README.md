# eCommerce Platform - Spring Boot Application

## Overview
The **eCommerce Platform** is a robust, scalable, and extensible web-based application designed to handle core e-commerce functionalities such as user management, product catalog, cart management, order processing, and payment handling.

This project was developed using **Spring Boot**, adhering to **clean code principles**, **SOLID design**, and Java **best practices**. The application is modular, clean, and ready for extension, making it suitable for developers aiming to modify or enhance it in the future.

---

## Features
- **User Authentication:** Register and login functionality with security.
- **Product Management:** CRUD operations for products.
- **Cart Management:** Add, update, and remove items from the cart.
- **Order Processing:** Create, view, and manage orders.
- **Payment Processing:** Simulated payment service using different strategies.
- **Global Exception Handling:** Custom exceptions with meaningful messages.
- **Logging:** Tracks key events for auditing.
- **Swagger API Documentation:** Integrated OpenAPI documentation.

---

## Technologies Used
- **Java 23**
- **Spring Boot** (Web, Security, JPA, Validation)
- **MapStruct** (Object mapping)
- **JUnit 5** (Unit testing)
- **Mockito** (Mocking)
- **H2 Database** (For testing purposes)
- **Swagger** (API Documentation)

---

## Clean Code & SOLID Design Principles
This project embraces **clean code** practices and follows the **SOLID** design principles:

1. **Single Responsibility:** Each class is responsible for a single functionality, making it easier to maintain and extend.
2. **Open/Closed Principle:** The project is designed to be open for extension (e.g., adding new features) but closed for modification.
3. **Liskov Substitution:** Abstractions are used correctly to allow for substitutable implementations.
4. **Interface Segregation:** Interfaces are fine-grained and not overloaded with unnecessary methods.
5. **Dependency Inversion:** High-level modules depend on abstractions rather than concrete implementations.

---

## Design Patterns Used
- **Factory Pattern:** Implemented in the `PaymentService` to handle multiple payment methods dynamically and extensibly.
- **Mapper Pattern:** Using **MapStruct** for mapping between DTOs and entities.
- **Exception Handling Pattern:** Centralized global exception handler (`GlobalExceptionHandler`) for clean and maintainable error handling.
- **Service Layer Abstraction:** All business logic is encapsulated in the service layer to keep controllers clean and focused on request handling.

---

## Project Structure
```
├── src/main/java/com/ecommerce_platform
│   ├── api
│   │   ├── controller        # API controllers for handling HTTP requests
│   │   └── dto               # DTOs for requests and responses
│   ├── infra
│   │   ├── config            # Application configurations (CORS, Security, Swagger)
│   │   ├── exception         # Custom exceptions and global handlers
│   │   ├── mapper            # MapStruct mappers for DTO conversion
│   │   └── util              # Utility classes (logging, email handling)
│   ├── repository            # Repositories for database access
│   ├── service               # Business logic
│   └── ECommercePlatformApplication.java
├── src/main/resources
│   └── application.yml       # Configuration file
└── pom.xml                   # Maven dependencies
```

---

## API Endpoints
The application exposes the following RESTful endpoints:

### **Authentication Endpoints**
| HTTP Method | Endpoint       | Description                    |
|-------------|----------------|--------------------------------|
| POST        | /api/auth/register | Register a new user            |
| POST        | /api/auth/login    | Authenticate a user            |

### **User Endpoints**
| HTTP Method | Endpoint              | Description                  |
|-------------|-----------------------|------------------------------|
| GET         | /api/users/{userId}   | Get user by ID               |
| PUT         | /api/users/{userId}   | Update user email            |
| DELETE      | /api/users/{userId}   | Delete user                  |

### **Product Endpoints**
| HTTP Method | Endpoint             | Description                   |
|-------------|----------------------|-------------------------------|
| POST        | /api/products        | Create a new product          |
| GET         | /api/products        | Retrieve all products         |
| PUT         | /api/products/{id}   | Update product by ID          |
| DELETE      | /api/products/{id}   | Delete product by ID          |

### **Cart Endpoints**
| HTTP Method | Endpoint                 | Description                  |
|-------------|--------------------------|------------------------------|
| POST        | /api/carts/add           | Add a product to cart        |
| PUT         | /api/carts/update        | Update cart item quantity    |
| DELETE      | /api/carts/remove        | Remove item from cart        |
| GET         | /api/carts/items         | Retrieve user's cart items   |

### **Order Endpoints**
| HTTP Method | Endpoint             | Description                   |
|-------------|----------------------|-------------------------------|
| POST        | /api/orders          | Create a new order            |
| GET         | /api/orders          | Retrieve all orders           |

### **Payment Endpoints**
| HTTP Method | Endpoint             | Description                   |
|-------------|----------------------|-------------------------------|
| POST        | /api/payments/process| Process a payment             |

---

## Exception Handling
- Centralized error handling is provided through the `GlobalExceptionHandler`, which handles exceptions like:
  - `UserNotFoundException`
  - `ProductNotFoundException`
  - `OutOfStockException`
  - `PaymentFailedException`

This ensures that API responses are consistent and user-friendly.

---

## Swagger Integration
Swagger is integrated using **Springdoc OpenAPI** to automatically generate API documentation.
- To access the Swagger UI, navigate to:
```
http://localhost:8090/swagger-ui/index.html
```
- The documentation includes all endpoints, request/response models, and descriptions.

---

## Clean Code Practices
The following clean code principles were adhered to:
- Meaningful method and variable names.
- Classes and methods are small, focused, and well-structured.
- DTOs and entities are separated to prevent accidental data leaks.
- Logging is provided for key events using `LoggerUtil`.

### **Why Use Request and Response Records Instead of Classic DTOs?**
In this project, we use **Java records** for request and response objects instead of classic DTOs (Data Transfer Objects). This decision was made to leverage the simplicity and immutability of records, enhancing maintainability and security.

#### **Advantages of Using Records:**
1. **Conciseness:** Java records reduce boilerplate code since getters, constructors, `equals()`, `hashCode()`, and `toString()` methods are automatically generated by the compiler.
2. **Immutability:** Records are inherently immutable, ensuring that the state of a request or response cannot be modified after creation, which improves data integrity.
3. **Readability:** Using records makes the code cleaner and more readable, as developers can focus on the essential structure of data without distractions from verbose boilerplate.
4. **Security:** By making request and response objects immutable, the risk of unintended or malicious modifications to data is minimized.

#### **Why Not Classic DTOs?**
Classic DTOs often involve manually writing boilerplate code for getters, setters, and other methods. This not only increases code size but also introduces the possibility of errors such as inconsistent method overrides or mutable state issues. While DTOs are still common, the use of Java records represents a more modern and efficient approach to handling data.

### **Example:**
**Classic DTO:**
```java
public class PaymentRequestDTO {
    private Long orderId;
    private BigDecimal amount;
    private String paymentMethod;

    public PaymentRequestDTO(Long orderId, BigDecimal amount, String paymentMethod) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public Long getOrderId() { return orderId; }
    public BigDecimal getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
}
```

**Record-based Request:**
```java
public record PaymentRequest(
        Long orderId,
        BigDecimal amount,
        String paymentMethod
) {}
```
As seen above, the record-based approach is much cleaner, and the compiler automatically handles everything behind the scenes.

---

## Ready for Extension
This project is built with extensibility in mind:
- **Payment Methods:** New payment methods can be added by implementing new handlers using the Factory Pattern.
- **User Roles:** New user roles can be added without changing core logic.
- **API Expansion:** Easily extendable with new API endpoints.

---

## Best Practices Followed
- **Dependency Injection:** Using Spring's dependency injection for loose coupling.
- **Validation:** Input validation using annotations like `@NotNull`, `@Size`, etc.
- **Layered Architecture:** Separation of concerns between controllers, services, repositories, and configuration.
- **Unit Testing:** Coverage with **JUnit 5** and **Mockito**.
- **Caching:** Selective caching in repositories to improve performance.

---

## Running the Application
To run the application locally:
1. Clone the repository:
```
git clone https://github.com/your-repository/eCommerce-Platform.git
```
2. Navigate to the project directory:
```
cd eCommerce-Platform
```
3. Build the project:
```
mvn clean install
```
4. Run the application:
```
mvn spring-boot:run
```
5. Open the Swagger UI to test the APIs.

---

## Future Enhancements
- Integrate external payment gateways (e.g., Stripe, PayPal).
- Implement user notifications (email or SMS) for key events.
- Add advanced search and filtering options for products.
- Optimize database queries and introduce asynchronous processing where needed.

---

## Contributors
- **Abdl02**