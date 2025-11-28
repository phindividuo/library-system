<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Sistema Biblioteca</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="auth-page">
    <div class="card auth-card">
        <div class="card-icon">📚</div>
        <h2>Bem-vindo</h2>
        
        <c:if test="${not empty erro}">
            <div class="erro">${erro}</div>
        </c:if>

        <form action="login" method="POST">
            <input type="email" name="email" placeholder="E-mail" required autofocus>
            <input type="password" name="senha" placeholder="Senha" required>
            <button type="submit" class="full-width">Entrar</button>
        </form>
        
        <br>
        <a href="cadastro-usuario.jsp" class="back-link">Não tem conta? Cadastre-se aqui</a>
    </div>
</body>
</html>