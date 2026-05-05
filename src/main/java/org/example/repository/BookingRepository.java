package org.example.repository;

import org.example.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository extends BaseRepository {

    public BookingRepository(Connection connection) {
        setConnection(connection);
    }
    private String tourName;

    public String getTourName() { return tourName; }
    public void setTourName(String tourName) { this.tourName = tourName; }

    // Добавить новое бронирование
    public void add(Booking booking) throws SQLException {
        String sql = """
                INSERT INTO "bookings" ("user_id", "tour_id", "total_price", "people", "status")
                VALUES (?, ?, ?, ?, ?)
                """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, booking.getUserId());
            ps.setLong(2, booking.getTourId());
            ps.setBigDecimal(3, booking.getTotalPrice());
            ps.setInt(4, booking.getPeople());
            ps.setString(5, booking.getStatus());
            ps.executeUpdate();
        }
    }

    // Найти все бронирования
    public List<Booking> findAll() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = """
        SELECT b.*, t.title AS tour_title
        FROM "bookings" b
        LEFT JOIN "tours" t ON b.tour_id = t.tour_id
        ORDER BY b.created_at DESC
        """;

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                bookings.add(mapRowToBooking(rs));
            }
        }
        return bookings;
    }

    // Найти бронирования пользователя
    public List<Booking> findByUserId(long userId) throws SQLException {
        System.out.println("[BookingRepository] findByUserId called with userId=" + userId);
        List<Booking> bookings = new ArrayList<>();
        String sql = """
            SELECT b.*, t.title as tour_title
            FROM "bookings" b
            LEFT JOIN "tours" t ON b.tour_id = t.tour_id
            WHERE b.user_id = ?
            ORDER BY b.created_at DESC
            """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking booking = mapRowToBooking(rs);
                    booking.setTourTitle(rs.getString("tour_title"));
                    bookings.add(booking);
                }
            }
        }

        System.out.println("[BookingRepository] Found " + bookings.size() + " bookings for userId=" + userId);
        return bookings;
    }

    // Найти бронирования по статусу
    public List<Booking> findByStatus(String status) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM \"bookings\" WHERE \"status\" = ? ORDER BY \"created_at\" DESC";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bookings.add(mapRowToBooking(rs));
                }
            }
        }
        return bookings;
    }

    // Обновить статус бронирования подтвердить/отменить
    public void updateStatus(long bookingId, String newStatus) throws SQLException {
        String sql = "UPDATE \"bookings\" SET \"status\" = ? WHERE \"booking_id\" = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setLong(2, bookingId);
            ps.executeUpdate();
        }
    }

    // Удалить бронирование
    public void delete(long bookingId) throws SQLException {
        String sql = "DELETE FROM \"bookings\" WHERE \"booking_id\" = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, bookingId);
            ps.executeUpdate();
        }
    }

    // ResultSet → Booking одну строку из результата в объект класса
    private Booking mapRowToBooking(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setBookingId(rs.getLong("booking_id"));
        b.setUserId(rs.getLong("user_id"));
        b.setTourId(rs.getLong("tour_id"));
        b.setTotalPrice(rs.getBigDecimal("total_price"));
        b.setPeople(rs.getInt("people"));
        Timestamp created = rs.getTimestamp("created_at");
        if (created != null) {
            b.setCreatedAt(created.toLocalDateTime());
        }
        b.setStatus(rs.getString("status"));
        return b;
    }
}
