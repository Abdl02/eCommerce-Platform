# eCommerce Platform Project

## **Project Overview**
The eCommerce Platform project is a fully functional online shopping system built using **Spring Boot**, **JPA**, **MapStruct**, and various key design patterns. It provides essential features for managing products, users, orders, carts, and payments while ensuring scalability and extensibility.

## **Key Features**
- **User Management:** Registration, authentication, and user profile management.
- **Product Management:** CRUD operations for products.
- **Cart Management:** Add, update, and remove items in the cart.
- **Order Processing:** Create and track customer orders.
- **Payment Integration:** Process payments and validate payment amounts.
- **Swagger API Documentation:** Automatically generated API documentation using OpenAPI.

---

## **Project Architecture**

### **Layered Architecture:**
The project is divided into several key layers to achieve separation of concerns:

1. **Controller Layer:** Handles incoming API requests and forwards them to the service layer.
    - Example: `AuthController`, `ProductController`

2. **Service Layer:** Contains the business logic of the application.
    - Example: `AuthService`, `OrderService`

3. **Repository Layer:** Interacts with the database using Spring Data JPA.
    - Example: `UserRepository`, `ProductRepository`

4. **Entity Layer:** Represents the database tables.
    - Example: `User`, `Product`

5. **DTO Layer:** Defines the data transfer objects to encapsulate data sent to/from APIs.
    - Example: `RegisterRequest`, `ProductResponse`

6. **Mapper Layer:** Converts between entities and DTOs using **MapStruct**.
    - Example: `UserMapper`, `ProductMapper`

---

## **Key Design Patterns**

### 1. **Factory Method Pattern** (Applied in Payment Processing)
- The `PaymentServiceFactory` dynamically selects the appropriate `PaymentService` implementation based on the payment method.
- **Benefits:** Enhances extensibility and isolates changes to specific implementations.

### 2. **Singleton Pattern** (Logger Utility)
- The `LoggerUtil` class follows the Singleton pattern, ensuring that a single instance is used throughout the application to write logs.

### 3. **Exception Handling (Global Exception Handling)**
- Centralized exception handling is implemented using the `GlobalExceptionHandler` with specific custom exceptions like `UserNotFoundException` and `PaymentFailedException`.

---

## **Dependencies and Technologies**
- **Spring Boot**: Core framework for the backend application.
- **Spring Data JPA**: Data persistence and interaction with the database.
- **H2 Database**: In-memory database for development and testing.
- **MapStruct**: Mapper framework to convert between entities and DTOs.
- **Spring Security**: Secures the application with authentication and authorization.
- **OpenAPI/Swagger**: API documentation.
- **Lombok**: Reduces boilerplate code for POJOs.

---

## **Setting Up the Project**

### **Prerequisites:**
- JDK 17 or higher
- Maven

### **Steps to Run:**
1. Clone the repository:
    ```sh
    git clone https://github.com/Abdl02/eCommerce-Platform.git
    cd eCommerce-Platform
    ```

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

4. Access the API documentation at:
   [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## **Key Endpoints**
### **Authentication**
| Endpoint              | HTTP Method | Description                          |
|----------------------|-------------|--------------------------------------|
| `/api/auth/register` | POST        | Register a new user                  |
| `/api/auth/login`    | POST        | Log in a user                        |

### **Product Management**
| Endpoint             | HTTP Method | Description                          |
|---------------------|-------------|--------------------------------------|
| `/api/products`      | GET         | Retrieve all products                |
| `/api/products`      | POST        | Create a new product                 |
| `/api/products/{id}` | PUT         | Update an existing product           |
| `/api/products/{id}` | DELETE      | Delete a product by ID               |

### **Order Management**
| Endpoint             | HTTP Method | Description                          |
|---------------------|-------------|--------------------------------------|
| `/api/orders`        | POST        | Create a new order                   |
| `/api/orders`        | GET         | Retrieve all orders                  |

### **Cart Management**
| Endpoint               | HTTP Method | Description                          |
|-----------------------|-------------|--------------------------------------|
| `/api/carts/add`      | POST        | Add a product to the cart            |
| `/api/carts/update`   | PUT         | Update the quantity of a cart item   |
| `/api/carts/remove`   | DELETE      | Remove a product from the cart       |

---

## **Exception Handling**
The project has centralized exception handling through the `GlobalExceptionHandler`.
### Key exceptions include:
- `UserNotFoundException`
- `ProductNotFoundException`
- `OutOfStockException`

These exceptions are caught globally, and the API returns meaningful error messages with appropriate HTTP status codes.

---

## **Testing**
Unit and integration tests are written using **JUnit 5** and **Mockito**.

### **Key Test Classes:**
- `AuthServiceTest`
- `CartServiceTest`
- `OrderServiceTest`
- `PaymentServiceTest`
- `ProductServiceTest`

To run the tests:
```sh
mvn test
```

---

## **Security**
The project uses **Spring Security** to secure endpoints, including authentication and role-based access control.
- `/api/auth/**` is public.
- Other endpoints require authentication.

## **Future Enhancements**
- **Payment Gateway Integration:** Integrate third-party payment gateways (e.g., Stripe, PayPal).
- **Email Notifications:** Implement email confirmation using a proper email server.
- **Role-based Authorization:** Expand access controls to admin-specific features.

## **Contributing**
1. Fork the repository.
2. Create a new feature branch.
3. Submit a pull request.

---

## **License**
This project is licensed under the MIT License.