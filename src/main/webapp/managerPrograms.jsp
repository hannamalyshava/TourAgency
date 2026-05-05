<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Управление программами</title>

    <style>
        body {
            background: #eef2f7;
            font-family: Segoe UI, sans-serif;
            padding: 30px;
        }

        .container {
            background: #fff;
            padding: 30px;
            border-radius: 14px;
            max-width: 1000px;
            margin: auto;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
        }

        h2 {
            margin-bottom: 20px;
            color: #2b3e50;
        }

        h3 {
            margin-top: 30px;
            color: #324a5e;
        }

        form input, form textarea {
            width: 100%;
            padding: 10px;
            margin: 6px 0 15px 0;
            border: 1px solid #ccc;
            border-radius: 8px;
            transition: .2s;
            font-size: 15px;
        }

        form input:focus, form textarea:focus {
            border-color: #4a76a8;
            box-shadow: 0 0 4px rgba(74,118,168,0.4);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            border-radius: 10px;
            overflow: hidden;
        }

        th {
            background: #4a76a8;
            color: white;
            padding: 12px;
            text-align: left;
        }

        td {
            padding: 12px;
            background: #fff;
            border-bottom: 1px solid #e6e6e6;
        }

        tr:nth-child(even) td {
            background: #f7f9fc;
        }

        tr:hover td {
            background: #eef5ff;
        }

        .btn {
            padding: 8px 16px;
            background: #4a76a8;
            color: white;
            border-radius: 8px;
            text-decoration: none;
            border: none;
            cursor: pointer;
            font-size: 14px;
            transition: 0.2s;
        }

        .btn:hover {
            background: #3a638c;
        }

        .btn-red {
            background: #d9534f;
        }

        .btn-red:hover {
            background: #b84642;
        }

        .actions {
            display: flex;
            gap: 10px;
        }
    </style>
</head>

<body>
<div class="container">
    <h2>Управление программами</h2>

    <h3>Добавить программу</h3>
    <form method="post" action="${pageContext.request.contextPath}/managerPrograms">
        <input type="hidden" name="action" value="add">

        <label>Название:</label>
        <input name="name" required>

        <label>Описание:</label>
        <textarea name="description" rows="3"></textarea>

        <label>Длительность:</label>
        <input name="duration" type="number" min="1" required>


        <button class="btn">Добавить</button>
    </form>

    <hr>

    <h3>Список программ</h3>

    <table>
        <tr>
            <th>ID</th>
            <th>Название</th>
            <th>Длительность</th>
            <th style="width: 120px;">Действия</th>
        </tr>

        <c:forEach var="p" items="${programs}">
            <tr>
                <td>${p.programId}</td>
                <td>${p.name}</td>
                <td>${p.duration}</td>

                <td>
                    <form action="${pageContext.request.contextPath}/managerPrograms" method="post">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${p.programId}">
                        <button class="btn btn-red">Удалить</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <br>
    <a class="btn" href="${pageContext.request.contextPath}/managerControl">Назад</a>
</div>
</body>
</html>
