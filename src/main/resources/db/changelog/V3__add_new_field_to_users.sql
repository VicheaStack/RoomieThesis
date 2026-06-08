-- =====================================================
-- Insert sample data (Mapped to explicit schema columns)
-- =====================================================

-- -----------------------------------------------------
-- 1. USERS (10 users: owners, renters, admins)
-- -----------------------------------------------------
INSERT INTO users (user_id, first_name, last_name, password, email, provider, phone_number, profile_photo_url, role, is_verified, is_active, last_login, created_at, updated_at) VALUES
                                                                                                                                                                                     (1, 'John', 'Doe', '$2a$10$xyz...', 'john.doe@example.com', NULL, '+1234567890', 'https://randomuser.me', 'OWNER', true, true, '2025-01-15 10:00:00', NOW(), NOW()),
                                                                                                                                                                                     (2, 'Jane', 'Smith', '$2a$10$xyz...', 'jane.smith@example.com', 'google', '+1234567891', 'https://randomuser.me', 'RENTER', true, true, '2025-01-16 11:00:00', NOW(), NOW()),
                                                                                                                                                                                     (3, 'Mike', 'Johnson', '$2a$10$xyz...', 'mike.j@example.com', NULL, '+1234567892', 'https://randomuser.me', 'OWNER', true, true, '2025-01-17 09:00:00', NOW(), NOW()),
                                                                                                                                                                                     (4, 'Emily', 'Brown', '$2a$10$xyz...', 'emily.b@example.com', NULL, '+1234567893', 'https://randomuser.me', 'RENTER', true, true, '2025-01-18 14:00:00', NOW(), NOW()),
                                                                                                                                                                                     (5, 'Chris', 'Wilson', '$2a$10$xyz...', 'chris.w@example.com', 'github', '+1234567894', 'https://randomuser.me', 'OWNER', true, true, '2025-01-19 08:00:00', NOW(), NOW()),
                                                                                                                                                                                     (6, 'Sarah', 'Martinez', '$2a$10$xyz...', 'sarah.m@example.com', NULL, '+1234567895', 'https://randomuser.me', 'RENTER', true, true, '2025-01-20 12:00:00', NOW(), NOW()),
                                                                                                                                                                                     (7, 'David', 'Garcia', '$2a$10$xyz...', 'david.g@example.com', NULL, '+1234567896', 'https://randomuser.me', 'OWNER', true, true, '2025-01-21 10:00:00', NOW(), NOW()),
                                                                                                                                                                                     (8, 'Lisa', 'Rodriguez', '$2a$10$xyz...', 'lisa.r@example.com', 'google', '+1234567897', 'https://randomuser.me', 'ADMIN', true, true, '2025-01-22 16:00:00', NOW(), NOW()),
                                                                                                                                                                                     (9, 'Tom', 'Lee', '$2a$10$xyz...', 'tom.lee@example.com', NULL, '+1234567898', 'https://randomuser.me', 'RENTER', true, true, '2025-01-23 09:30:00', NOW(), NOW()),
                                                                                                                                                                                     (10, 'Anna', 'White', '$2a$10$xyz...', 'anna.w@example.com', NULL, '+1234567899', 'https://randomuser.me', 'OWNER', true, true, '2025-01-24 13:00:00', NOW(), NOW())
    ON CONFLICT (email) DO NOTHING;

SELECT setval(pg_get_serial_sequence('users', 'user_id'), COALESCE(MAX(user_id), 1)) FROM users;

-- -----------------------------------------------------
-- 2. SYSTEM SETTINGS
-- -----------------------------------------------------
INSERT INTO system_settings (setting_id, setting_key, setting_value, data_type, category, description, is_public) VALUES
                                                                                                                      (1, 'site.name', 'RoomieHub', 'string', 'general', 'Site name', true),
                                                                                                                      (2, 'contact.email', 'admin@roomiehub.com', 'string', 'contact', 'Admin email', false),
                                                                                                                      (3, 'max.booking.days', '30', 'integer', 'booking', 'Maximum days per booking', true)
    ON CONFLICT (setting_key) DO NOTHING;

SELECT setval(pg_get_serial_sequence('system_settings', 'setting_id'), COALESCE(MAX(setting_id), 1)) FROM system_settings;

-- -----------------------------------------------------
-- 3. AMENITIES
-- -----------------------------------------------------
INSERT INTO amenities (amenity_id, name, description, icon_class, category, is_active) VALUES
                                                                                           (1, 'WiFi', 'High-speed internet', 'wifi', 'internet', true),
                                                                                           (2, 'Parking', 'Free parking on premises', 'parking', 'parking', true),
                                                                                           (3, 'Kitchen', 'Full kitchen', 'kitchen', 'food', true),
                                                                                           (4, 'Air Conditioning', 'Central AC', 'ac', 'comfort', true),
                                                                                           (5, 'Washer/Dryer', 'In-unit laundry', 'laundry', 'utility', true),
                                                                                           (6, 'TV', 'Smart TV with Netflix', 'tv', 'entertainment', true),
                                                                                           (7, 'Pool', 'Outdoor pool', 'pool', 'recreation', true),
                                                                                           (8, 'Gym', 'Fitness center', 'gym', 'recreation', true)
    ON CONFLICT (name) DO NOTHING;

SELECT setval(pg_get_serial_sequence('amenities', 'amenity_id'), COALESCE(MAX(amenity_id), 1)) FROM amenities;

-- -----------------------------------------------------
-- 4. ROOMS
-- -----------------------------------------------------
INSERT INTO rooms (room_id, owner_id, title, description, price_per_night, location, latitude, longitude, room_type, status, size_sqft, max_occupancy, has_private_bathroom, is_furnished, is_verified, total_views, total_bookings, average_rating, created_at, updated_at) VALUES
                                                                                                                                                                                                                                                                                 (1, 1, 'Cozy Downtown Studio', 'Modern studio in city center', 85.00, 'Downtown, City', 40.7128, -74.0060, 'studio', 'AVAILABLE', 450, 2, true, true, true, 120, 15, 4.8, NOW(), NOW()),
                                                                                                                                                                                                                                                                                 (2, 3, 'Spacious 2BR Apartment', 'Near metro station', 150.00, 'Midtown', 40.7580, -73.9855, 'apartment', 'AVAILABLE', 850, 4, true, true, true, 200, 32, 4.9, NOW(), NOW()),
                                                                                                                                                                                                                                                                                 (3, 5, 'Luxury Loft', 'Rooftop terrace', 220.00, 'SoHo', 40.7235, -74.0000, 'loft', 'BOOKED', 1100, 2, true, true, true, 350, 45, 5.0, NOW(), NOW()),
                                                                                                                                                                                                                                                                                 (4, 7, 'Shared Room in Flat', 'Friendly housemates', 45.00, 'Brooklyn', 40.6782, -73.9442, 'shared', 'AVAILABLE', 200, 1, false, true, false, 60, 8, 4.2, NOW(), NOW()),
                                                                                                                                                                                                                                                                                 (5, 10, 'Studio near University', 'Perfect for students', 65.00, 'University District', 40.8075, -73.9626, 'studio', 'AVAILABLE', 380, 2, true, false, true, 90, 12, 4.5, NOW(), NOW()),
                                                                                                                                                                                                                                                                                 (6, 1, 'Penthouse Suite', 'City views', 300.00, 'Financial District', 40.7069, -74.0093, 'apartment', 'AVAILABLE', 1500, 4, true, true, true, 500, 60, 4.9, NOW(), NOW())
    ON CONFLICT (room_id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('rooms', 'room_id'), COALESCE(MAX(room_id), 1)) FROM rooms;

-- -----------------------------------------------------
-- 5. ROOM AMENITIES
-- -----------------------------------------------------
INSERT INTO room_amenities (room_id, amenity_id) VALUES
                                                     (1,1), (1,2), (1,4),
                                                     (2,1), (2,2), (2,3), (2,4), (2,5),
                                                     (3,1), (3,2), (3,4), (3,6), (3,7), (3,8),
                                                     (4,1), (4,4),
                                                     (5,1), (5,2), (5,4), (5,5),
                                                     (6,1), (6,2), (6,4), (6,6), (6,7)
    ON CONFLICT (room_id, amenity_id) DO NOTHING;

-- -----------------------------------------------------
-- 6. PHOTOS
-- -----------------------------------------------------
INSERT INTO photos (photo_id, room_id, photo_url, caption, is_primary, display_order) VALUES
                                                                                          (1, 1, 'https://picsum.photos', 'Living room', true, 1),
                                                                                          (2, 1, 'https://picsum.photos', 'Bedroom', false, 2),
                                                                                          (3, 2, 'https://picsum.photos', 'Kitchen', true, 1),
                                                                                          (4, 2, 'https://picsum.photos', 'Living area', false, 2),
                                                                                          (5, 3, 'https://picsum.photos', 'Rooftop terrace', true, 1),
                                                                                          (6, 3, 'https://picsum.photos', 'Bedroom', false, 2),
                                                                                          (7, 4, 'https://picsum.photos', 'Shared room', true, 1),
                                                                                          (8, 5, 'https://picsum.photos', 'Study desk', true, 1),
                                                                                          (9, 5, 'https://picsum.photos', 'Window view', false, 2),
                                                                                          (10, 6, 'https://picsum.photos', 'Living room', true, 1),
                                                                                          (11, 6, 'https://picsum.photos', 'Bedroom', false, 2)
    ON CONFLICT (photo_id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('photos', 'photo_id'), COALESCE(MAX(photo_id), 1)) FROM photos;

-- -----------------------------------------------------
-- 7. OWNER PROFILES
-- -----------------------------------------------------
INSERT INTO owner_profiles (user_id, total_listings, rating_count, total_rating) VALUES
                                                                                     (1, 2, 12, 58.5),
                                                                                     (3, 1, 8, 39.2),
                                                                                     (5, 1, 15, 75.0),
                                                                                     (7, 1, 4, 16.8),
                                                                                     (10, 1, 6, 27.0)
    ON CONFLICT (user_id) DO NOTHING;

-- -----------------------------------------------------
-- 8. ADMIN PROFILES
-- -----------------------------------------------------
INSERT INTO admin_profiles (admin_id, username, permissions, last_login) VALUES
    (8, 'lisar_admin', '["MANAGE_USERS", "MANAGE_ROOMS", "VIEW_REPORTS"]'::jsonb, NOW())
    ON CONFLICT (admin_id) DO NOTHING;

-- -----------------------------------------------------
-- 9. CONVERSATIONS
-- -----------------------------------------------------
INSERT INTO conversations (conversation_id, renter_id, owner_id, room_id, created_at) VALUES
                                                                                          (1, 2, 1, 1, NOW()),
                                                                                          (2, 4, 3, 2, NOW()),
                                                                                          (3, 6, 5, 3, NOW()),
                                                                                          (4, 9, 7, 4, NOW()),
                                                                                          (5, 2, 10, 5, NOW())
    ON CONFLICT (conversation_id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('conversations', 'conversation_id'), COALESCE(MAX(conversation_id), 1)) FROM conversations;

-- -----------------------------------------------------
-- 10. MESSAGES
-- -----------------------------------------------------
INSERT INTO messages (message_id, conversation_id, sender_id, receiver_id, message_text, is_read, created_at) VALUES
                                                                                                                  (1, 1, 2, 1, 'Hi, is the studio available next week?', true, NOW()),
                                                                                                                  (2, 1, 1, 2, 'Yes, still available!', true, NOW()),
                                                                                                                  (3, 1, 2, 1, 'Great, I will book it.', false, NOW()),
                                                                                                                  (4, 2, 4, 3, 'Can I bring a pet?', true, NOW()),
                                                                                                                  (5, 2, 3, 4, 'Sorry, no pets allowed.', true, NOW()),
                                                                                                                  (6, 2, 4, 3, 'Ok thanks.', false, NOW()),
                                                                                                                  (7, 3, 6, 5, 'Is the pool heated?', true, NOW()),
                                                                                                                  (8, 3, 5, 6, 'Yes, all year round.', true, NOW()),
                                                                                                                  (9, 3, 6, 5, 'Perfect, book it!', false, NOW())
    ON CONFLICT (message_id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('messages', 'message_id'), COALESCE(MAX(message_id), 1)) FROM messages;
