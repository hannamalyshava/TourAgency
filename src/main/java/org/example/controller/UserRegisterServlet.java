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
 * Контроллер регистрации пользователя.
 * URL: /register
 */
@WebServlet("/register")
public class UserRegisterServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // --- 1. Получение данных из формы ---
        String lastName = req.getParameter("lastName");
        String firstName = req.getParameter("firstName");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // --- 2. Валидация минимальная ---
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            req.setAttribute("error", "Email и пароль обязательны!");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        String passwordConfirm = req.getParameter("passwordConfirm");

        if (!password.equals(passwordConfirm)) {
            req.setAttribute("error", "Пароли не совпадают!");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        // --- 3. Создание объекта пользователя ---
        User user = new User();
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("CLIENT"); // Роль по умолчанию

        try {
            // --- 4. Вызов сервиса ---
            userService.registerUser(user);

            // --- 5. АВТО-ЛОГИН ---
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            // --- 6. Переход в личный кабинет ---
            resp.sendRedirect(req.getContextPath() + "/profile.jsp");

        } catch (SQLException e) {
            // --- 7. Ошибка — возврат с сообщением ---
            req.setAttribute("error", "Ошибка регистрации: " + e.getMessage());
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
