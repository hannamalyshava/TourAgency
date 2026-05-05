package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.User;
import org.example.service.UserService;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Авторизация по имени, фамилии, телефону и паролю.
 */

@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // --- 1. Получение данных ---
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        // --- 2. Проверка на пустые поля ---
        if (firstName == null || firstName.isEmpty() ||
                lastName == null || lastName.isEmpty() ||
                phone == null || phone.isEmpty() ||
                password == null || password.isEmpty()) {

            req.setAttribute("error", "Введите имя, фамилию, номер телефона и пароль!");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        try {
            // --- 3. Поиск пользователя по данным ---
            User user = userService.loginByPersonalData(firstName, lastName, phone, password);

            if (user == null) {
                req.setAttribute("error", "Данные не совпадают. Проверьте введённые значения.");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }

            // --- 4. Создание сессии ---
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("role", user.getRole());
            
            System.out.println("[UserLoginServlet] User logged in: " + user.getFirstName() + " " + user.getLastName());
            System.out.println("[UserLoginServlet] User ID: " + user.getUserId());

            // --- 5. Переход в зависимости от роли ---
            if ("MANAGER".equals(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/profile");
            } else {
                resp.sendRedirect(req.getContextPath() + "/");
            }

        } catch (SQLException e) {
            req.setAttribute("error", "Ошибка авторизации: " + e.getMessage());
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
