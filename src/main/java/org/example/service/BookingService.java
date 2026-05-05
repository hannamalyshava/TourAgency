package org.example.service;

import org.example.bd.DatabaseConnection;
import org.example.model.Booking;
import org.example.model.Tour;
import org.example.repository.TourRepository;
import org.example.repository.BookingRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Сервис для работы с бронированиями.
 * Каждый метод выполняет одну бизнес-операцию в рамках одной транзакции.
 */


public class BookingService {

    // --- 1. Добавить новое бронирование ---
    public void createBooking(Booking booking) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // начало транзакции

            BookingRepository bookingRepository = new BookingRepository(conn);
            bookingRepository.add(booking);

            conn.commit(); // фиксируем изменения
        } catch (SQLException e) {
            throw new SQLException("Ошибка при создании бронирования", e);
        }
    }

    // --- 1.1. Добавить новое бронирование с уменьшением свободных мест ---
    public void createBookingWithSeatsUpdate(Booking booking) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // начало транзакции

            // Сначала проверяем и уменьшаем свободные места
            TourRepository tourRepository = new TourRepository(conn);
            
            // Проверяем, достаточно ли мест
            Tour tour = tourRepository.findById(booking.getTourId());
            if (tour == null) {
                throw new SQLException("Тур не найден");
            }
            
            if (tour.getAvailableSeats() < booking.getPeople()) {
                throw new SQLException("Недостаточно свободных мест. Доступно: " + tour.getAvailableSeats() + ", запрошено: " + booking.getPeople());
            }
            
            // Уменьшаем количество мест
            tourRepository.decreaseAvailableSeats(booking.getTourId(), booking.getPeople());
            
            // Создаём бронирование
            BookingRepository bookingRepository = new BookingRepository(conn);
            bookingRepository.add(booking);

            conn.commit(); // фиксируем изменения
            
            System.out.println("[BookingService] Booking created and seats decreased for tour " + booking.getTourId());
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // откатываем изменения при ошибке
                    System.out.println("[BookingService] Transaction rolled back due to error");
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new SQLException("Ошибка при создании бронирования: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

    // --- 2. Получить все бронирования ---
    public List<Booking> getAllBookings() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            BookingRepository bookingRepository = new BookingRepository(conn);
            List<Booking> bookings = bookingRepository.findAll();

            conn.commit();
            return bookings;
        } catch (SQLException e) {
            throw new SQLException("Ошибка при получении списка бронирований", e);
        }
    }

    // --- 3. Получить бронирования по пользователю ---
    public List<Booking> getBookingsByUser(long userId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            BookingRepository bookingRepository = new BookingRepository(conn);
            List<Booking> bookings = bookingRepository.findByUserId(userId);

            conn.commit();
            return bookings;
        } catch (SQLException e) {
            throw new SQLException("Ошибка при получении бронирований пользователя", e);
        }
    }

    // --- 4. Получить бронирования по статусу ---
    public List<Booking> getBookingsByStatus(String status) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            BookingRepository bookingRepository = new BookingRepository(conn);
            List<Booking> bookings = bookingRepository.findByStatus(status);

            conn.commit();
            return bookings;
        } catch (SQLException e) {
            throw new SQLException("Ошибка при получении бронирований по статусу", e);
        }
    }

    // --- 5. Обновить статус бронирования ---
    public void updateBookingStatus(long bookingId, String newStatus) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            BookingRepository bookingRepository = new BookingRepository(conn);
            bookingRepository.updateStatus(bookingId, newStatus);

            conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Ошибка при обновлении статуса бронирования", e);
        }
    }

    // --- 5.1. Обновить статус бронирования с возвратом мест при отмене ---
    public void updateBookingStatusWithSeatsUpdate(long bookingId, String newStatus) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            BookingRepository bookingRepository = new BookingRepository(conn);
            
            // Если статус меняется на CANCELLED, возвращаем места
            if ("CANCELLED".equals(newStatus)) {
                // Получаем информацию о бронировании
                List<Booking> allBookings = bookingRepository.findAll();
                Booking booking = null;
                for (Booking b : allBookings) {
                    if (b.getBookingId() == bookingId) {
                        booking = b;
                        break;
                    }
                }
                
                if (booking != null && !"CANCELLED".equals(booking.getStatus())) {
                    // Возвращаем места только если бронирование ещё не было отменено
                    TourRepository tourRepository = new TourRepository(conn);
                    tourRepository.increaseAvailableSeats(booking.getTourId(), booking.getPeople());
                    System.out.println("[BookingService] Returned " + booking.getPeople() + " seats for tour " + booking.getTourId());
                }
            }
            
            // Обновляем статус
            bookingRepository.updateStatus(bookingId, newStatus);

            conn.commit();
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new SQLException("Ошибка при обновлении статуса бронирования: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

    // --- 6. Удалить бронирование ---
    public void deleteBooking(long bookingId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            BookingRepository bookingRepository = new BookingRepository(conn);
            bookingRepository.delete(bookingId);

            conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Ошибка при удалении бронирования", e);
        }
    }
}
