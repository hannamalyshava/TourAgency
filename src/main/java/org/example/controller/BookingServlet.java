package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.Booking;
import org.example.model.Tour;
import org.example.service.BookingService;
import org.example.service.TourService;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {

    private final BookingService bookingService = new BookingService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        // Проверка авторизации
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        long userId = (long) session.getAttribute("userId");

        // Получаем параметры из формы
        String tourIdParam = req.getParameter("tourId");
        String peopleParam = req.getParameter("people");
        String priceParam = req.getParameter("price");
        System.out.println("PRICE = " + priceParam);

        if (tourIdParam == null || peopleParam == null || priceParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Недостаточно данных для бронирования");
            return;
        }

        try {
            long tourId = Long.parseLong(tourIdParam);
            int people = Integer.parseInt(peopleParam);
            BigDecimal price = new BigDecimal(priceParam);

            // Рассчитываем общую стоимость
            BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(people));

            // Создаём объект бронирования
            Booking booking = new Booking();
            booking.setUserId(userId);
            booking.setTourId(tourId);
            booking.setPeople(people);
            booking.setTotalPrice(totalPrice);
            booking.setStatus("PENDING"); // начальный статус

            //Сохраняем через сервис с уменьшением свободных мест
            bookingService.createBookingWithSeatsUpdate(booking);
            
            System.out.println("[BookingServlet] Booking created successfully for user " + userId + ", tour " + tourId + ", people: " + people);

            // Перенаправляем на страницу подтверждения
            resp.sendRedirect(req.getContextPath() + "/bookingSuccess.jsp");

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректные данные");
        } catch (SQLException e) {
        e.printStackTrace(); // <-- важно!
        String cause = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
        req.setAttribute("error", "Ошибка при создании бронирования: " + cause);
        req.getRequestDispatcher("/error.jsp").forward(req, resp);
    }

}


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String tourIdParam = req.getParameter("tourId");

        if (tourIdParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не указан ID тура");
            return;
        }

        try {
            long tourId = Long.parseLong(tourIdParam);

            // Загружаем тур
            TourService tourService = new TourService();
            Tour tour = tourService.findById(tourId);

            if (tour == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Тур не найден");
                return;
            }

            // Кладём тур в request
            req.setAttribute("tour", tour);

            // Переход на booking.jsp
            req.getRequestDispatcher("/booking.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", "Ошибка загрузки страницы бронирования: " + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

}
