package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Tour;
import org.example.service.TourService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/search-tours")
public class SearchToursServlet extends HttpServlet {

    private TourService tourService = new TourService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String country = req.getParameter("country");
        String type = req.getParameter("type");
        String date = req.getParameter("date");

        Double priceMin = parseDouble(req.getParameter("priceMin"));
        Double priceMax = parseDouble(req.getParameter("priceMax"));
        Integer seats = parseInt(req.getParameter("seats"));

        System.out.println("[SearchToursServlet] Параметры поиска:");
        System.out.println("  country=" + country + ", type=" + type + ", date=" + date);
        System.out.println("  priceMin=" + priceMin + ", priceMax=" + priceMax + ", seats=" + seats);

        try {
            List<Tour> tours;
            
            // Если все параметры пустые, показываем все туры
            if (isEmpty(country) && isEmpty(type) && isEmpty(date) && 
                priceMin == null && priceMax == null && seats == null) {
                System.out.println("[SearchToursServlet] Параметры не заданы, загружаем все туры");
                tours = tourService.getAllTours();
            } else {
                tours = tourService.search(country, type, date, priceMin, priceMax, seats);
            }

            System.out.println("[SearchToursServlet] Найдено туров: " + tours.size());
            req.setAttribute("tours", tours);

            // переход на JSP
            req.getRequestDispatcher("/searchResults.jsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Ошибка поиска туров: " + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
    
    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private Double parseDouble(String value) {
        try {
            if (value == null || value.isEmpty()) return null;
            return Double.parseDouble(value);
        } catch (Exception e) {
            return null;
        }
    }

    private Integer parseInt(String value) {
        try {
            if (value == null || value.isEmpty()) return null;
            return Integer.parseInt(value);
        } catch (Exception e) {
            return null;
        }
    }
}
