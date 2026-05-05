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

@WebServlet(value = {"/", "/index"})
public class MainPageServlet extends HttpServlet {

    private final TourService tourService = new TourService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("========================================");
        System.out.println("[MainPageServlet] ВЫЗВАН!");
        System.out.println("[MainPageServlet] Request URI: " + req.getRequestURI());
        System.out.println("[MainPageServlet] Context Path: " + req.getContextPath());
        System.out.println("[MainPageServlet] Servlet Path: " + req.getServletPath());
        System.out.println("[MainPageServlet] Loading tours...");
        
        try {
            List<Tour> toursList = tourService.getAllTours();
            System.out.println("[MainPageServlet] Количество туров из БД: " + toursList.size());
            
            if (!toursList.isEmpty()) {
                System.out.println("[MainPageServlet] Первый тур: " + toursList.get(0).getTitle());
            } else {
                System.out.println("[MainPageServlet] WARNING: Список туров пуст!");
            }
            
            req.setAttribute("tours", toursList);
            System.out.println("[MainPageServlet] Атрибут 'tours' установлен");
            
        } catch (SQLException e) {
            e.printStackTrace();
            String errorMessage = "Ошибка загрузки туров: " + e.getMessage();
            if (e.getCause() != null) {
                errorMessage += " (Причина: " + e.getCause().getMessage() + ")";
            }
            if (e.getSQLState() != null) {
                errorMessage += " [SQL State: " + e.getSQLState() + "]";
            }
            System.err.println("[MainPageServlet] Детали ошибки: " + errorMessage);
            req.setAttribute("error", errorMessage);
            req.setAttribute("tours", new java.util.ArrayList<>()); // Устанавливаем пустой список при ошибке
        }

        System.out.println("[MainPageServlet] Перенаправление на /index.jsp");
        System.out.println("========================================");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
