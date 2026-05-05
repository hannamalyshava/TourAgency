<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Управление заявками</title>

    <style>
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background-color: #eef2f7;
            margin: 0;
            padding: 40px;
            color: #333;
        }

        .container {
            max-width: 1200px;
            margin: auto;
            background: white;
            padding: 40px;
            border-radius: 14px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.12);
            animation: fadein 0.5s ease;
        }

        h2 {
            margin-top: 0;
            font-size: 28px;
            font-weight: 600;
            color: #007bff;
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0 10px;
            margin-top: 30px;
        }

        th, td {
            padding: 14px 16px;
            text-align: left;
        }

        th {
            background: #f0f4f8;
            color: #333;
            font-weight: 600;
            font-size: 16px;
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
        }

        tr {
            background: white;
            border-radius: 12px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.08);
            transition: transform 0.2s, box-shadow 0.2s;
        }

        tr:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 18px rgba(0,0,0,0.15);
        }

        td {
            font-size: 15px;
            color: #555;
        }

        .btn {
            padding: 8px 16px;
            border: none;
            border-radius: 10px;
            color: white;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: 0.25s;
            text-decoration: none;
        }

        .btn:hover {
            opacity: 0.9;
        }

        .btn-status {
            background: linear-gradient(135deg, #007bff, #3399ff);
        }

        .btn-delete {
            background: linear-gradient(135deg, #d9534f, #e74c3c);
        }

        select {
            padding: 6px 10px;
            border-radius: 8px;
            border: 1px solid #ccc;
            font-size: 14px;
        }

        .status-badge {
            padding: 6px 12px;
            border-radius: 12px;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 13px;
            color: white;
        }

        .PENDING { background: #ffc107; }
        .CONFIRMED { background: #28a745; }
        .CANCELLED { background: #dc3545; }

        @keyframes fadein {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .back-btn {
            display: inline-block;
            margin-top: 30px;
            padding: 12px 25px;
            background: linear-gradient(135deg, #6c757d, #495057);
            color: white;
            border-radius: 12px;
            font-size: 16px;
            font-weight: 600;
            text-decoration: none;
            transition: 0.25s;
        }

        .back-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 14px rgba(0,0,0,0.2);
        }
    </style>
</head>

<body>
<div class="container">
    <h2>Управление заявками</h2>

    <table>
        <tr>
            <th>ID</th>
            <th>Тур</th>
            <th>Пользователь</th>
            <th>Статус</th>
            <th>Действия</th>
        </tr>

        <c:forEach var="b" items="${bookings}">
            <tr>
                <td>${b.bookingId}</td>
                <td>${b.tourTitle}</td>
                <td>${b.userId}</td>
                <td>
                    <span class="status-badge ${b.status}">${b.status}</span>
                </td>
                <td>
                   <!-- Изменение статуса -->
                   <form action="${pageContext.request.contextPath}/managerBookings" method="post" style="display:inline-block; margin-right:5px;">
                       <input type="hidden" name="action" value="updateStatus">
                       <input type="hidden" name="bookingId" value="${b.bookingId}">
                       <select name="status">
                           <option>PENDING</option>
                           <option>CONFIRMED</option>
                           <option>CANCELLED</option>
                       </select>
                       <button class="btn btn-status">OK</button>
                   </form>

                   <!-- Удаление -->
                   <form action="${pageContext.request.contextPath}/managerBookings" method="post" style="display:inline-block;">
                       <input type="hidden" name="action" value="delete">
                       <input type="hidden" name="id" value="${b.bookingId}">
                       <button class="btn btn-delete">Удалить</button>
                   </form>

                </td>
            </tr>
        </c:forEach>
    </table>

    <a class="back-btn" href="${pageContext.request.contextPath}/managerControl">Назад</a>
</div>
</body>
</html>
