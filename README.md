# Fake Store API

Fake Store API is a Spring Boot-based backend application that provides a fake e-commerce store's functionality, including user authentication, product management, and order handling.

## Features

- JWT-based authentication
- User registration and login
- CRUD operations for products, categories, and brands
- Role-based access control
- MongoDB as the database
- Spring Security for authorization
- JavaMailSender for OTP-based verification

## Tech Stack

- **Backend**: Java 17, Spring Boot 3.4.2
- **Database**: MongoDB
- **Authentication**: JWT
- **Build Tool**: Maven
- **Other**: Spring Security, JavaMailSender

## Getting Started

### Prerequisites

- Java 17 or later
- Maven
- MongoDB installed and running
- An SMTP server for email verification

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/tamimsoft/fake-store.git
   cd fake-store
   ```

2. Configure application properties:

   - Update `src/main/resources/application.properties` or `application.yml` with your MongoDB credentials and email SMTP settings.

3. Build and run the project:

   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

## API Endpoints

### Authentication

- `POST /auth/register` - Register a new user
- `POST /auth/login` - Login and receive JWT token

### Products

- `GET /products` - Retrieve all products (public access)
- `GET /products/{id}` - Retrieve a product by ID (secured)
- `POST /products` - Create a new product (admin only)
- `DELETE /products/{id}` - Delete a product (admin only)

### Categories

- `GET /categories` - Retrieve all categories
- `POST /categories` - Create a new category (admin only)

### Brands

- `GET /brands` - Retrieve all brands
- `POST /brands` - Create a new brand (admin only)

## Security

- Uses JWT authentication.
- Public access for `GET /products` and `GET /categories`.
- All other endpoints require authentication.

## Testing

To run tests:

```sh
mvn test
```

## License

This project is open-source and available under the MIT License.

## Contributing

Pull requests are welcome! Open an issue to discuss changes before submitting a PR.

## Contact

- **Author**: Tamim Hasan
- **GitHub**: [github.com/tamimsoft/fake-store](https://github.com/tamimsoft/fake-store)

