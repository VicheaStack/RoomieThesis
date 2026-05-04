# 🏠 RoomieHub

RoomieHub is a full-featured room rental platform backend built with Spring Boot. It connects room owners with renters, offering secure user management, real-time messaging, and a seamless booking and payment experience.

---

## 🚀 Features

- **User Management**  
  - Registration/Login with email & password (BCrypt encryption)  
  - Google OAuth2 social login  
  - Role-based access control (OWNER, RENTER, ADMIN)  
  - Stateless JWT authentication

- **Room Listings**  
  - CRUD operations for rooms (owners only)  
  - Detailed room info: title, description, price, location, type, amenities  
  - Image upload via Cloudinary  
  - Favorite rooms functionality

- **Bookings & Payments**  
  - Renters can book rooms for a date range  
  - Automatic total price calculation  
  - Payment record with transaction ID and method

- **Communication & Social**  
  - Real-time private messaging via WebSockets  
  - User notifications (booking updates, messages)  
  - Star ratings and reviews for completed stays

- **Admin Console**  
  - Manage all users and profiles  
  - View audit logs for system activity

- **API Documentation**  
  - Swagger/OpenAPI integration for easy API exploration

---

## 🛠️ Technology Stack

| Category                | Technology                                                                 |
|-------------------------|----------------------------------------------------------------------------|
| **Language**            | Java 21                                                                    |
| **Framework**           | Spring Boot 4.0.4                                                          |
| **Web**                 | Spring Web MVC (REST), WebSocket                                           |
| **Database**            | PostgreSQL (primary), H2 (dev/test)                                        |
| **Database Migrations** | Flyway                                                                     |
| **ORM**                 | Spring Data JPA (Hibernate)                                                |
| **Security**            | Spring Security, OAuth2 Client & Resource Server, JWT (jjwt 0.12.5), BCrypt |
| **File Storage**        | Cloudinary                                                                 |
| **Object Mapping**      | MapStruct + Lombok binding                                                 |
| **API Documentation**   | SpringDoc OpenAPI (Swagger UI)                                             |
| **Validation**          | Bean Validation (Hibernate Validator)                                      |
| **Environment Config**  | spring-dotenv (.env files)                                                 |
| **Dev Tools**           | Spring Boot DevTools (hot reload)                                          |
| **Build**               | Maven                                                                      |

---

## 📦 Getting Started

### Prerequisites

- Java 21
- Maven 3.8+
- PostgreSQL instance (H2 can be used for development)
- Cloudinary account (for image uploads)
- Google Cloud Console project (for OAuth2)

### Configuration

1. Clone the repo:
   ```bash
   git clone https://github.com/VicheaStack/RoomieThesis.git
   cd RoomieThesis
