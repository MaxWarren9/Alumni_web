<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Log in with your account</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background-color: #ffffff;
            padding: 20px 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 1000px;
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #343a40;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        table thead th {
            background-color: #007bff;
            color: #fff;
            padding: 10px;
            text-align: left;
        }

        table tbody td {
            padding: 10px;
            border-bottom: 1px solid #dee2e6;
        }

        table tbody tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        button {
            padding: 6px 12px;
            font-size: 14px;
            color: #fff;
            background-color: #dc3545;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #c82333;
        }

        .link {
            display: block;
            text-align: center;
            margin-top: 15px;
            color: #007bff;
            text-decoration: none;
        }

        .link:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>
<div class="container">
    <h2>Список пользователей</h2>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Имя пользователя</th>
            <th>Пароль</th>
            <th>Роли</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${allUsers}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>
                    <c:forEach items="${user.roles}" var="role">
                        ${role.name};
                    </c:forEach>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin" method="post" style="display:inline;">
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <input type="hidden" name="action" value="delete"/>
                        <button type="submit">Удалить</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="/" class="link">Главная</a>
</div>
</body>
</html>