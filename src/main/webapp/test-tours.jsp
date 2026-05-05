<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Тест загрузки туров</title>
</head>
<body>
    <h1>Тест загрузки туров</h1>
    
    <h2>Отладочная информация:</h2>
    <p><strong>Атрибут 'tours' существует:</strong> ${not empty tours ? 'Да' : 'Нет'}</p>
    <p><strong>Тип атрибута 'tours':</strong> ${tours.getClass().name}</p>
    <p><strong>Размер списка:</strong> ${tours.size()}</p>
    <p><strong>Атрибут 'error':</strong> ${error}</p>
    
    <hr>
    
    <h2>Список туров:</h2>
    <c:choose>
        <c:when test="${empty tours}">
            <p style="color: red;">Список туров пуст!</p>
        </c:when>
        <c:otherwise>
            <ul>
                <c:forEach var="tour" items="${tours}">
                    <li>
                        <strong>${tour.title}</strong> - ${tour.country} - ${tour.price} BYN
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>
    
    <hr>
    <p><a href="${pageContext.request.contextPath}/">Вернуться на главную</a></p>
</body>
</html>
