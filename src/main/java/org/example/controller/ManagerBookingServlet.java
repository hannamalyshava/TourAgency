package org.example.controller;

import org.example.service.BookingService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/managerBookings")
public class ManagerBookingServlet extends HttpServlet {

    private final BookingService bookingService = new BookingService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            req.setAttribute("bookings", bookingService.getAllBookings());
            req.getRequestDispatcher("/managerBookings.jsp")
                    .forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Ошибка загрузки списка заявок", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        try {
            // --- Удаление заявки ---
            if ("delete".equals(action)) {
                long id = Long.parseLong(req.getParameter("id"));
                bookingService.deleteBooking(id);
            }

            // --- Обновление статуса заявки ---
            else if ("updateStatus".equals(action)) {
                long bookingId = Long.parseLong(req.getParameter("bookingId"));
                String status = req.getParameter("status");
                bookingService.updateBookingStatusWithSeatsUpdate(bookingId, status);
            }

            // Неизвестное действие
            else {
                throw new ServletException("Неизвестное действие: " + action);
            }

            resp.sendRedirect(req.getContextPath() + "/managerBookings");

        } catch (SQLException e) {
            throw new ServletException("Ошибка при обработке заявки", e);
        }
    }
}
