
-- 3_fill.sql
-- Тестовые данные для пользователей, отелей, программ, туров, связей и бронирований
-- USERS
INSERT INTO "users" ("user_id", "l_name", "f_name", "phone", "email", "role", "passw")
VALUES
  (1, 'Иванов',   'Иван',    '+79010000001', 'ivanov@example.com', 'CLIENT',  'hash1'),
  (2, 'Петров',   'Пётр',    '+79010000002', 'petrov@example.com', 'CLIENT',  'hash2'),
  (3, 'Сидоров',  'Сидор',   '+79010000003', 'sidorov@example.com', 'MANAGER', 'hash3'),
  (4, 'Козлова',  'Мария',   '+79010000005', 'maria@example.com',   'CLIENT',  'hash4');
SELECT setval('users_user_id_seq', 5, true);

-- PROGRAMS
INSERT INTO "programs" ("program_id", "name", "description", "duration")
VALUES
  (1, 'Экскурсионный тур', 'Обзорные экскурсии по городу и окрестностям', 5),
  (2, 'Пляжный отдых', 'Отдых на море с размещением в отеле', 7),
  (3, 'Горнолыжный тур', 'Активный отдых на лыжах/бордах', 6);
SELECT setval('programs_program_id_seq', 4, true);

-- HOTELS
INSERT INTO "hotels" ("hotel_id", "name", "stars", "description", "price")
VALUES
  (1, 'Sunrise Hotel', 4, 'Уютный отель у моря', 85.00),
  (2, 'Mountain Inn', 3, 'Комфортабельный отель у склонов', 100.00),
  (3, 'City Center Lodge', 4, 'Отель в центре города', 95.00),
  (4, 'Budget Stay', 2, 'Доступное размещение', 40.00);
SELECT setval('hotels_hotel_id_seq', 5, true);

-- TOURS
INSERT INTO "tours" ("tour_id", "title", "description", "country", "city", "price", "start_date", "end_date", "available_seats", "hotel_id", "program_id")
VALUES
  (1, 'Путешествие на море', 'Пляжный отдых с трансфером и завтраками', 'Испания', 'Барселона', 2570.00, '2025-08-01', '2025-08-08', 20, 1, 2),
  (2, 'Горнолыжный уикенд', '3 дня катания и проживание', 'Австрия', 'Шнееберг', 2000.00, '2025-12-15', '2025-12-21', 15, 2, 3),
  (3, 'Экскурсии по городу', 'Пешеходные экскурсии и музеи', 'Россия', 'Санкт-Петербург', 500.00, '2025-09-10', '2025-09-14', 30, 3, 1),
  (4, 'Комбинированный тур', 'Город + пляж', 'Греция', 'Афины', 1000.00, '2025-07-05', '2025-07-12', 10, 1, 2),
  (5, 'Релакс в горах', 'Тишина, SPA и прогулки', 'Швейцария', 'Гриндельвальд', 2500.00, '2025-05-01', '2025-05-07', 3, 2, 1),
  (6, 'Солнечный пляж', 'Пляжный отдых в прекрасном египетском городе', 'Египет', 'Кемер', 1700.00, '2025-08-01', '2025-08-08',15, 1, 2);

SELECT setval('tours_tour_id_seq', 6, true);

-- BOOKINGS
INSERT INTO "bookings" ("booking_id", "user_id", "tour_id", "total_price", "people", "created_at", "status")
VALUES
  (1, 1, 1, 1500.00, 2, '2025-05-01 10:00:00', 'PENDING'),
  (2, 2, 3, 120.00, 1, '2025-05-02 11:30:00', 'CONFIRMED'),
  (3, 1, 2, 840.00, 2, '2025-05-03 09:15:00', 'CONFIRMED'),
  (4, 4, 4, 1080.00, 2, '2025-05-04 14:20:00', 'CANCELLED'),
  (5, 1, 5, 300.00, 1, '2025-05-05 16:45:00', 'PENDING');
SELECT setval('bookings_booking_id_seq', 6, true);
