package org.example.repository;

import org.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends BaseRepository {

    // Конструктор, принимающий соединение
    public UserRepository(Connection connection) {
        setConnection(connection);
    }

    // Добавить пользователя
    public void add(User user) throws SQLException {
        String sql = """
            INSERT INTO "users" ("l_name", "f_name", "phone", "email", "role", "passw")
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, user.getLastName());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getRole());
            ps.setString(6, user.getPassword());
            ps.executeUpdate();
        }
    }

    //Найти всех пользователей
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM \"users\" ORDER BY \"user_id\"";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        }
        return users;
    }

    // Найти пользователя по ID
    public User findById(Long id) throws SQLException {
        String sql = "SELECT * FROM \"users\" WHERE \"user_id\" = ?";
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

    //  Найти пользователя по email
    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM \"users\" WHERE LOWER(\"email\") = LOWER(?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    //  Удалить пользователя по ID
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM \"users\" WHERE \"user_id\" = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    // ResultSet → User
    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getLong("user_id"));
        user.setLastName(rs.getString("l_name"));
        user.setFirstName(rs.getString("f_name"));
        user.setPhone(rs.getString("phone"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        user.setPassword(rs.getString("passw"));

        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) user.setCreatedAt(ts.toLocalDateTime());

        return user;
    }

    public User findByPersonalData(String firstName, String lastName, String phone, String password) throws SQLException {
        String sql = """
        SELECT * FROM "users"
        WHERE LOWER("f_name") = LOWER(?)
          AND LOWER("l_name") = LOWER(?)
          AND "phone" = ?
          AND "passw" = ?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, phone);
            ps.setString(4, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

}
