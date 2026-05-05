package org.example.service;

import org.example.bd.DatabaseConnection;
import org.example.model.Hotel;
import org.example.repository.HotelRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Сервис для работы с отелями.
 * Каждый метод выполняет одну бизнес-операцию в рамках одной транзакции.
 */
public class HotelService {

    // Добавить новый отель
    public void addHotel(Hotel hotel) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // начало транзакции

            HotelRepository hotelRepository = new HotelRepository(conn);
            hotelRepository.add(hotel);

            conn.commit(); // зафиксировать изменения
        } catch (SQLException e) {
            throw new SQLException("Ошибка при добавлении отеля", e);
        }
    }

    // Получить все отели
    public List<Hotel> getAllHotels() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            HotelRepository hotelRepository = new HotelRepository(conn);
            List<Hotel> hotels = hotelRepository.findAll();

            conn.commit();
            return hotels;
        } catch (SQLException e) {
            throw new SQLException("Ошибка при получении списка отелей", e);
        }
    }

    //Найти отель по ID
    public Hotel findById(Long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            HotelRepository hotelRepository = new HotelRepository(conn);
            Hotel hotel = hotelRepository.findById(id);

            conn.commit();
            return hotel;
        } catch (SQLException e) {
            throw new SQLException("Ошибка при поиске отеля по ID", e);
        }
    }

    //  Найти отели по количеству звёзд
    public List<Hotel> getHotelsByStars(int stars) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            HotelRepository hotelRepository = new HotelRepository(conn);
            List<Hotel> hotels = hotelRepository.findByStars(stars);

            conn.commit();
            return hotels;
        } catch (SQLException e) {
            throw new SQLException("Ошибка при поиске отелей по количеству звёзд", e);
        }
    }

    // Удалить отель
    public void deleteHotel(Long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            HotelRepository hotelRepository = new HotelRepository(conn);
            hotelRepository.delete(id);

            conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Ошибка при удалении отеля", e);
        }
    }
}

