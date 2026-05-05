<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Управление турами</title>

    <style>
        body {
            background:#eef2f7;
            font-family:Segoe UI, sans-serif;
            padding:40px;
            margin:0;
        }

        .container {
            max-width:1200px;
            margin:auto;
            background:white;
            padding:40px;
            border-radius:16px;
            box-shadow:0 6px 20px rgba(0,0,0,0.12);
            animation: fadein 0.5s ease;
        }

        h2, h3 {
            color:#007bff;
            font-weight:600;
        }

        h2 {
            text-align:center;
            font-size:28px;
            margin-top:0;
        }

        /* --- Поля формы растянутые на весь экран --- */
        form .field {
            display:flex;
            flex-direction:column;
            margin-bottom:18px;
            width:100%;
        }

        form input, form textarea {
            padding:12px;
            border-radius:10px;
            border:1px solid #ccc;
            font-size:16px;
            width:100%;
            box-sizing:border-box;
        }

        textarea {
            height:100px;
        }

        /* --- Grid для расположения полей --- */
        .form-grid {
            display:grid;
            grid-template-columns: 1fr 1fr;
            gap:25px 35px;
            margin-bottom:20px;
        }

        .form-grid textarea {
            grid-column: 1 / -1;
        }

        /* --- Кнопки --- */
        .btn {
            padding:12px 20px;
            border:none;
            border-radius:12px;
            color:white;
            font-size:16px;
            font-weight:600;
            cursor:pointer;
            transition:0.25s;
            background:linear-gradient(135deg, #007bff, #3399ff);
        }

        .btn:hover {
            transform:translateY(-2px);
            box-shadow:0 4px 14px rgba(0,0,0,0.18);
        }

        .btn-red {
            background:linear-gradient(135deg, #d9534f, #e74c3c);
        }

        .btn-red:hover {
            box-shadow:0 4px 14px rgba(255,0,0,0.25);
        }

        table {
            width:100%;
            border-collapse:separate;
            border-spacing:0 12px;
            margin-top:25px;
        }

        th {
            background:#f0f4f8;
            padding:14px 16px;
            font-size:15px;
            font-weight:600;
            border-radius:10px 10px 0 0;
        }

        td {
            padding:14px 16px;
            background:white;
            font-size:15px;
            border-bottom-left-radius:10px;
            border-bottom-right-radius:10px;
        }

        tr {
            background:white;
            box-shadow:0 3px 10px rgba(0,0,0,0.08);
            transition:0.2s;
        }

        tr:hover {
            transform:translateY(-2px);
            box-shadow:0 6px 18px rgba(0,0,0,0.15);
        }

        hr {
            margin:40px 0;
            opacity:0.3;
        }

        .back-btn {
            display:inline-block;
            margin-top:30px;
            padding:12px 25px;
            background:linear-gradient(135deg, #6c757d, #495057);
            color:white;
            border-radius:12px;
            font-size:16px;
            font-weight:600;
            text-decoration:none;
            transition:0.25s;
        }

        .back-btn:hover {
            transform:translateY(-2px);
            box-shadow:0 4px 14px rgba(0,0,0,0.2);
        }

        @keyframes fadein {
            from { opacity: 0; transform: translateY(10px); }
            to   { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>

<body>
<div class="container">
    <h2>Управление турами</h2>

    <h3>Добавить тур</h3>

    <form method="post" action="${pageContext.request.contextPath}/managerTours">
        <input type="hidden" name="action" value="add">

        <div class="form-grid">

            <div class="field">
                <label>Название:</label>
                <input name="title" required>
            </div>

            <div class="field">
                <label>Страна:</label>
                <input name="country" required>
            </div>

            <div class="field">
                <label>Город:</label>
                <input name="city" required>
            </div>

            <div class="field">
                <label>Цена:</label>
                <input name="price" type="number" step="0.01" required>
            </div>

            <div class="field">
                <label>Дата начала:</label>
                <input name="startDate" type="date" required>
            </div>

            <div class="field">
                <label>Дата конца:</label>
                <input name="endDate" type="date" required>
            </div>

            <div class="field">
                <label>Места:</label>
                <input name="seats" type="number" required>
            </div>

            <div class="field">
                <label>Отель ID (опционально):</label>
                <input name="hotelId" type="number">
            </div>

            <div class="field">
                <label>Программа ID (опционально):</label>
                <input name="programId" type="number">
            </div>

            <div class="field">
                <label>Описание:</label>
                <textarea name="description"></textarea>
            </div>

        </div>

        <button class="btn">Добавить</button>
    </form>

    <hr>

    <h3>Список туров</h3>

    <table>
        <tr>
            <th>ID</th>
            <th>Название</th>
            <th>Страна</th>
            <th>Город</th>
            <th>Цена</th>
            <th>Места</th>
            <th>Действия</th>
        </tr>

        <c:forEach var="t" items="${tours}">
            <tr>
                <td>${t.tourId}</td>
                <td>${t.title}</td>
                <td>${t.country}</td>
                <td>${t.city}</td>
                <td>${t.price} ₽</td>
                <td>${t.availableSeats}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/managerTours" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${t.tourId}">
                        <button class="btn btn-red">Удалить</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <a class="back-btn" href="${pageContext.request.contextPath}/managerControl">Назад</a>
</div>
</body>
</html>
