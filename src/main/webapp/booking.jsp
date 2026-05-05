<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="ru_BY"/>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Оформление заявки — ${tour.title}</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #eef2f7;
            margin: 0;
            padding: 40px;
        }

        .container {
            max-width: 1100px;
            margin: auto;
            display: flex;
            gap: 40px;
        }

        .left, .right {
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 3px 12px rgba(0,0,0,0.13);
        }

        .left { width: 40%; }
        .right { width: 60%; }

        h2 { margin-top: 0; }

        .info p {
            margin: 8px 0;
            font-size: 16px;
        }

        input, select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 20px;
            border-radius: 8px;
            border: 1px solid #ccc;
            font-size: 16px;
        }

        .btn {
            padding: 12px 20px;
            font-size: 17px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
        }

        .btn-submit {
            background: #28a745;
            color: white;
        }

        .btn-cancel {
            background: #dc3545;
            color: white;
            margin-left: 10px;
        }

        .price-box {
            background: #f1f5fb;
            padding: 14px;
            border-radius: 8px;
            font-size: 18px;
        }
    </style>

    <script>
        function updateTotal() {
            let price = parseFloat(document.getElementById("price").value);
            let people = parseInt(document.getElementById("people").value);

            if (!isNaN(price) && !isNaN(people)) {
                document.getElementById("totalPrice").innerText = (price * people).toFixed(2) + " BYN";
            }
        }
    </script>
</head>

<body>

<div class="container">

    <!-- Левый блок -->
    <div class="left">
        <h2>Информация о туре</h2>

        <div class="info">
            <p><b>Название:</b> ${tour.title}</p>
            <p><b>Страна:</b> ${tour.country}</p>
            <p><b>Город:</b> ${tour.city}</p>

            <p><b>Даты:</b>
                <fmt:formatDate value="${tour.startDateAsDate}" pattern="dd.MM.yyyy"/> —
                <fmt:formatDate value="${tour.endDateAsDate}" pattern="dd.MM.yyyy"/>
            </p>

            <p><b>Цена за 1 человека:</b>
              <fmt:formatNumber value="${tour.price}"
                                type="number"
                                minFractionDigits="2"
                                maxFractionDigits="2"/> BYN

            </p>

            <p><b>Свободные места:</b> ${tour.availableSeats}</p>
        </div>
    </div>

    <!-- Правый блок -->
    <div class="right">
        <h2>Оформление заявки</h2>

        <form method="post" action="${pageContext.request.contextPath}/booking">

            <input type="hidden" name="tourId" value="${tour.tourId}">
            <input type="hidden" id="price" name="price" value="${tour.price.toPlainString()}">

            <label>Фамилия</label>
            <input type="text" name="lastName" required>

            <label>Имя</label>
            <input type="text" name="firstName" required>

            <label>Телефон</label>
            <input type="text" name="phone" required>

            <label>Email</label>
            <input type="email" name="email" required>

            <label>Количество человек</label>
            <input type="number" id="people" name="people" value="1" min="1"
                   max="${tour.availableSeats}" onchange="updateTotal()" required>

            <div class="price-box">
                <b>Итоговая цена:</b>
                <span id="totalPrice">
                    <fmt:formatNumber value="${tour.price}" pattern="#0.00"/> BYN
                </span>
            </div>

            <br>

            <button type="submit" class="btn btn-submit">Отправить</button>

            <a href="${pageContext.request.contextPath}/tour?id=${tour.tourId}">
                <button type="button" class="btn btn-cancel">Отмена</button>
            </a>
        </form>
    </div>

</div>

<script>
    updateTotal();
</script>

</body>
</html>
