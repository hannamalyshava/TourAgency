<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Результаты поиска туров</title>

    <style>
        /* Стиль взят из примера */
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background-color: #eef2f7; /* Светло-голубой фон */
            margin: 0;
            padding: 0;
            color: #333;
        }

        /* Стиль взят из примера */
        header {
            background: linear-gradient(135deg, #007bff, #0056b3); /* Синий градиент */
            padding: 20px 40px;
            color: white;
            box-shadow: 0 2px 10px rgba(0,0,0,0.2);
        }

        header h1 {
            margin: 0;
            font-size: 28px;
            font-weight: 600;
        }

        /* Адаптация стиля контейнера из примера */
        .container {
            display: flex;
            padding: 30px;
            gap: 30px;
            max-width: 1300px;
            margin: 0 auto;
        }

        /* ------------------ 1. ЛЕВАЯ ПАНЕЛЬ (ПОИСК/ФИЛЬТР) ------------------ */
        .search-panel {
            flex: 0 0 300px;
            padding: 25px;
            background: white;
            border-radius: 12px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.15);
            height: fit-content;
            position: sticky;
            top: 30px;
        }

        .search-panel h3 {
            margin-top: 0;
            font-size: 22px;
            color: #007bff;
            border-bottom: 2px solid #eef2f7;
            padding-bottom: 15px;
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-top: 15px;
            font-weight: 600;
            color: #333;
        }

        input[type="text"], input[type="date"], select {
            width: 100%;
            padding: 12px;
            margin-top: 8px;
            border-radius: 8px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            font-size: 16px;
        }

        /* Единый стиль для кнопок */
        .panel-button {
            margin-top: 25px;
            padding: 12px;
            border: none;
            border-radius: 12px;
            color: white;
            width: 100%;
            cursor: pointer;
            font-size: 18px;
            font-weight: 600;
            transition: 0.25s;
            box-shadow: 0 3px 10px rgba(0,0,0,0.15);
        }

        .panel-button.submit-button {
            background-color: #007bff;
        }
        .panel-button.submit-button:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
            box-shadow: 0 5px 12px rgba(0,0,0,0.18);
        }

        .panel-button.reset-button {
            background-color: #6c757d;
            margin-top: 10px;
            font-size: 16px;
        }
        .panel-button.reset-button:hover {
            background-color: #5a6268;
            transform: translateY(-2px);
            box-shadow: 0 5px 12px rgba(0,0,0,0.18);
        }

        /* ------------------ 2. ПРАВАЯ ПАНЕЛЬ (РЕЗУЛЬТАТЫ) ------------------ */
        .results-panel {
            width: 70%;
        }

        .results-panel h2 {
            font-size: 26px;
            margin-top: 0;
            margin-bottom: 20px;
            font-weight: 600;
        }

        .tour-card {
            background: white;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.15);
            margin-bottom: 25px;
            transition: 0.2s;
            /* border-left: 5px solid #007bff;
        }

        .tour-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 18px rgba(0,0,0,0.18);
        }

        .tour-card h3 {
            margin-top: 0;
            font-size: 24px; /* Чуть крупнее */
            margin-bottom: 10px;
        }

        .tour-link {
            text-decoration: none;
            color: #007bff;
            font-weight: 600;
        }
        .tour-link:hover {
            text-decoration: underline;
        }

        .tour-meta {
            margin-top: 15px;
            margin-bottom: 15px;
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            font-size: 15px;
            color: #555;
            border-top: 1px solid #eee;
            border-bottom: 1px solid #eee;
            padding: 10px 0;
        }
        .tour-meta span b {
            color: #333;
            font-weight: 700;
        }

        .error-message {
            color: #dc3545;
            font-weight: 600;
            font-size: 17px;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        .info-text {
            font-size: 17px;
            color: #555;
        }
    </style>

    <fmt:setLocale value="ru_RU"/>

</head>
<body>

<header>
    <h1>Результаты поиска туров</h1>
</header>

<div class="container">

    <div class="search-panel">
        <h3>🔍 Фильтр туров</h3>

        <form action="${pageContext.request.contextPath}/search-tours" method="get">

            <label for="country">Страна:</label>
            <select name="country" id="country">
                <option value="">— Любая —</option>
                <option value="Россия" <c:if test="${param.country == 'Россия'}">selected</c:if>>Россия</option>
                <option value="Египет" <c:if test="${param.country == 'Египет'}">selected</c:if>>Египет</option>
                <option value="Италия" <c:if test="${param.country == 'Италия'}">selected</c:if>>Италия</option>
                <option value="Швейцария" <c:if test="${param.country == 'Швейцария'}">selected</c:if>>Швейцария</option>
                <option value="Греция" <c:if test="${param.country == 'Греция'}">selected</c:if>>Греция</option>
                <option value="Австрия" <c:if test="${param.country == 'Австрия'}">selected</c:if>>Австрия</option>
            </select>

            <label for="type">Тип отдыха:</label>
            <select name="type" id="type">
                <option value="">— Любой —</option>
                <option value="Пляжный" <c:if test="${param.type == 'Пляжный'}">selected</c:if>>Пляжный</option>
                <option value="Горнолыжный" <c:if test="${param.type == 'Горнолыжный'}">selected</c:if>>Горнолыжный</option>
                <option value="Экскурсионный" <c:if test="${param.type == 'Экскурсионный'}">selected</c:if>>Экскурсионный</option>
            </select>

            <label for="date">Дата начала:</label>
            <input type="date" name="date" id="date" value="${param.date}">

            <label for="priceMin">Цена от :</label>
            <input type="text" name="priceMin" id="priceMin" placeholder="Например, 500" value="${param.priceMin}">

            <label for="priceMax">Цена до :</label>
            <input type="text" name="priceMax" id="priceMax" placeholder="Например, 1000" value="${param.priceMax}">

            <label for="seats">Мин. мест:</label>
            <select name="seats" id="seats">
                <option value="">Не важно</option>
                <option value="1" <c:if test="${param.seats == '1'}">selected</c:if>>1+</option>
                <option value="2" <c:if test="${param.seats == '2'}">selected</c:if>>2+</option>
                <option value="4" <c:if test="${param.seats == '4'}">selected</c:if>>4+</option>
                <option value="10" <c:if test="${param.seats == '10'}">selected</c:if>>10+</option>
            </select>

            <button type="submit" class="panel-button submit-button">Найти туры</button>
        </form>

        <a href="${pageContext.request.contextPath}/search-tours">
            <button type="button" class="panel-button reset-button">Сбросить фильтр</button>
        </a>

    </div>

    <div class="results-panel">
        <h2> Найденные туры</h2>

        <c:if test="${not empty error}">
            <p class="error-message">Ошибка: ${error}</p>
        </c:if>

        <c:choose>
            <c:when test="${empty tours}">
                <p class="info-text">По вашему запросу туры не найдены. Попробуйте изменить критерии поиска.</p>
            </c:when>

            <c:otherwise>
                <p class="info-text">Найдено туров: **${fn:length(tours)}**</p>

                <c:forEach var="t" items="${tours}">
                    <div class="tour-card">
                        <h3>
                            <a class="tour-link"
                               href="${pageContext.request.contextPath}/tour?id=${t.tourId}">
                                ${t.title}
                            </a>
                        </h3>

                        <div class="tour-meta">
                            <span><b>Даты:</b>
                                <fmt:formatDate value="${t.startDateAsDate}" pattern="dd.MM.yyyy"/>
                                —
                                <fmt:formatDate value="${t.endDateAsDate}" pattern="dd.MM.yyyy"/>
                            </span>
                            <span><b>Страна:</b> ${t.country}</span>
                            <span><b>Цена:</b>
                                <fmt:formatNumber value="${t.price}" pattern="#0.00" /> BYN
                            </span>
                            <span><b>Места:</b> ${t.availableSeats}</span>
                        </div>
                        <p class="info-text">${t.description}</p> <%-- Описание тура --%>
                    </div>
                </c:forEach>

            </c:otherwise>
        </c:choose>

    </div>

</div>

</body>
</html>