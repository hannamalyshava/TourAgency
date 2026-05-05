package org.example.controller;

import org.example.model.Program;
import org.example.service.ProgramService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/managerPrograms")
public class ManagerProgramServlet extends HttpServlet {

    private final ProgramService programService = new ProgramService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            List<Program> programs = programService.getAllPrograms();
            req.setAttribute("programs", programs);

            req.getRequestDispatcher("/managerPrograms.jsp")
                    .forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Ошибка получения списка программ", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        try {
            // --- Удаление программы ---
            if ("delete".equals(action)) {
                long id = Long.parseLong(req.getParameter("id"));
                programService.deleteProgram(id);
            }
            // --- Добавление программы ---
            else {
                Program p = new Program();
                p.setName(req.getParameter("name"));
                p.setDescription(req.getParameter("description"));
                p.setDuration(Integer.parseInt(req.getParameter("duration")));
                p.setCreatedAt(LocalDateTime.now());

                programService.addProgram(p);
            }

            resp.sendRedirect(req.getContextPath() + "/managerPrograms");

        } catch (SQLException e) {
            throw new ServletException("Ошибка обработки программы", e);
        }
    }
}
