<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Личный кабинет</title>

    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: "Segoe UI", sans-serif;
            background: #eef2f7;
        }

        .container {
            max-width: 1100px;
            margin: 40px auto;
            padding: 25px;
            background: white;
            border-radius: 14px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.15);
        }

        h1 {
            margin-top: 0;
            text-align: center;
            color: #333;
        }

        .flex {
            display: flex;
            gap: 30px;
        }

        .left, .right {
            flex: 1;
        }

        .card {
            background: #f8f9fc;
            padding: 20px;
            border-radius: 12px;
            margin-bottom: 20px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .card h2 {
            margin-top: 0;
            color: #444;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 10px;
            overflow: hidden;
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        th {
            background: #f0f0f0;
        }

        tr:hover {
            background: #fafafa;
        }

        .status-pending {
            color: #ff9800;
            font-weight: 600;
        }

        .status-confirmed {
            color: #4caf50;
            font-weight: 600;
        }

        .status-cancelled {
            color: #f44336;
            font-weight: 600;
        }

        .logout-btn {
            display: inline-block;
            margin-top: 15px;
            padding: 10px 20px;
            background: #d9534f;
            color: white;
            border-radius: 8px;
            text-decoration: none;
        }

        .logout-btn:hover {
            background: #b84642;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Личный кабинет</h1>

    <div class="flex">

        <!-- ЛЕВАЯ КОЛОНКА - ДАННЫЕ ПОЛЬЗОВАТЕЛЯ -->
        <div class="left">
            <div class="card">
                <h2>Мои данные</h2>

                <p><strong>Имя:</strong> ${sessionScope.user.firstName}</p>
                <p><strong>Фамилия:</strong> ${sessionScope.user.lastName}</p>
                <p><strong>Телефон:</strong> ${sessionScope.user.phone}</p>
                <p><strong>Email:</strong> ${sessionScope.user.email}</p>

                <c:if test="${sessionScope.user.role == 'MANAGER'}">
                    <a href="${pageContext.request.contextPath}/managerControl"
                       class="logout-btn"
                       style="background:#4a76a8; margin-top:20px;">
                        Управление
                    </a>
                </c:if>


                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Выйти</a>
            </div>
        </div>

        <!-- ПРАВАЯ КОЛОНКА - МОИ ЗАЯВКИ -->
        <div class="right">
            <div class="card">
                <h2>Мои заявки</h2>

                <c:if test="${empty bookings}">
                    <p>У вас пока нет заявок.</p>
                </c:if>

                <c:if test="${not empty bookings}">
                    <table>
                        <tr>
                            <th>Название тура</th>
                            <th>Дата отправки</th>
                            <th>Статус</th>
                        </tr>

                        <c:forEach var="b" items="${bookings}">
                            <tr>
                                <td>${b.tourTitle}</td>
                                <td>
                                    <fmt:parseDate value="${b.createdAt}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both" />
                                    <fmt:formatDate value="${parsedDate}" pattern="dd.MM.yyyy HH:mm" />
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${b.status == 'PENDING'}">
                                            <span class="status-pending">Ожидает</span>
                                        </c:when>
                                        <c:when test="${b.status == 'CONFIRMED'}">
                                            <span class="status-confirmed">Подтверждена</span>
                                        </c:when>
                                        <c:when test="${b.status == 'CANCELLED'}">
                                            <span class="status-cancelled">Отменена</span>
                                        </c:when>
                                        <c:otherwise>${b.status}</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>

                </c:if>

            </div>
        </div>

    </div>
</div>

</body>
</html>
