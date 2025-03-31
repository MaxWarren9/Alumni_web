<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Профиль</title>
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
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            width: 450px;
            text-align: center;
        }

        h2 {
            margin-bottom: 20px;
            color: #343a40;
        }

        .profile-info {
            text-align: left;
            margin-bottom: 20px;
        }

        .profile-info p {
            margin: 10px 0;
            font-size: 16px;
        }

        .edit-btn, .save-btn, .logout-btn {
            display: inline-block;
            margin-top: 10px;
            padding: 10px 20px;
            font-size: 16px;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
        }

        .edit-btn {
            background-color: #007bff;
        }

        .edit-btn:hover {
            background-color: #0056b3;
        }

        .save-btn {
            background-color: #28a745;
        }

        .save-btn:hover {
            background-color: #218838;
        }

        .logout-btn {
            background-color: #dc3545;
        }

        .logout-btn:hover {
            background-color: #c82333;
        }

        .form-group {
            display: none;
            text-align: left;
            margin-top: 15px;
        }

        .form-group label {
            font-weight: bold;
            display: block;
            margin-top: 10px;
        }

        .form-group input {
            width: 100%;
            padding: 8px;
            border: 1px solid #ced4da;
            border-radius: 5px;
            font-size: 14px;
            margin-top: 5px;
        }

    </style>
</head>
<body>
<div class="container">
    <h2>Личный профиль</h2>

    <div class="profile-info">
        <p><strong>Имя:</strong> <span id="firstName">Иван</span></p>
        <p><strong>Фамилия:</strong> <span id="lastName">Иванов</span></p>
        <p><strong>Дата рождения:</strong> <span id="dateOfBirth">01.01.2000</span></p>
        <p><strong>Год выпуска:</strong> <span id="graduationYear">2020</span></p>
        <p><strong>Email:</strong> <span id="email">ivan.ivanov@example.com</span></p>
    </div>

    <button class="edit-btn" onclick="showEditForm()">Редактировать</button>

    <form id="editForm" class="form-group">
        <label for="firstNameInput">Имя:</label>
        <input type="text" id="firstNameInput" value="Иван">

        <label for="lastNameInput">Фамилия:</label>
        <input type="text" id="lastNameInput" value="Иванов">

        <label for="dateOfBirthInput">Дата рождения:</label>
        <input type="date" id="dateOfBirthInput" value="2000-01-01">

        <label for="graduationYearInput">Год выпуска:</label>
        <input type="number" id="graduationYearInput" value="2020">

        <label for="emailInput">Email:</label>
        <input type="email" id="emailInput" value="ivan.ivanov@example.com">

        <button type="button" class="save-btn" onclick="saveProfile()">Сохранить</button>
    </form>

    <a href="/logout" class="logout-btn">Выйти</a>
</div>

<script>
    function showEditForm() {
        document.getElementById('editForm').style.display = 'block';
    }

    function saveProfile() {
        // Обновление отображаемой информации
        document.getElementById('firstName').textContent = document.getElementById('firstNameInput').value;
        document.getElementById('lastName').textContent = document.getElementById('lastNameInput').value;
        document.getElementById('dateOfBirth').textContent = document.getElementById('dateOfBirthInput').value;
        document.getElementById('graduationYear').textContent = document.getElementById('graduationYearInput').value;
        document.getElementById('email').textContent = document.getElementById('emailInput').value;

        document.getElementById('editForm').style.display = 'none';
    }
</script>
</body>
</html>