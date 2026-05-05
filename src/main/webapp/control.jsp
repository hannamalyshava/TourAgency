<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Панель управления</title>

    <style>
        body {
            background: linear-gradient(135deg, #e3eaf5, #f8fafc);
            font-family: Segoe UI, Arial, sans-serif;
            padding: 40px;
        }

        .container {
            max-width: 650px;
            margin: auto;
            background: white;
            padding: 40px;
            border-radius: 18px;
            box-shadow: 0 6px 22px rgba(0,0,0,0.15);
            text-align: center;
            animation: fadeIn .5s ease;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to   { opacity: 1; transform: translateY(0); }
        }

        h1 {
            color: #2c3e50;
            margin-bottom: 25px;
            font-size: 28px;
            font-weight: 600;
        }

        .btn {
            display: block;
            width: 100%;
            padding: 16px;
            margin: 14px 0;
            text-decoration: none;
            color: white;
            background: #4a76a8;
            border-radius: 12px;
            font-size: 18px;
            font-weight: 500;
            transition: 0.25s;
            box-shadow: 0 3px 10px rgba(74,118,168,0.25);
        }

        .btn:hover {
            background: #3c5f86;
            box-shadow: 0 6px 16px rgba(74,118,168,0.35);
            transform: translateY(-2px);
        }

        .footer {
            margin-top: 30px;
            font-size: 14px;
            color: #7a8a9c;
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

    <div class="footer">Менеджерская зона · TravelAgencySystem</div>
</div>

</body>
</html>
