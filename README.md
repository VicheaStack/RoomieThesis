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

## 🛠️ Tech Stack

- **Backend Framework:** Spring Boot (Java)
- **Database:** PostgreSQL (primary), Redis (caching & session)
- **Security:** Spring Security, OAuth2 (Google), JWT
- **Real-time:** WebSockets
- **File Storage:** Cloudinary
- **Build Tool:** Maven
- **Documentation:** Swagger (SpringDoc OpenAPI)
- **Other:** Lombok, Jakarta Persistence (JPA), Hibernate

---

## 📦 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL & Redis instances
- Cloudinary account (for image uploads)
- Google Cloud Console project (for OAuth2)

### Configuration

1. Clone the repo:
   ```bash
   git clone https://github.com/VicheaStack/RoomieThesis.git
   cd RoomieThesis
