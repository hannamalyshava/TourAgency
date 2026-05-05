package org.example.service;

import org.example.bd.DatabaseConnection;
import org.example.model.User;
import org.example.repository.UserRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Сервис для работы с пользователями.
 * Каждый метод выполняется в рамках одной транзакции.
 */

public class UserService {

    // Добавление пользователя (регистрация)
    public void registerUser(User user) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // начало транзакции

            UserRepository repository = new UserRepository(conn);
            repository.add(user);

            conn.commit(); // подтверждение
        } catch (SQLException e) {
            throw new SQLException("Ошибка при регистрации пользователя", e);
        }
    }

    //  Получить всех пользователей
    public List<User> getAllUsers() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            UserRepository repository = new UserRepository(conn);
            List<User> users = repository.findAll();

            conn.commit();
            return users;
        } catch (SQLException e) {
            throw new SQLException("Ошибка при получении списка пользователей", e);
        }
    }

    //Найти пользователя по ID
    public User getUserById(Long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            UserRepository repository = new UserRepository(conn);
            User user = repository.findById(id);

            conn.commit();
            return user;
        } catch (SQLException e) {
            throw new SQLException("Ошибка при поиске пользователя по ID", e);
        }
    }

    // Найти пользователя по email
    public User getUserByEmail(String email) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            UserRepository repository = new UserRepository(conn);
            User user = repository.findByEmail(email);

            conn.commit();
            return user;
        } catch (SQLException e) {
            throw new SQLException("Ошибка при поиске пользователя по email", e);
        }
    }

    // Авторизация по имени, фамилии, телефону и паролю
    public User loginByPersonalData(String firstName, String lastName, String phone, String password) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            UserRepository repository = new UserRepository(conn);
            User user = repository.findByPersonalData(firstName, lastName, phone, password);

            conn.commit();

            return user; // если null — неверные данные
        } catch (SQLException e) {
            throw new SQLException("Ошибка при аутентификации пользователя по персональным данным", e);
        }
    }


    // Удалить пользователя
    public void deleteUser(Long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            UserRepository repository = new UserRepository(conn);
            repository.delete(id);

            conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Ошибка при удалении пользователя", e);
        }
    }
}
