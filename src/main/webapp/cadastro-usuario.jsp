<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Novo Usuário</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="auth-page">
    <div class="card auth-card">
        <h2>Crie sua Conta</h2>
        <form action="cadastrar-usuario" method="POST">
            <label>Nome Completo:</label>
            <input type="text" name="nome" required placeholder="Ex: João Silva">
            
            <label>E-mail:</label>
            <input type="email" name="email" required placeholder="seu@email.com">
            
            <label>Senha:</label>
            <input type="password" name="senha" required placeholder="******">
            
            <button type="submit" class="btn-success full-width">Cadastrar</button>
        </form>
        <br>
        <a href="login.jsp" class="back-link">← Voltar para Login</a>
    </div>
</body>
</html>