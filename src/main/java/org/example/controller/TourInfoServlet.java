package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Tour;
import org.example.service.HotelService;
import org.example.service.ProgramService;
import org.example.service.TourService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/tour")
public class TourInfoServlet extends HttpServlet {

    private final TourService tourService = new TourService();
    private final HotelService hotelService = new HotelService();
    private final ProgramService programService = new ProgramService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");

        if (idParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не указан ID тура");
            return;
        }

        long id;
        try {
            id = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный ID тура");
            return;
        }

        try {
            Tour tour = tourService.findById(id);

            if (tour == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Тур не найден");
                return;
            }

            req.setAttribute("tour", tour);


            if (tour.getHotelId() != null) {
                req.setAttribute("hotel", hotelService.findById(tour.getHotelId()));
            }


            if (tour.getProgramId() != null) {
                req.setAttribute("program", programService.findById(tour.getProgramId()));
            }

            req.getRequestDispatcher("/tourDetails.jsp").forward(req, resp);

        } catch (SQLException e) {
            req.setAttribute("error", "Ошибка загрузки тура: " + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
