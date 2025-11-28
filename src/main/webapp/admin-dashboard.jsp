<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Painel Administrativo</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="admin-bg">
    <div class="navbar">
        <h2>📚 Painel de Controle</h2>
        <div>
            <span>Olá, <strong>${sessionScope.usuarioLogado.nome}</strong></span> &nbsp;|&nbsp;
            <a href="login" class="logout-btn">Sair</a>
        </div>
    </div>

    <div class="container">
        <div style="margin-bottom: 30px;">
            <h1>Visão Geral</h1>
            <p>Gerência do Sistema da Biblioteca.</p>
        </div>

        <div class="grid">
            <!-- Livros -->
            <div class="dashboard-card blue">
                <h3>📖 Acervo</h3>
                <p>Gerenciamento do catálogo de livros.</p>
                <div style="margin-top: 15px; display: flex; flex-direction: column; gap: 8px;">
                    <a href="listar-livros">Visualizar Acervo →</a>
                    <a href="cadastro-livro.jsp">+ Cadastrar Novo Livro</a>
                    <a href="listar-avaliacoes" style="color: var(--purple);">★ Ver Avaliações</a>
                </div>
            </div>

            <!-- Estoque -->
            <div class="dashboard-card green">
                <h3>📦 Estoque</h3>
                <p>Controle de estoque de exemplares.</p>
                <div style="margin-top: 15px;">
                    <a href="selecionar-livro-estoque" class="text-green">Gerenciar Entradas →</a>
                </div>
            </div>

            <!-- Usuários -->
            <div class="dashboard-card orange">
                <h3>👥 Usuários</h3>
                <p>Leitores cadastrados no sistema.</p>
                <div style="margin-top: 15px;">
                    <a href="listar-usuarios" style="color: #fd7e14;">Listar Usuários →</a>
                </div>
            </div>

            <!-- Empréstimos -->
            <div class="dashboard-card purple">
                <h3>🔄 Empréstimos</h3>
                <p>Histórico dos empréstimos, registros de retiradas e devoluções.</p>
                <div style="margin-top: 15px; display: flex; flex-direction: column; gap: 8px;">
                    <a href="listar-emprestimos" style="color: var(--purple);">Histórico Geral →</a>
                    <a href="novo-emprestimo" style="color: var(--purple);">+ Novo Empréstimo</a>
                    <a href="admin-devolucoes" style="color: var(--purple);">⬇ Registrar Devolução</a>
                </div>
            </div>
            
            <!-- Penalidades -->
            <div class="dashboard-card red">
                <h3>🚫 Penalidades</h3>
                <p>Gestão de multas e bloqueios.</p>
                <div style="margin-top: 15px;">
                    <a href="admin-penalidades" class="text-red">Ver Bloqueios Ativos →</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>