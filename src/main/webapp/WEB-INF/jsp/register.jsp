<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Регистрация</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f8f9fa;
      margin: 0;
      padding: 0;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }

    .container {
      background-color: #ffffff;
      padding: 20px 30px;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      width: 300px;
    }

    h2 {
      text-align: center;
      margin-bottom: 20px;
      color: #343a40;
    }

    .form-group {
      margin-bottom: 15px;
    }

    label {
      display: block;
      margin-bottom: 5px;
      font-weight: bold;
      color: #495057;
    }

    input {
      width: 100%;
      padding: 8px 10px;
      font-size: 14px;
      border: 1px solid #ced4da;
      border-radius: 4px;
    }

    .error {
      color: #dc3545;
      font-size: 12px;
      margin-top: 5px;
    }

    button {
      width: 100%;
      padding: 10px;
      font-size: 16px;
      color: #fff;
      background-color: #007bff;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }

    button:hover {
      background-color: #0056b3;
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
  <form:form method="POST" modelAttribute="alumni">
    <h2>Регистрация</h2>

    <!-- Username -->
    <div class="form-group">
      <label for="username">Имя пользователя</label>
      <form:input type="text" path="username" id="username" name="username" placeholder="Введите никнейм пользователя" />
      <form:errors path="username" cssClass="error" />
    </div>

    <!-- First Name -->
    <div class="form-group">
      <label for="firstName">Имя</label>
      <form:input type="text" path="firstName" id="firstName" name="firstName" placeholder="Введите имя" />
      <form:errors path="firstName" cssClass="error" />
    </div>

    <!-- Last Name -->
    <div class="form-group">
      <label for="lastName">Фамилия</label>
      <form:input type="text" path="lastName" id="lastName" name="lastName" placeholder="Введите фамилию" />
      <form:errors path="lastName" cssClass="error" />
    </div>

    <!-- Graduation Year -->
    <div class="form-group">
      <label for="graduationYear">Год выпуска</label>
      <form:input type="number" path="graduationYear" id="graduationYear" name="graduationYear" placeholder="Введите год выпуска" />
      <form:errors path="graduationYear" cssClass="error" />
    </div>

    <!-- Email -->
    <div class="form-group">
      <label for="email">Email</label>
      <form:input type="text" path="email" id="email" name="email" placeholder="Введите email" />
      <form:errors path="email" cssClass="error" />
    </div>

    <!-- Password -->
    <div class="form-group">
      <label for="password">Пароль</label>
      <form:input type="password" path="password" id="password" name="password" placeholder="Введите пароль" />
      <form:errors path="password" cssClass="error" />
    </div>

    <!-- Confirm Password -->
    <div class="form-group">
      <label for="passwordConfirm">Подтвердите пароль</label>
      <form:input type="password" path="passwordConfirm" id="passwordConfirm" name="passwordConfirm" placeholder="Подтвердите пароль" />
      <form:errors path="passwordConfirm" cssClass="error" />
    </div>

    <!-- Submit Button -->
    <button type="submit">Зарегистрироваться</button>
  </form:form>

  <!-- Back to Home -->
  <a href="/" class="link">Главная</a>
</div>
</body>
</html>