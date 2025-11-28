<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Login - Biblioteca</title>
        <meta charset="UTF-8">
        <style>
            body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f0f2f5; margin: 0; }
            .login-card { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); width: 300px; text-align: center; }
            input { width: 100%; padding: 10px; margin: 10px 0; box-sizing: border-box; border: 1px solid #ddd; border-radius: 4px; }
            button { width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
            button:hover { background-color: #0056b3; }
            .erro { color: red; font-size: 0.9em; margin-bottom: 10px; }
            a { text-decoration: none; color: #007bff; font-size: 0.9em; }
        </style>
    </head>
    <body>
        <div class="login-card">
            <h2>Bem-vindo!</h2>
            
            <c:if test="${not empty erro}">
                <div class="erro">${erro}</div>
            </c:if>

            <form action="login" method="POST">
                <input type="email" name="email" placeholder="Seu E-mail" required>
                <input type="password" name="senha" placeholder="Sua Senha" required>
                <button type="submit">Entrar</button>
            </form>
            
            <br>
            <a href="cadastro-usuario.html">Cadastre-se no Sistema da Biblioteca</a>
        </div>
    </body>
</html>