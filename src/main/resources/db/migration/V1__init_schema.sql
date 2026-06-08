-- =====================================================
-- 1. USERS
-- =====================================================
CREATE TABLE IF NOT EXISTS users (
                                     user_id          BIGSERIAL PRIMARY KEY,
                                     first_name       VARCHAR(100) NOT NULL,
    last_name        VARCHAR(100) NOT NULL,
    password         VARCHAR(255) NOT NULL,
    email            VARCHAR(100) NOT NULL UNIQUE,
    provider         VARCHAR(50),
    provider_id      VARCHAR(100),
    phone_number     VARCHAR(20),
    profile_photo_url VARCHAR(500),
    role             VARCHAR(20) NOT NULL,
    is_verified      BOOLEAN DEFAULT FALSE,
    is_active        BOOLEAN DEFAULT TRUE,
    last_login       TIMESTAMP,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);

-- =====================================================
-- 2. SYSTEM SETTINGS
-- =====================================================
CREATE TABLE IF NOT EXISTS system_settings (
                                               setting_id     BIGSERIAL PRIMARY KEY,
                                               setting_key    VARCHAR(100) NOT NULL UNIQUE,
    setting_value  TEXT NOT NULL,
    data_type      VARCHAR(50) DEFAULT 'string',
    category       VARCHAR(50) DEFAULT 'general',
    description    TEXT,
    is_public      BOOLEAN DEFAULT FALSE,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by     BIGINT REFERENCES users(user_id) ON DELETE SET NULL
    );

CREATE INDEX idx_system_settings_key ON system_settings(setting_key);

-- =====================================================
-- 3. ROOMS
-- =====================================================
CREATE TABLE IF NOT EXISTS rooms (
                                     room_id            BIGSERIAL PRIMARY KEY,
                                     owner_id           BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    title              VARCHAR(255) NOT NULL,
    description        TEXT,
    price_per_night    DOUBLE PRECISION NOT NULL,
    location           VARCHAR(255) NOT NULL,
    latitude           DOUBLE PRECISION,
    longitude          DOUBLE PRECISION,
    room_type          VARCHAR(50) DEFAULT 'single',
    status             VARCHAR(20) DEFAULT 'AVAILABLE',
    size_sqft          INTEGER,
    max_occupancy      INTEGER DEFAULT 1,
    has_private_bathroom BOOLEAN DEFAULT FALSE,
    is_furnished       BOOLEAN DEFAULT FALSE,
    is_verified        BOOLEAN DEFAULT FALSE,
    total_views        INTEGER DEFAULT 0,
    total_bookings     INTEGER DEFAULT 0,
    average_rating     DOUBLE PRECISION DEFAULT 0.0,
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_rooms_owner ON rooms(owner_id);
CREATE INDEX idx_rooms_status ON rooms(status);
CREATE INDEX idx_rooms_location ON rooms(location);
CREATE INDEX idx_rooms_price ON rooms(price_per_night);

-- =====================================================
-- 4. AMENITIES
-- =====================================================
CREATE TABLE IF NOT EXISTS amenities (
                                         amenity_id    BIGSERIAL PRIMARY KEY,
                                         name          VARCHAR(100) NOT NULL UNIQUE,
    description   TEXT,
    icon_class    VARCHAR(100),
    category      VARCHAR(50) DEFAULT 'general',
    is_active     BOOLEAN DEFAULT TRUE,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS room_amenities (
                                              room_id     BIGINT NOT NULL REFERENCES rooms(room_id) ON DELETE CASCADE,
    amenity_id  BIGINT NOT NULL REFERENCES amenities(amenity_id) ON DELETE CASCADE,
    PRIMARY KEY (room_id, amenity_id)
    );

-- =====================================================
-- 5. PHOTOS
-- =====================================================
CREATE TABLE IF NOT EXISTS photos (
                                      photo_id      BIGSERIAL PRIMARY KEY,
                                      room_id       BIGINT NOT NULL REFERENCES rooms(room_id) ON DELETE CASCADE,
    photo_url     VARCHAR(500) NOT NULL,
    caption       TEXT,
    is_primary    BOOLEAN DEFAULT FALSE,
    display_order INTEGER DEFAULT 0,
    uploaded_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_photos_room ON photos(room_id);

-- =====================================================
-- 6. OWNER PROFILES
-- =====================================================
CREATE TABLE IF NOT EXISTS owner_profiles (
                                              user_id        BIGINT PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
    total_listings INTEGER DEFAULT 0,
    rating_count   INTEGER DEFAULT 0,
    total_rating   DOUBLE PRECISION DEFAULT 0.0
    );

-- =====================================================
-- 7. ADMIN PROFILES
-- =====================================================
CREATE TABLE IF NOT EXISTS admin_profiles (
                                              admin_id    BIGINT PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
    username    VARCHAR(100) NOT NULL UNIQUE,
    permissions JSONB,
    last_login  TIMESTAMP
    );

-- =====================================================
-- 8. CONVERSATIONS (corrected)
-- =====================================================
CREATE TABLE IF NOT EXISTS conversations (
                                             conversation_id BIGSERIAL PRIMARY KEY,
                                             renter_id       BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    owner_id        BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    room_id         BIGINT REFERENCES rooms(room_id) ON DELETE SET NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_conversations_renter ON conversations(renter_id);
CREATE INDEX idx_conversations_owner ON conversations(owner_id);
CREATE INDEX idx_conversations_room ON conversations(room_id);

-- =====================================================
-- 9. MESSAGES
-- =====================================================
CREATE TABLE IF NOT EXISTS messages (
                                        message_id      BIGSERIAL PRIMARY KEY,
                                        conversation_id BIGINT NOT NULL REFERENCES conversations(conversation_id) ON DELETE CASCADE,
    sender_id       BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    receiver_id     BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    message_text    TEXT NOT NULL,
    is_read         BOOLEAN DEFAULT FALSE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_messages_conversation ON messages(conversation_id);
CREATE INDEX idx_messages_sender ON messages(sender_id);
CREATE INDEX idx_messages_receiver ON messages(receiver_id);
CREATE INDEX idx_messages_created ON messages(created_at);

-- =====================================================
-- 10. NOTIFICATIONS
-- =====================================================
CREATE TABLE IF NOT EXISTS notifications (
                                             notification_id BIGSERIAL PRIMARY KEY,
                                             user_id         BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    type            VARCHAR(50) NOT NULL,
    title           VARCHAR(200) NOT NULL,
    message         TEXT NOT NULL,
    data            JSONB,
    is_read         BOOLEAN DEFAULT FALSE,
    read_at         TIMESTAMP,
    action_url      VARCHAR(500),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_notifications_user ON notifications(user_id);
CREATE INDEX idx_notifications_read ON notifications(user_id, is_read);

-- =====================================================
-- 11. BOOKINGS (with conversation_id FK to conversations)
-- =====================================================
CREATE TABLE IF NOT EXISTS bookings (
                                        booking_id        BIGSERIAL PRIMARY KEY,
                                        room_id           BIGINT NOT NULL REFERENCES rooms(room_id) ON DELETE CASCADE,
    renter_id         BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    start_date        DATE NOT NULL,
    end_date          DATE NOT NULL,
    total_nights      INTEGER NOT NULL,
    price_per_night   DOUBLE PRECISION NOT NULL,
    total_amount      DOUBLE PRECISION NOT NULL,
    status            VARCHAR(20) DEFAULT 'PENDING',
    special_requests  TEXT,
    cancellation_reason TEXT,
    cancelled_at      TIMESTAMP,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    conversation_id   BIGINT UNIQUE REFERENCES conversations(conversation_id) ON DELETE SET NULL
    );

CREATE INDEX idx_bookings_room ON bookings(room_id);
CREATE INDEX idx_bookings_renter ON bookings(renter_id);
CREATE INDEX idx_bookings_dates ON bookings(start_date, end_date);
CREATE INDEX idx_bookings_status ON bookings(status);

-- =====================================================
-- 12. PAYMENTS (foreign key to bookings)
-- =====================================================
CREATE TABLE IF NOT EXISTS payments (
                                        payment_id       BIGSERIAL PRIMARY KEY,
                                        booking_id       BIGINT NOT NULL UNIQUE REFERENCES bookings(booking_id) ON DELETE CASCADE,
    amount           DOUBLE PRECISION NOT NULL,
    currency         VARCHAR(3) DEFAULT 'USD',
    payment_method   VARCHAR(50) NOT NULL,
    status           VARCHAR(20) DEFAULT 'PENDING',
    transaction_id   VARCHAR(100) UNIQUE,
    gateway_response JSONB,
    receipt_url      VARCHAR(500),
    paid_at          TIMESTAMP,
    refunded_at      TIMESTAMP,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_payments_booking ON payments(booking_id);
CREATE INDEX idx_payments_status ON payments(status);
CREATE INDEX idx_payments_transaction ON payments(transaction_id);

-- =====================================================
-- 13. REVIEWS
-- =====================================================
CREATE TABLE IF NOT EXISTS reviews (
                                       review_id       BIGSERIAL PRIMARY KEY,
                                       room_id         BIGINT NOT NULL REFERENCES rooms(room_id) ON DELETE CASCADE,
    renter_id       BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    booking_id      BIGINT NOT NULL UNIQUE REFERENCES bookings(booking_id) ON DELETE CASCADE,
    rating          INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
    title           VARCHAR(255),
    comment         TEXT NOT NULL,
    owner_response  TEXT,
    is_verified     BOOLEAN DEFAULT FALSE,
    is_flagged      BOOLEAN DEFAULT FALSE,
    flag_reason     TEXT,
    helpful_count   INTEGER DEFAULT 0,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_reviews_room ON reviews(room_id);
CREATE INDEX idx_reviews_renter ON reviews(renter_id);
CREATE INDEX idx_reviews_rating ON reviews(rating);

-- =====================================================
-- 14. FAVORITES (many-to-many between users and rooms)
-- =====================================================
CREATE TABLE IF NOT EXISTS favorites (
                                         id          BIGSERIAL PRIMARY KEY,
                                         renter_id   BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    room_id     BIGINT NOT NULL REFERENCES rooms(room_id) ON DELETE CASCADE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (renter_id, room_id)
    );

CREATE INDEX idx_favorites_renter ON favorites(renter_id);
CREATE INDEX idx_favorites_room ON favorites(room_id);

-- =====================================================
-- 15. AUDIT LOGS
-- =====================================================
CREATE TABLE IF NOT EXISTS audit_logs (
                                          log_id      BIGSERIAL PRIMARY KEY,
                                          user_id     BIGINT REFERENCES users(user_id) ON DELETE SET NULL,
    action      VARCHAR(100) NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    entity_id   BIGINT,
    old_values  JSONB,
    new_values  JSONB,
    ip_address  VARCHAR(45),
    user_agent  TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_audit_user ON audit_logs(user_id);
CREATE INDEX idx_audit_entity ON audit_logs(entity_type, entity_id);
CREATE INDEX idx_audit_created ON audit_logs(created_at);