<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gerenciar Estoque</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="auth-page">
    <div class="card auth-card">
        <h1 style="color: var(--success);">Reposição</h1>
        
        <div class="book-info">
            <h2 style="color: var(--primary); margin:0;">${not empty livro ? livro.titulo : param.titulo}</h2>
            <p>ISBN: ${not empty livro ? livro.isbn : param.isbn}</p>
            <p>Atual: <strong>${not empty livro ? livro.quantidade : param.qtd}</strong> unidades</p>
        </div>

        <form action="adicionar-estoque" method="POST">
            <input type="hidden" name="id" value="${not empty livro ? livro.id : param.id}">
            
            <label>Quantidade a Adicionar (+):</label>
            <input type="number" name="novaQuantidade" min="1" required value="1" style="text-align: center; font-size: 1.2rem;">
            
            <button type="submit" class="btn btn-success full-width">Confirmar Entrada</button>
        </form>
        
        <br>
        <a href="listar-livros" class="back-link">Cancelar</a>
    </div>
</body>
</html>