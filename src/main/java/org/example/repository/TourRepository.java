package org.example.repository;

import org.example.model.Tour;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TourRepository extends BaseRepository {

    public TourRepository(Connection connection) {
        setConnection(connection);
    }

    //Добавить новый тур
    public void add(Tour tour) throws SQLException {
        String sql = """
            INSERT INTO "tours" 
            ("title", "description", "country", "city", "price",
             "start_date", "end_date", "available_seats", "hotel_id", "program_id")
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, tour.getTitle());
            ps.setString(2, tour.getDescription());
            ps.setString(3, tour.getCountry());
            ps.setString(4, tour.getCity());
            ps.setBigDecimal(5, tour.getPrice());
            ps.setDate(6, Date.valueOf(tour.getStartDate()));
            ps.setDate(7, Date.valueOf(tour.getEndDate()));
            ps.setInt(8, tour.getAvailableSeats());

            if (tour.getHotelId() != null) {
                ps.setLong(9, tour.getHotelId());
            } else {
                ps.setNull(9, Types.BIGINT);
            }

            if (tour.getProgramId() != null) {
                ps.setLong(10, tour.getProgramId());
            } else {
                ps.setNull(10, Types.BIGINT);
            }

            ps.executeUpdate();
        }
    }


    public List<Tour> search(String country, String type, String date,
                             Double priceMin, Double priceMax, Integer seats) throws SQLException {

        List<Tour> tours = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
        SELECT * FROM "tours"
        WHERE 1=1
    """);

        List<Object> params = new ArrayList<>();

        // Поиск по стране
        if (country != null && !country.isBlank()) {
            sql.append(" AND LOWER(\"country\") LIKE LOWER(?)");
            params.add("%" + country.trim() + "%");
        }

        // Поиск по типу (description)
        if (type != null && !type.isBlank()) {
            sql.append(" AND LOWER(\"description\") LIKE LOWER(?)");
            params.add("%" + type.trim() + "%");
        }

        // Поиск по дате
        if (date != null && !date.isBlank()) {
            try {
                LocalDate parsed = LocalDate.parse(date);
                sql.append(" AND \"start_date\" = ?");
                params.add(Date.valueOf(parsed));
            } catch (Exception e) {
                System.out.println("⚠ Неверный формат даты: " + date);
            }
        }

        // Цена от
        if (priceMin != null) {
            sql.append(" AND \"price\" >= ?");
            params.add(BigDecimal.valueOf(priceMin));
        }

        // Цена до
        if (priceMax != null) {
            sql.append(" AND \"price\" <= ?");
            params.add(BigDecimal.valueOf(priceMax));
        }

        // Свободные места
        if (seats != null) {
            sql.append(" AND \"available_seats\" >= ?");
            params.add(seats);
        }

        sql.append(" ORDER BY \"start_date\" ASC");

        try (PreparedStatement ps = getConnection().prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                if (p instanceof String s) ps.setString(i + 1, s);
                else if (p instanceof Integer n) ps.setInt(i + 1, n);
                else if (p instanceof BigDecimal bd) ps.setBigDecimal(i + 1, bd);
                else if (p instanceof Date d) ps.setDate(i + 1, d);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tours.add(mapRow(rs));
                }
            }
        }

        return tours;
    }

    public List<Tour> findAll() throws SQLException {
        List<Tour> list = new ArrayList<>();

        String sql = "SELECT * FROM \"tours\"";
        
        System.out.println("[TourRepository] Executing query: " + sql);

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
            
            System.out.println("[TourRepository] Found " + list.size() + " tours");
        }

        return list;
    }


    //Найти тур по стране
    public List<Tour> findByCountry(String country) throws SQLException {
        List<Tour> tours = new ArrayList<>();
        String sql = "SELECT * FROM \"tours\" WHERE LOWER(\"country\") LIKE LOWER(?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + country + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tours.add(mapRow(rs));
                }
            }
        }
        return tours;
    }

    // Найти туры по диапазону цен
    public List<Tour> findByPriceRange(double min, double max) throws SQLException {
        List<Tour> tours = new ArrayList<>();
        String sql = "SELECT * FROM \"tours\" WHERE \"price\" BETWEEN ? AND ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setDouble(1, min);
            ps.setDouble(2, max);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tours.add(mapRow(rs));
                }
            }
        }
        return tours;
    }

    // Найти тур по ID
    public Tour findById(long id) throws SQLException {
        String sql = "SELECT * FROM \"tours\" WHERE \"tour_id\" = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null; // если не найден
    }

    // Удалить тур по ID
    public void deleteById(long id) throws SQLException {
        String sql = "DELETE FROM \"tours\" WHERE \"tour_id\" = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    // Обновить существующий тур
    public void update(Tour tour) throws SQLException {
        String sql = """
            UPDATE "tours" SET 
                "title" = ?, 
                "description" = ?, 
                "country" = ?, 
                "city" = ?, 
                "price" = ?, 
                "start_date" = ?, 
                "end_date" = ?, 
                "available_seats" = ?, 
                "hotel_id" = ?, 
                "program_id" = ?
            WHERE "tour_id" = ?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, tour.getTitle());
            ps.setString(2, tour.getDescription());
            ps.setString(3, tour.getCountry());
            ps.setString(4, tour.getCity());
            ps.setBigDecimal(5, tour.getPrice());
            ps.setDate(6, tour.getStartDate() != null ? Date.valueOf(tour.getStartDate()) : null);
            ps.setDate(7, tour.getEndDate() != null ? Date.valueOf(tour.getEndDate()) : null);
            ps.setInt(8, tour.getAvailableSeats());

            if (tour.getHotelId() != null) {
                ps.setLong(9, tour.getHotelId());
            } else {
                ps.setNull(9, Types.BIGINT);
            }

            if (tour.getProgramId() != null) {
                ps.setLong(10, tour.getProgramId());
            } else {
                ps.setNull(10, Types.BIGINT);
            }

            ps.setLong(11, tour.getTourId()); // ID тура в WHERE

            ps.executeUpdate();
        }
    }

    // Уменьшить количество свободных мест
    public void decreaseAvailableSeats(long tourId, int seatsToDecrease) throws SQLException {
        String sql = """
            UPDATE "tours" 
            SET "available_seats" = "available_seats" - ?
            WHERE "tour_id" = ? AND "available_seats" >= ?
        """;
        
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, seatsToDecrease);
            ps.setLong(2, tourId);
            ps.setInt(3, seatsToDecrease);
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new SQLException("Не удалось уменьшить количество мест. Возможно, недостаточно свободных мест.");
            }
            
            System.out.println("[TourRepository] Decreased " + seatsToDecrease + " seats for tour " + tourId);
        }
    }

    // Увеличить количество свободных мест (при отмене бронирования)
    public void increaseAvailableSeats(long tourId, int seatsToIncrease) throws SQLException {
        String sql = """
            UPDATE "tours" 
            SET "available_seats" = "available_seats" + ?
            WHERE "tour_id" = ?
        """;
        
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, seatsToIncrease);
            ps.setLong(2, tourId);
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new SQLException("Не удалось увеличить количество мест. Тур не найден.");
            }
            
            System.out.println("[TourRepository] Increased " + seatsToIncrease + " seats for tour " + tourId);
        }
    }


    // ResultSet → Tour
    private Tour mapRow(ResultSet rs) throws SQLException {
        Tour tour = new Tour();
        tour.setTourId(rs.getLong("tour_id"));
        tour.setTitle(rs.getString("title"));
        tour.setDescription(rs.getString("description"));
        tour.setCountry(rs.getString("country"));
        tour.setCity(rs.getString("city"));
        tour.setPrice(rs.getBigDecimal("price"));

        Date start = rs.getDate("start_date");
        if (start != null) tour.setStartDate(start.toLocalDate());

        Date end = rs.getDate("end_date");
        if (end != null) tour.setEndDate(end.toLocalDate());

        tour.setAvailableSeats(rs.getInt("available_seats"));
        tour.setHotelId((Long) rs.getObject("hotel_id"));
        tour.setProgramId((Long) rs.getObject("program_id"));

        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) tour.setCreatedAt(ts.toLocalDateTime());

        return tour;
    }

}
