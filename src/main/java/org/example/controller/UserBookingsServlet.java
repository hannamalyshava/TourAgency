package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.Booking;
import org.example.model.User;
import org.example.service.BookingService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Контроллер для отображения списка бронирований пользователя.
 * Метод: GET
 * Задача: турист запрашивает список своих бронирований.
 */
@WebServlet("/user/bookings")
public class UserBookingsServlet extends HttpServlet {

    private final BookingService bookingService = new BookingService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        // Проверяем, авторизован ли пользователь
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        try {
            // Получаем список бронирований пользователя
            List<Booking> bookings = bookingService.getBookingsByUser(user.getUserId());

            // Передаём список на JSP
            req.setAttribute("bookings", bookings);
            req.getRequestDispatcher("/WEB-INF/views/bookings/user-bookings.jsp")
                    .forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Ошибка при загрузке списка бронирований");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
