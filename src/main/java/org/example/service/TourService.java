package org.example.service;

import org.example.bd.DatabaseConnection;
import org.example.model.Tour;
import org.example.repository.TourRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Сервис для работы с турами.
 * Все операции выполняются в рамках одной транзакции.
 */

public class TourService {

    // Добавить новый тур
    public void addTour(Tour tour) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // начало транзакции

            TourRepository repository = new TourRepository(conn);
            repository.add(tour);

            conn.commit(); // подтверждение изменений
        } catch (SQLException e) {
            throw new SQLException("Ошибка при добавлении тура", e);
        }
    }

    // Получить все туры
    public List<Tour> getAllTours() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            TourRepository repository = new TourRepository(conn);
            List<Tour> tours = repository.findAll();

            return tours;

        } catch (SQLException e) {
            System.err.println("Ошибка при получении списка туров: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            throw new SQLException("Ошибка при получении списка туров: " + e.getMessage(), e);
        }
    }

    //  Найти туры по стране
    public List<Tour> getToursByCountry(String country) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            TourRepository repository = new TourRepository(conn);
            List<Tour> tours = repository.findByCountry(country);

            conn.commit();
            return tours;
        } catch (SQLException e) {
            throw new SQLException("Ошибка при поиске туров по стране", e);
        }
    }

    // Найти туры по диапазону цен
    public List<Tour> getToursByPriceRange(double min, double max) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            TourRepository repository = new TourRepository(conn);
            List<Tour> tours = repository.findByPriceRange(min, max);

            conn.commit();
            return tours;
        } catch (SQLException e) {
            throw new SQLException("Ошибка при поиске туров по диапазону цен", e);
        }
    }

    //  Удалить тур по ID
    public void deleteTour(long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            TourRepository repository = new TourRepository(conn);
            repository.deleteById(id);

            conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Ошибка при удалении тура", e);
        }
    }

    // Поиск туров по нескольким критериям
    public List<Tour> search(String country, String type, String date,
                             Double priceMin, Double priceMax, Integer seats) throws SQLException {

        try (Connection conn = DatabaseConnection.getConnection()) {

            conn.setAutoCommit(false); // начало транзакции

            TourRepository repository = new TourRepository(conn);

            List<Tour> tours = repository.search(country, type, date, priceMin, priceMax, seats);

            conn.commit(); // завершение транзакции

            return tours;

        } catch (SQLException e) {
            throw new SQLException("Ошибка при поиске туров", e);
        }
    }

    //  Найти тур по ID
    public Tour findById(long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            TourRepository repository = new TourRepository(conn);
            Tour tour = repository.findById(id); //дописать этот метод в репозитории

            conn.commit();
            return tour;

        } catch (SQLException e) {
            throw new SQLException("Ошибка при поиске тура по ID", e);
        }
    }

    //Обновить существующий тур
    public void updateTour(Tour tour) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // начало транзакции

            TourRepository repository = new TourRepository(conn);
            repository.update(tour); // вызываем метод из репозитория

            conn.commit(); // подтверждаем изменения
        } catch (SQLException e) {
            throw new SQLException("Ошибка при обновлении тура", e);
        }
    }

    // Уменьшить количество свободных мест
    public void decreaseAvailableSeats(long tourId, int seatsToDecrease) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // начало транзакции

            TourRepository repository = new TourRepository(conn);
            
            // Проверяем, достаточно ли мест
            Tour tour = repository.findById(tourId);
            if (tour == null) {
                throw new SQLException("Тур не найден");
            }
            
            if (tour.getAvailableSeats() < seatsToDecrease) {
                throw new SQLException("Недостаточно свободных мест. Доступно: " + tour.getAvailableSeats());
            }
            
            // Уменьшаем количество мест
            repository.decreaseAvailableSeats(tourId, seatsToDecrease);

            conn.commit(); // подтверждаем изменения
        } catch (SQLException e) {
            throw new SQLException("Ошибка при уменьшении свободных мест: " + e.getMessage(), e);
        }
    }

    // Увеличить количество свободных мест (при отмене бронирования)
    public void increaseAvailableSeats(long tourId, int seatsToIncrease) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // начало транзакции

            TourRepository repository = new TourRepository(conn);
            repository.increaseAvailableSeats(tourId, seatsToIncrease);

            conn.commit(); // подтверждаем изменения
        } catch (SQLException e) {
            throw new SQLException("Ошибка при увеличении свободных мест: " + e.getMessage(), e);
        }
    }

}
