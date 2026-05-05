<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Ошибка</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f8d7da;
            padding: 40px;
        }
        .box {
            background: white;
            padding: 25px;
            border-radius: 10px;
            max-width: 600px;
            margin: auto;
            box-shadow: 0 3px 10px rgba(0,0,0,0.15);
            border-left: 6px solid #dc3545;
        }
        h2 { color: #dc3545; }
        p { font-size: 18px; }
    </style>
</head>
<body>

<div class="box">
    <h2>Произошла ошибка</h2>

    <p>
        <c:out value="${error}" default="Неизвестная ошибка"/>
    </p>

    <a href="${pageContext.request.contextPath}/">Вернуться на главную</a>
</div>

</body>
</html>
