<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="ru_RU"/>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Главная страница — Поиск туров</title>

    <style>
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background-color: #eef2f7;
            margin: 0;
            padding: 0;
            color: #333;
        }

        header {
            background: linear-gradient(135deg, #007bff, #0056b3);
            padding: 20px 40px;
            color: white;
            box-shadow: 0 2px 10px rgba(0,0,0,0.2);
        }

        header h1 {
            margin: 0;
            font-size: 28px;
            font-weight: 600;
        }

        .container {
            display: flex;
            padding: 30px;
            gap: 30px;
            max-width: 1300px;
            margin: 0 auto;
        }

        /* ----------- СТИЛИ ДЛЯ КНОПОК В ШАПКЕ ----------- */
        .nav-btn {
            padding: 10px 20px;
            background-color: rgba(255, 255, 255, 0.15);
            border: 1px solid rgba(255, 255, 255, 0.3);
            border-radius: 8px;
            color: white;
            font-size: 15px;
            font-weight: 500;
            cursor: pointer;
            text-decoration: none;
            transition: all 0.25s ease;
            margin-left: 10px;
            display: inline-block;
            text-align: center;
        }

        .nav-btn:hover {
            background-color: rgba(255, 255, 255, 0.25);
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            border-color: rgba(255, 255, 255, 0.4);
        }

        /* ----------- КНОПКА ПОИСКА ТУРА ----------- */
        .search-button-box {
            width: 30%;
            display: flex;
            justify-content: center;
            align-items: flex-start;
        }

        .big-button {
            padding: 20px 30px;
            background-color: #007bff;
            border: none;
            border-radius: 12px;
            color: white;
            font-size: 20px;
            font-weight: 600;
            cursor: pointer;
            width: 100%;
            text-align: center;
            text-decoration: none;
            transition: 0.25s;
            box-shadow: 0 3px 10px rgba(0,0,0,0.15);
        }

        .big-button:hover {
            background-color: #0056b3;
            transform: translateY(-3px);
            box-shadow: 0 6px 15px rgba(0,0,0,0.18);
        }

        /* ----------- ПРАВАЯ ЧАСТЬ — ТУРЫ ----------- */
        .tours {
            width: 70%;
        }

        .tours h2 {
            font-size: 26px;
            margin-top: 0;
            margin-bottom: 20px;
        }

        .tour-card {
            background: white;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.15);
            margin-bottom: 25px;
            transition: 0.2s;
        }

        .tour-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 18px rgba(0,0,0,0.18);
        }

        .tour-card h3 {
            margin-top: 0;
            font-size: 22px;
        }

        .tour-link {
            text-decoration: none;
            color: #007bff;
            font-weight: 600;
        }

        .tour-link:hover {
            text-decoration: underline;
        }

    </style>
</head>

<body>

<header>
    <div style="display: flex; justify-content: space-between; align-items: center;">
        <h1>Поиск туров и маршрутов</h1>

        <div>

            <c:choose>


                <c:when test="${not empty sessionScope.user}">
                    <a href="${pageContext.request.contextPath}/profile" class="nav-btn">
                        Личный кабинет
                    </a>

                    <a href="${pageContext.request.contextPath}/logout" class="nav-btn">
                        Выход
                    </a>
                </c:when>


                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/register.jsp" class="nav-btn">
                        Личный кабинет
                    </a>

                    <a href="${pageContext.request.contextPath}/register.jsp" class="nav-btn">
                        Регистрация
                    </a>

                    <a href="${pageContext.request.contextPath}/login.jsp" class="nav-btn">
                        Авторизация
                    </a>
                </c:otherwise>

            </c:choose>

        </div>
    </div>
</header>


<div class="container">

    <!-- ----------- КНОПКА ПОИСКА ТУРА ----------- -->
    <div class="search-button-box">
        <a class="big-button" href="${pageContext.request.contextPath}/search-tours">Поиск тура</a>
    </div>

    <!-- ----------- СПИСОК ТУРОВ ----------- -->
    <div class="tours">
        <h2>Туристические маршруты</h2>


        <c:if test="${not empty error}">
            <p style="color: red; font-size: 16px;">${error}</p>
        </c:if>

        <c:if test="${empty tours}">
            <p style="font-size: 17px; color: #555;">Туры не найдены.</p>
        </c:if>

        <c:forEach var="t" items="${tours}">
            <div class="tour-card">
                <h3>
                    <a class="tour-link" href="${pageContext.request.contextPath}/tour?id=${t.tourId}">
                        ${t.title}
                    </a>
                </h3>
                <p><b>Страна:</b> ${t.country}</p>
                <p><b>Даты:</b> ${t.startDate} — ${t.endDate}</p>
                <p><b>Цена:</b> <fmt:formatNumber value="${t.price}" pattern="#0.00" /> BYN</p>
                <p><b>Свободные места:</b> ${t.availableSeats}</p>
            </div>
        </c:forEach>

    </div>

</div>

</body>
</html>