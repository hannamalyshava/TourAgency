package org.example.controller;

import org.example.model.Hotel;
import org.example.service.HotelService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;



@WebServlet("/managerHotel")
public class ManagerHotelServlet extends HttpServlet {

    private final HotelService hotelService = new HotelService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            List<Hotel> hotels = hotelService.getAllHotels();
            req.setAttribute("hotels", hotels);

            req.getRequestDispatcher("/managerHotel.jsp")
                    .forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Ошибка загрузки списка отелей", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        try {
            switch (action) {

                case "add":
                    addHotel(req);
                    break;

                case "delete":
                    deleteHotel(req);
                    break;

                default:
                    throw new ServletException("Неизвестное действие: " + action);
            }

            resp.sendRedirect(req.getContextPath() + "/managerHotel");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void addHotel(HttpServletRequest req) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setName(req.getParameter("name"));
        hotel.setStars(Integer.parseInt(req.getParameter("stars")));
        hotel.setDescription(req.getParameter("description"));
        hotel.setPrice(new BigDecimal(req.getParameter("price")));
        hotel.setCreatedAt(LocalDateTime.now());

        hotelService.addHotel(hotel);
    }

    private void deleteHotel(HttpServletRequest req) throws SQLException {
        Long id = Long.parseLong(req.getParameter("id"));
        hotelService.deleteHotel(id);
    }
}