<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Главная</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f5f7;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background-color: #ffffff;
            padding: 40px 50px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 400px;
        }

        h3 {
            margin-bottom: 20px;
            color: #343a40;
        }

        h4 a {
            display: block;
            margin: 10px 0;
            font-size: 16px;
            color: #007bff;
            text-decoration: none;
        }

        h4 a:hover {
            text-decoration: underline;
        }

        .btn {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            font-size: 16px;
            color: #fff;
            background-color: #007bff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
        }

        .btn:hover {
            background-color: #0056b3;
        }

        .btn-logout {
            background-color: #dc3545;
        }

        .btn-logout:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
<div class="container">
    <h3>Добро пожаловать на сайт выпускников НИУ ВШЭ!</h3>

    <sec:authorize access="!isAuthenticated()">
        <h4><a class="btn" href="/login">Войти</a></h4>
        <h4><a class="btn" href="/register">Зарегистрироваться</a></h4>
    </sec:authorize>

    <sec:authorize access="isAuthenticated()">
        <h4><a class="btn btn-logout" href="/logout">Выйти</a></h4>

        <h4><a href="/api/specializations">Список специализаций</a></h4>
        <h4><a href="/api/alumni">Список выпускников</a></h4>
        <h4><a href="/api/profile">Личный профиль</a></h4>
    </sec:authorize>
</div>
</body>
</html>