package org.example.controller;

import org.example.model.Tour;
import org.example.service.TourService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/managerTours")
public class ManagerTourServlet extends HttpServlet {

    private final TourService tourService = new TourService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            List<Tour> tours = tourService.getAllTours();
            req.setAttribute("tours", tours);

            req.getRequestDispatcher("/managerTours.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Ошибка загрузки туров", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            resp.sendRedirect(req.getContextPath() + "/managerTours");
            return;
        }

        try {
            switch (action) {

                case "add":
                    addTour(req);
                    break;

                case "delete":
                    deleteTour(req);
                    break;

                default:
                    break;
            }

            resp.sendRedirect(req.getContextPath() + "/managerTours");

        } catch (SQLException e) {
            throw new ServletException("Ошибка при обработке действия: " + action, e);
        }
    }

    // --- Создание тура ---
    private void addTour(HttpServletRequest req) throws SQLException {
        Tour tour = new Tour();
        tour.setTitle(req.getParameter("title"));
        tour.setDescription(req.getParameter("description"));
        tour.setCountry(req.getParameter("country"));
        tour.setCity(req.getParameter("city"));
        tour.setPrice(new BigDecimal(req.getParameter("price")));
        tour.setStartDate(LocalDate.parse(req.getParameter("startDate")));
        tour.setEndDate(LocalDate.parse(req.getParameter("endDate")));
        tour.setAvailableSeats(Integer.parseInt(req.getParameter("seats")));

        String hotelId = req.getParameter("hotelId");
        tour.setHotelId(hotelId == null || hotelId.isEmpty() ? null : Long.valueOf(hotelId));

        String programId = req.getParameter("programId");
        tour.setProgramId(programId == null || programId.isEmpty() ? null : Long.valueOf(programId));

        tour.setCreatedAt(LocalDateTime.now());

        tourService.addTour(tour);
    }

    // --- Удаление тура ---
    private void deleteTour(HttpServletRequest req) throws SQLException {
        long id = Long.parseLong(req.getParameter("id"));
        tourService.deleteTour(id);
    }
}
