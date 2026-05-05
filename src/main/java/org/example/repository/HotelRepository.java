package org.example.repository;

import org.example.model.Hotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelRepository extends BaseRepository {

    public HotelRepository(Connection connection) {
        setConnection(connection);
    }
    //Добавить новый отель
    public void add(Hotel hotel) throws SQLException {
        String sql = """
                INSERT INTO "hotels" ("name", "stars", "description", "price")
                VALUES (?, ?, ?, ?)
                """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, hotel.getName());
            ps.setInt(2, hotel.getStars());
            ps.setString(3, hotel.getDescription());
            ps.setBigDecimal(4, hotel.getPrice());
            ps.executeUpdate();
        }
    }

    //Найти все отели
    public List<Hotel> findAll() throws SQLException {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM \"hotels\" ORDER BY \"hotel_id\"";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                hotels.add(mapRow(rs));
            }
        }
        return hotels;
    }

    // Найти отель по ID
    public Hotel findById(Long id) throws SQLException {
        String sql = "SELECT * FROM \"hotels\" WHERE \"hotel_id\" = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    //Поиск отелей по количеству звёзд
    public List<Hotel> findByStars(int stars) throws SQLException {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM \"hotels\" WHERE \"stars\" = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, stars);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    hotels.add(mapRow(rs));
                }
            }
        }
        return hotels;
    }

    // Обновить отель
    public void update(Hotel hotel) throws SQLException {
        String sql = """
                UPDATE "hotels"
                SET "name" = ?, "stars" = ?, "description" = ?, "price" = ?
                WHERE "hotel_id" = ?
                """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, hotel.getName());
            ps.setInt(2, hotel.getStars());
            ps.setString(3, hotel.getDescription());
            ps.setBigDecimal(4, hotel.getPrice());
            ps.setLong(5, hotel.getHotelId());
            ps.executeUpdate();
        }
    }

    //  Удалить отель
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM \"hotels\" WHERE \"hotel_id\" = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    //  ResultSet → Hotel
    private Hotel mapRow(ResultSet rs) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setHotelId(rs.getLong("hotel_id"));
        hotel.setName(rs.getString("name"));
        hotel.setStars(rs.getInt("stars"));
        hotel.setDescription(rs.getString("description"));
        hotel.setPrice(rs.getBigDecimal("price"));
        Timestamp created = rs.getTimestamp("created_at");
        if (created != null) {
            hotel.setCreatedAt(created.toLocalDateTime());
        }
        return hotel;
    }
}
