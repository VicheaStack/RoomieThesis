**✅ Here is the clean README content ready to copy and paste:**

```markdown
# 🏠 RoomieHub - Rental Platform Backend

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat&logo=openjdk) 
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.4-6DB33F?style=flat&logo=spring) 
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13+-4169E1?style=flat&logo=postgresql)

**RoomieHub** is a full-featured backend REST API for a modern room/apartment rental platform. Built with **Java 21 + Spring Boot 4**, it handles the complete rental lifecycle — from user management and room listings to bookings, reviews, real-time messaging, and admin tools.

This project was developed as my **thesis** and is my main **portfolio project** to demonstrate strong backend development skills.

## ✨ Key Features

- User Management (Registration, JWT + Google OAuth2, roles: RENTER/OWNER/ADMIN)
- Room Listings with pagination, search & filtering
- Booking System with date validation and availability checks
- Reviews & Ratings (one review per booking)
- Favorites, Real-time Messaging (WebSocket + STOMP)
- Cloudinary CDN for photo uploads
- Audit Logging with JSON diff tracking
- System Settings, Amenities, Owner Profiles
- Role-based Access Control

**Total API Endpoints**: **65+**

## 🧱 Architecture

- Clean Layered Architecture (Controller → Service → Repository)
- DTO pattern only (Entities never exposed to controllers)
- MapStruct for object mapping
- Proper relationship handling to avoid N+1 queries
- Modular and maintainable code structure

## 🔐 Security

- JWT Authentication
- OAuth2 Google Login
- BCrypt password encoding
- Role-based authorization with Spring Security

## ⚙️ Tech Stack

| Category           | Technology                              |
|--------------------|-----------------------------------------|
| Language           | Java 21                                 |
| Framework          | Spring Boot 4.0.4                       |
| Security           | Spring Security + JWT + OAuth2          |
| Database           | PostgreSQL + Flyway                     |
| ORM                | JPA / Hibernate                         |
| Mapping            | MapStruct + Lombok                      |
| Image Storage      | Cloudinary                              |
| Real-time          | WebSocket (STOMP)                       |
| Documentation      | SpringDoc OpenAPI / Swagger             |
| Containerization   | Docker + Docker Compose                 |
| Build Tool         | Maven                                   |

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/VicheaStack/RoomieThesis.git
cd RoomieThesis
```

### 2. Configure Environment Variables
Set the following variables (recommended to use `application-dev.yml`):

```env
DB_URL=jdbc:postgresql://localhost:5432/roomiehub
DB_USERNAME=your_username
DB_PASSWORD=your_password

JWT_SECRET=your_very_strong_secret_here   # Generate: openssl rand -base64 32

# Google OAuth2 (Optional)
GOOGLE_CLIENT_ID=...
GOOGLE_CLIENT_SECRET=...

# Cloudinary
CLOUDINARY_CLOUD_NAME=...
CLOUDINARY_API_KEY=...
CLOUDINARY_API_SECRET=...
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

Application will start at **`http://localhost:8080`**

Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Docker
```bash
docker-compose up --build
```

## 🧪 Development & Testing

- Fixed **32+ JPA/Hibernate issues** during development
- Over 60 endpoints tested with Postman
- Basic Unit Tests (JUnit + Mockito)
- Audit logging for major operations

## 🗺️ Future Improvements

- Complete Integration Tests
- Email notifications
- Rate limiting
- CI/CD with GitHub Actions
- React/Vue frontend integration

## 📄 License
GPL-3.0

---

**Built with ❤️ by Leng Chan Vichea**
```

---

**How to use:**
1. Go to your repository → `README.md`
2. Replace all the old content with the text above
3. Commit and push

Would you like any modifications (add screenshots section, make it shorter, change anything, etc.)? Just tell me!
