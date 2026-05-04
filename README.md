```markdown
# 🏠 RoomieHub – Rental Platform Backend

RoomieHub is a full‑featured rental backend built with **Java 21 + Spring Boot 4.0.4**. It covers the entire lifecycle of a modern rental platform: user registration, room listings, bookings, reviews, favourites, messaging, photo management, admin tools, and OAuth2 login – all secured with JWT.

## ✨ Features at a Glance

| Domain           | Endpoints | Description |
|------------------|-----------|-------------|
| Users            | 6         | Tenant/Owner registration, CRUD, role‑based access |
| Rooms            | 5         | Full CRUD with pagination, owner‑only creation |
| Bookings         | 5         | Auto‑pricing, date validation, renter‑only booking |
| Reviews          | 5         | One review per booking, rating system |
| Favorites        | 7         | Add, remove, check, list by renter, list all |
| Messages         | 3         | Send, get messages, mark as read |
| Photos           | 3         | CRUD for photo metadata |
| CDN Upload       | 1         | Cloudinary image upload |
| Amenities        | 5         | CRUD for room amenities |
| Owner Profile    | 6         | Create, rate, update, delete |
| Notifications    | 2         | Create & delete notifications |
| System Settings  | 6         | Key‑value config with typed getters |
| Admin Profile    | 5         | Admin profile creation, listing, update, deletion |
| Audit Log        | 5         | Action logging with JSON diff storage |

All CRUD operations include **pagination**, **role‑based access control**, and clean **DTO mapping**.

## 🧱 Architecture

```
Controller  →  DTO (record / class)
     ↓
Service  →  Business logic, role checks, calculations
     ↓
Repository (Spring Data JPA)  →  PostgreSQL
```

- Controllers **never** return entities – they always map to response DTOs.
- Services receive IDs and fetch relationships themselves to avoid lazy‑loading issues.
- MapStruct handles entity ↔ DTO conversion.
- The application uses `open‑in‑view: true` during development to simplify transaction handling.

## 🔐 Security

- **Stateless JWT** filter for all API calls.
- **OAuth2 login** (Google) with automatic local user creation and JWT generation upon success.
- **BCrypt** password encoding.
- **Role hierarchy**: `RENTER`, `OWNER`, `ADMIN`.
- Swagger endpoints are public; all other endpoints require authentication (except user registration).

Core security files:
- `SecurityConfig.java` – the rulebook
- `JwtUtil.java` – token creation/validation
- `JwtAuthenticationFilter.java` – stateless authentication filter
- `CustomOAuth2UserService.java` – OAuth2 user loading
- `OAuth2SuccessHandler.java` – JWT issuance after social login

## ⚙️ Tech Stack

| Category          | Technology                         |
|-------------------|------------------------------------|
| Language          | Java 21                            |
| Framework         | Spring Boot 4.0.4, Spring Security 7.x |
| Database          | PostgreSQL                         |
| ORM               | Hibernate / JPA                    |
| Object Mapping    | MapStruct                          |
| Authentication    | JWT (stateless) + OAuth2 (Google)  |
| File Storage      | Cloudinary CDN                     |
| Real‑time         | WebSocket (STOMP)                  |
| Documentation     | Swagger / OpenAPI (SpringDoc)      |
| Build Tool        | Maven                              |

## 🚀 Quick Start

### Prerequisites
- Java 21
- PostgreSQL (running on `localhost:5432`)
- Maven
- (Optional) Redis

### 1. Clone the repository
```bash
git clone https://github.com/your-username/RoomieHub.git
cd RoomieHub
git checkout feature/complete-roomiehub-api
```

### 2. Configure environment variables
```env
DB_URL=jdbc:postgresql://localhost:5432/RoomieHub
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password
JWT_SECRET=<base64-encoded 256-bit secret>
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret
```
Generate a JWT secret: `openssl rand -base64 32`

### 3. Run the application
```bash
mvn spring-boot:run
```
The server starts on `http://localhost:8088`. Swagger UI is at `/swagger-ui.html`.

### 4. Test data (optional)
SQL scripts for demo users, rooms, and bookings can be provided. The database structure is maintained automatically with `ddl-auto: update`.

## 🧪 Testing

- **32+ JPA/Hibernate bugs** fixed during development (null constraints, lazy initialization, ambiguous mappings, DTO alignment).
- Over **60 endpoints** tested manually with Postman.
- Pagination, role enforcement, date validation, and duplicate checks all verified.
- Audit Log, Amenities, System Settings, and Cloudinary upload all working.

## 🗺️ Future Roadmap

- ✅ OAuth2 Google login
- ⬜ Email alerts (booking confirmation, etc.)
- ⬜ Moderation endpoints for Admin
- ⬜ Integration tests (JUnit + MockMvc)
- ⬜ Docker Compose for one‑command startup
- ⬜ Package security as a reusable Spring Boot starter
- ⬜ Frontend (React / Vue) integration

## 🤝 Contributing

This project is a personal learning and portfolio piece. If you have ideas or want to contribute, feel free to open an issue or pull request.

## 📄 License

This project is for educational and portfolio purposes. All rights reserved.

---

Built with ❤️ and far too much coffee over a few intense days of debugging.  
*If your back hurts after reading this, take a break – the code will wait.*
```
