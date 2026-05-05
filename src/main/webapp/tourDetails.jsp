<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="ru_RU"/>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>${tour.title}</title>

    <style>
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(180deg, #e9eef5, #f7f7f7);
            margin: 0;
            padding: 40px;
            color: #333;
        }

        .container {
            max-width: 900px;
            margin: auto;
            background: white;
            padding: 40px;
            border-radius: 14px;
            box-shadow: 0 4px 14px rgba(0,0,0,0.12);
            animation: fadein 0.4s ease;
        }

        h1 {
            font-size: 32px;
            margin-top: 0;
            color: #222;
            border-left: 6px solid #007bff;
            padding-left: 12px;
        }

        h2 {
            font-size: 24px;
            margin-bottom: 15px;
            color: #2d2d2d;
            border-left: 4px solid #007bff;
            padding-left: 10px;
        }

        .section {
            margin-top: 40px;
        }

        .info-block p {
            margin: 8px 0;
            font-size: 17px;
            color: #444;
        }

        .card {
            padding: 22px;
            border-radius: 12px;
            background: #fafbfd;
            border: 1px solid #dcdfe3;
            box-shadow: 0 2px 6px rgba(0,0,0,0.08);
        }

        .card p {
            margin: 8px 0;
            font-size: 16px;
            color: #444;
        }

        .btn-order {
            display: block;
            margin-top: 40px;
            padding: 14px;
            background: #28a745;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 10px;
            font-size: 20px;
            font-weight: 600;
            transition: 0.25s;
            box-shadow: 0 3px 10px rgba(0,0,0,0.15);
        }

        .btn-order:hover {
            background: #218838;
            transform: translateY(-2px);
            box-shadow: 0 6px 14px rgba(0,0,0,0.22);
        }

        @keyframes fadein {
            from { opacity: 0; transform: translateY(15px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>

<body>
<div class="container">

    <!-- === Основная информация о туре === -->
    <h1>${tour.title}</h1>

    <div class="info-block">
        <p><b>Страна:</b> ${tour.country}</p>
        <p><b>Город:</b> ${tour.city}</p>

        <p><b>Даты:</b>
            <fmt:formatDate value="${tour.startDateAsDate}" pattern="dd.MM.yyyy" /> —
            <fmt:formatDate value="${tour.endDateAsDate}" pattern="dd.MM.yyyy" />
        </p>

        <p><b>Стоимость:</b>
            <fmt:formatNumber value="${tour.price}" pattern="#0.00" /> BYN
        </p>

        <p><b>Доступные места:</b> ${tour.availableSeats}</p>

        <p><b>Описание:</b><br>${tour.description}</p>
    </div>

    <!-- === Программа тура === -->
    <div class="section">
        <h2>Программа тура</h2>

        <c:choose>
            <c:when test="${not empty program}">
                <div class="card">
                    <p><b>Название:</b> ${program.name}</p>
                    <p><b>Продолжительность:</b> ${program.duration}</p>
                    <p><b>Описание:</b></p>
                    <p>${program.description}</p>
                </div>
            </c:when>
            <c:otherwise>
                <p style="font-size: 16px; color: #777;">Программа для этого тура не указана.</p>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- === Гостиница === -->
    <div class="section">
        <h2>Гостиница</h2>

        <c:choose>
            <c:when test="${not empty hotel}">
                <div class="card">
                    <p><b>Название:</b> ${hotel.name}</p>
                    <p><b>Категория:</b> ${hotel.stars} ★</p>
                    <p><b>Цена за ночь:</b>
                        <fmt:formatNumber value="${hotel.price}" pattern="#0.00" /> BYN
                    </p>
                    <p><b>Описание:</b></p>
                    <p>${hotel.description}</p>
                </div>
            </c:when>
            <c:otherwise>
                <p style="font-size: 16px; color: #777;">Гостиница для этого тура не указана.</p>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- === Кнопка === -->
<a href="${pageContext.request.contextPath}/booking?tourId=${tour.tourId}" class="btn-order">
    Забронировать тур
</a>



</div>
</body>
</html>