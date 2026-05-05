-- 2_create.sql

-- таблица пользователей
CREATE TABLE "users" (
  "user_id"    BIGSERIAL PRIMARY KEY,
  "l_name"     VARCHAR(100),
  "f_name"     VARCHAR(100),
  "phone"      VARCHAR(30),
  "email"      VARCHAR(255) UNIQUE,
  "role"       VARCHAR(20) NOT NULL DEFAULT 'CLIENT',
  "passw"      VARCHAR(255) NOT NULL, -- храните хэш пароля
  "created_at" TIMESTAMP NOT NULL DEFAULT now(),
  CONSTRAINT users_role_check CHECK (role IN ('CLIENT','MANAGER'))
);
CREATE INDEX idx_users_email ON "users" ("email");
CREATE INDEX idx_users_name ON "users" ("l_name", "f_name");

-- таблица программы тура
CREATE TABLE "programs" (
  "program_id"   BIGSERIAL PRIMARY KEY,
  "name"         VARCHAR(255) NOT NULL,
  "description"  TEXT,
  "duration"     INTEGER, -- длительность в днях
  "created_at"   TIMESTAMP NOT NULL DEFAULT now()
);

-- таблица отелей
CREATE TABLE "hotels" (
  "hotel_id"     BIGSERIAL PRIMARY KEY,
  "name"         VARCHAR(255) NOT NULL,
  "stars"        SMALLINT CHECK (stars >= 1 AND stars <= 5),
  "description"  TEXT,
  "price"        NUMERIC(10,2), -- всего цифр, после запятой
  "created_at"   TIMESTAMP NOT NULL DEFAULT now()
);

-- таблица туров
CREATE TABLE "tours" (
  "tour_id"         BIGSERIAL PRIMARY KEY,
  "title"           VARCHAR(255) NOT NULL,
  "description"     TEXT,
  "country"         VARCHAR(100),
  "city"            VARCHAR(100),
  "price"           NUMERIC(10,2) NOT NULL,
  "start_date"      DATE,
  "end_date"        DATE,
  "available_seats" INTEGER NOT NULL DEFAULT 0 CHECK (available_seats >= 0),
  "hotel_id"        BIGINT REFERENCES "hotels"("hotel_id") ON UPDATE RESTRICT ON DELETE RESTRICT, --нельзя обновить и удалить ПК
  "program_id"      BIGINT REFERENCES "programs"("program_id") ON UPDATE RESTRICT ON DELETE SET NULL, --если удалить то в Пк ставим 0
  "created_at"      TIMESTAMP NOT NULL DEFAULT now()
);
CREATE INDEX idx_tours_country ON "tours" ("country");
CREATE INDEX idx_tours_start_date ON "tours" ("start_date");
CREATE INDEX idx_tours_hotel ON "tours" ("hotel_id");

-- таблица бронирований / заявок
CREATE TABLE "bookings" (
  "booking_id"   BIGSERIAL PRIMARY KEY,
  "user_id"      BIGINT NOT NULL REFERENCES "users"("user_id") ON UPDATE RESTRICT ON DELETE RESTRICT,
  "tour_id"      BIGINT NOT NULL REFERENCES "tours"("tour_id") ON UPDATE RESTRICT ON DELETE RESTRICT,
  "total_price"  NUMERIC(10,2) NOT NULL,
  "people"       INTEGER NOT NULL CHECK (people > 0),
  "created_at"   TIMESTAMP NOT NULL DEFAULT now(),
  "status"       VARCHAR(20) NOT NULL DEFAULT 'PENDING',
  CONSTRAINT bookings_status_check CHECK (status IN ('PENDING','CONFIRMED','CANCELLED'))
);
CREATE INDEX idx_bookings_user ON "bookings" ("user_id");
CREATE INDEX idx_bookings_tour ON "bookings" ("tour_id");
CREATE INDEX idx_bookings_status ON "bookings" ("status");
