<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Авторизация</title>

    <style>
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: #eef2f7;
            margin: 0;
            padding: 40px;
        }

        .container {
            max-width: 480px;
            margin: auto;
            background: white;
            padding: 35px;
            border-radius: 14px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.15);
            animation: fadein 0.4s ease;
        }

        h1 {
            margin-top: 0;
            text-align: center;
            color: #333;
            font-size: 26px;
        }

        label {
            font-size: 16px;
            font-weight: 600;
            margin-bottom: 6px;
            display: block;
        }

        input {
            width: 100%;
            padding: 12px;
            border-radius: 10px;
            border: 1px solid #ccc;
            margin-bottom: 18px;
            font-size: 16px;
        }

        .btn-save {
            width: 100%;
            padding: 14px;
            background: linear-gradient(135deg, #007bff, #3399ff);
            border: none;
            color: white;
            font-size: 18px;
            border-radius: 12px;
            cursor: pointer;
            transition: 0.25s;
        }

        .btn-save:hover {
            background: linear-gradient(135deg, #006ee6, #2e8ae6);
            transform: translateY(-2px);
            box-shadow: 0 4px 14px rgba(0,0,0,0.18);
        }

        .error {
            color: red;
            text-align: center;
            font-size: 16px;
            margin-bottom: 15px;
        }

        @keyframes fadein {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>

<body>

<div class="container">
    <h1>Вход в аккаунт</h1>

    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">

        <label>Имя</label>
        <input type="text" name="firstName" required>

        <label>Фамилия</label>
        <input type="text" name="lastName" required>

        <label>Телефон</label>
        <input type="text" name="phone" placeholder="+7XXXXXXXXXX" required>

        <label>Пароль</label>
        <input type="password" name="password" required>

        <button class="btn-save" type="submit">Войти</button>
    </form>
</div>

</body>
</html>
