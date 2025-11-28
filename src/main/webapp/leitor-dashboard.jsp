<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Minha Biblioteca</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="leitor-bg">
    <div class="navbar navbar-leitor">
        <h2>Minha Biblioteca</h2>
        <div style="text-align: right;">
            <span>Olá, <strong>${sessionScope.usuarioLogado.nome}</strong></span><br>
            <a href="login" class="logout-link">Sair</a>
        </div>
    </div>

    <div class="container">
        <!-- Consultar Acervo -->
        <a href="listar-livros" class="action-card blue">
            <div>
                <h3>📖 Consultar Acervo</h3>
                <p>Pesquise livros, verifique disponibilidade e faça empréstimos.</p>
            </div>
            <div class="card-icon">→</div>
        </a>

        <!-- Meus Empréstimos -->
        <a href="meus-emprestimos" class="action-card green">
            <div>
                <h3>📚 Meus Livros</h3>
                <p>Veja seus empréstimos ativos, prazos e renove livros.</p>
            </div>
            <div class="card-icon">→</div>
        </a>
        
        <!-- Avaliações -->
        <a href="listar-avaliacoes" class="action-card orange">
            <div>
                <h3>⭐ Avaliações da Comunidade</h3>
                <p>Descubra o que outros leitores estão achando dos livros.</p>
            </div>
            <div class="card-icon">→</div>
        </a>

        <!-- Penalidades -->
        <a href="minhas-penalidades" class="action-card red">
            <div>
                <h3>🚫 Minhas Penalidades</h3>
                <p>Verifique se você possui bloqueios ou pendências.</p>
            </div>
            <div class="card-icon">→</div>
        </a>
    </div>
</body>
</html>