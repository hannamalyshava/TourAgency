<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Детали заявки</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .container {
            max-width: 800px;
            width: 100%;
            background: white;
            border-radius: 15px;
            padding: 40px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
        }
        h1 {
            color: #667eea;
            margin-bottom: 30px;
            text-align: center;
        }
        .info-section {
            margin-bottom: 30px;
            padding: 20px;
            background: #f8f9fa;
            border-radius: 10px;
        }
        .info-section h2 {
            color: #333;
            margin-bottom: 15px;
            font-size: 20px;
            border-bottom: 2px solid #667eea;
            padding-bottom: 10px;
        }
        .info-row {
            display: flex;
            margin-bottom: 12px;
        }
        .info-label {
            font-weight: 600;
            color: #555;
            min-width: 180px;
        }
        .info-value {
            color: #333;
        }
        .status {
            padding: 8px 15px;
            border-radius: 5px;
            font-size: 14px;
            font-weight: 600;
            display: inline-block;
        }
        .status-pending {
            background: #fff3cd;
            color: #856404;
        }
        .status-confirmed {
            background: #d4edda;
            color: #155724;
        }
        .status-cancelled {
            background: #f8d7da;
            color: #721c24;
        }
        .actions {
            display: flex;
            gap: 10px;
            margin-top: 30px;
        }
        .btn {
            flex: 1;
            padding: 12px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            text-align: center;
            display: inline-block;
        }
        .btn-confirm {
            background: #28a745;
            color: white;
        }
        .btn-confirm:hover {
            background: #218838;
        }
        .btn-cancel {
            background: #dc3545;
            color: white;
        }
        .btn-cancel:hover {
            background: #c82333;
        }
        .btn-back {
            background: #6c757d;
            color: white;
        }
        .btn-back:hover {
            background: #5a6268;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Детали заявки №${booking.bookingId}</h1>
        
        <div class="info-section">
            <h2>Информация о заявке</h2>
            <div class="info-row">
                <span class="info-label">ID заявки:</span>
                <span class="info-value">${booking.bookingId}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Дата создания:</span>
                <span class="info-value"><fmt:formatDate value="${booking.createdAtAsDate}" pattern="dd.MM.yyyy HH:mm"/></span>
            </div>
            <div class="info-row">
                <span class="info-label">Количество человек:</span>
                <span class="info-value">${booking.people}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Общая стоимость:</span>
                <span class="info-value">${booking.totalPrice} ₽</span>
            </div>
            <div class="info-row">
                <span class="info-label">Статус:</span>
                <span class="info-value">
                    <c:choose>
                        <c:when test="${booking.status == 'PENDING'}">
                            <span class="status status-pending">Ожидает подтверждения</span>
                        </c:when>
                        <c:when test="${booking.status == 'CONFIRMED'}">
                            <span class="status status-confirmed">Подтверждена</span>
                        </c:when>
                        <c:when test="${booking.status == 'CANCELLED'}">
                            <span class="status status-cancelled">Отменена</span>
                        </c:when>
                    </c:choose>
                </span>
            </div>
        </div>

        <div class="info-section">
            <h2>Информация о туре</h2>
            <div class="info-row">
                <span class="info-label">Название:</span>
                <span class="info-value">${tour.title}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Направление:</span>
                <span class="info-value">${tour.country}, ${tour.city}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Даты поездки:</span>
                <span class="info-value">
                    <fmt:formatDate value="${tour.startDateAsDate}" pattern="dd.MM.yyyy"/> - 
                    <fmt:formatDate value="${tour.endDateAsDate}" pattern="dd.MM.yyyy"/>
                </span>
            </div>
            <div class="info-row">
                <span class="info-label">Цена за человека:</span>
                <span class="info-value">${tour.price} ₽</span>
            </div>
            <div class="info-row">
                <span class="info-label">Описание:</span>
                <span class="info-value">${tour.description}</span>
            </div>
        </div>

        <div class="actions">
            <a href="${pageContext.request.contextPath}/manager/dashboard" class="btn btn-back">Назад</a>
            <c:if test="${booking.status == 'PENDING'}">
                <a href="${pageContext.request.contextPath}/manager/booking/status?id=${booking.bookingId}&status=CONFIRMED" 
                   class="btn btn-confirm"
                   onclick="return confirm('Подтвердить заявку?')">Подтвердить</a>
                <a href="${pageContext.request.contextPath}/manager/booking/status?id=${booking.bookingId}&status=CANCELLED" 
                   class="btn btn-cancel"
                   onclick="return confirm('Отменить заявку?')">Отменить</a>
            </c:if>
        </div>
    </div>
</body>
</html>
