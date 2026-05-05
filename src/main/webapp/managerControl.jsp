<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Панель управления</title>

    <style>
        body {
            background: #eef2f7;
            font-family: "Segoe UI", sans-serif;
            margin: 0;
            padding: 40px 0;
        }

        .container {
            max-width: 700px;
            margin: auto;
            background: white;
            padding: 35px;
            border-radius: 14px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.15);
            text-align: center;
        }

        h1 {
            color: #333;
            margin-top: 0;
            margin-bottom: 25px;
        }

        .btn {
            display: block;
            width: 100%;
            max-width: 400px;
            margin: 15px auto;
            padding: 14px;
            text-decoration: none;
            color: white;
            background: #4a76a8;
            border-radius: 10px;
            font-size: 18px;
            transition: background 0.2s;
        }

        .btn:hover {
            background: #3c5f86;
        }

        .back-btn {
            margin-top: 25px;
            display: inline-block;
            padding: 10px 20px;
            background: #d9534f;
            color: white;
            text-decoration: none;
            border-radius: 8px;
        }

        .back-btn:hover {
            background: #c14541;
        }
    </style>
</head>

<body>

<div class="container">
    <h1>Панель управления менеджера</h1>

    <a class="btn" href="${pageContext.request.contextPath}/managerBookings">
        Управление заявками
    </a>

    <a class="btn" href="${pageContext.request.contextPath}/managerHotel">
        Управление отелями
    </a>

    <a class="btn" href="${pageContext.request.contextPath}/managerTours">
        Управление турами
    </a>

    <a class="btn" href="${pageContext.request.contextPath}/managerPrograms">
        Управление программами
    </a>

    <a class="back-btn" href="${pageContext.request.contextPath}/profile">
        Назад в профиль
    </a>
</div>

</body>
</html>
