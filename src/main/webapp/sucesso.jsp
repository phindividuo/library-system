<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
    <title>Operação Realizada</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="auth-page">
    <div class="card auth-card">
        <div class="card-icon" style="color: var(--success);">✅</div>
        <h1 class="text-green">Sucesso!</h1>
        
        <!-- O request.setCharacterEncoding no início para tentar resolver os erros de encoding -->
        <h3 style="font-weight: normal; color: #555;">${param.mensagem}</h3>
        
        <br>
        <a href="dashboard" class="btn">Voltar ao Início</a>
    </div>
</body>
</html>