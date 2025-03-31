<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Log in with your account</title>
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

    .login-container {
      background-color: #ffffff;
      padding: 30px 40px;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      width: 100%;
      max-width: 400px;
    }

    h2 {
      text-align: center;
      color: #343a40;
      margin-bottom: 20px;
    }

    form {
      display: flex;
      flex-direction: column;
      gap: 15px;
    }

    input {
      padding: 10px;
      font-size: 16px;
      border: 1px solid #ced4da;
      border-radius: 4px;
      width: 100%;
    }

    input:focus {
      outline: none;
      border-color: #007bff;
      box-shadow: 0 0 4px rgba(0, 123, 255, 0.25);
    }

    button {
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

    .register-link {
      text-align: center;
      margin-top: 10px;
    }

    .register-link a {
      color: #007bff;
      text-decoration: none;
    }

    .register-link a:hover {
      text-decoration: underline;
    }
  </style>
</head>

<body>
<sec:authorize access="isAuthenticated()">
  <% response.sendRedirect("/"); %>
</sec:authorize>

<div class="login-container">
  <h2>Вход в систему</h2>
  <form method="POST" action="/login">
    <input name="username" type="text" placeholder="Username" required/>
    <input name="password" type="password" placeholder="Password" required />
    <button type="submit">Log In</button>
  </form>
  <div class="register-link">
    <h4><a href="/register">Зарегистрироваться</a></h4>
  </div>
</div>

</body>
</html>