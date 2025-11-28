<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Painel Administrativo - Biblioteca</title>
        <meta charset="UTF-8">
        <style>
            body { font-family: Arial, sans-serif; background-color: #f4f6f9; margin: 0; padding: 0; }
            
            /* Barra Superior */
            .navbar { background-color: #343a40; color: white; padding: 15px 20px; display: flex; justify-content: space-between; align-items: center; }
            .navbar h2 { margin: 0; font-size: 1.2rem; }
            .logout-btn { color: #ff6b6b; text-decoration: none; font-weight: bold; font-size: 0.9rem; border: 1px solid #ff6b6b; padding: 5px 10px; border-radius: 4px; }
            .logout-btn:hover { background-color: #ff6b6b; color: white; }

            /* Conteúdo Principal */
            .container { max-width: 1000px; margin: 30px auto; padding: 0 20px; }
            .welcome-msg { margin-bottom: 30px; color: #555; }

            /* Grid de Cartões */
            .dashboard-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 20px; }
            
            .card { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); border-left: 5px solid #007bff; transition: transform 0.2s; }
            .card:hover { transform: translateY(-5px); box-shadow: 0 5px 15px rgba(0,0,0,0.1); }
            
            .card h3 { margin-top: 0; color: #333; }
            .card p { color: #666; font-size: 0.9rem; }
            .card a { display: inline-block; margin-top: 10px; text-decoration: none; color: #007bff; font-weight: bold; }
            
            /* Cores diferentes para seções diferentes */
            .border-green { border-left-color: #28a745; }
            .border-orange { border-left-color: #fd7e14; }
            .border-purple { border-left-color: #6f42c1; }
        </style>
    </head>
    <body>
        <!-- Barra Superior -->
        <div class="navbar">
            <h2>📚 Sistema Biblioteca | Administração</h2>
            <div>
                <span>Olá, <strong>${sessionScope.usuarioLogado.nome}</strong></span>
                &nbsp;|&nbsp;
                <a href="login" class="logout-btn">Sair</a> <!-- O Logout é um GET no LoginServlet -->
            </div>
        </div>

        <div class="container">
            <div class="welcome-msg">
                <h1>Painel de Controle</h1>
                <p>Gerencie o acervo, usuários e empréstimos do sistema.</p>
            </div>

            <div class="dashboard-grid">
                
                <!-- Seção de Livros -->
                <div class="card">
                    <h3>📖 Acervo de Livros</h3>
                    <p>Cadastre novos títulos ou visualize o catálogo.</p>
                    <a href="listar-livros">Ver Todos os Livros &rarr;</a><br>
                    <a href="cadastro-livro.html">+ Novo Livro</a><br>
                    <a href="listar-avaliacoes" style="color: #6f42c1;">★ Ver Avaliações</a>
                </div>

                <!-- Seção de Estoque -->
                <div class="card border-green">
                    <h3>📦 Estoque</h3>
                    <p>Reponha unidades de livros esgotados.</p>
                    <a href="selecionar-livro-estoque">Gerenciar Estoque &rarr;</a>
                </div>

                <!-- Seção de Usuários -->
                <div class="card border-orange">
                    <h3>👥 Usuários</h3>
                    <p>Visualize os leitores cadastrados no sistema.</p>
                    <a href="listar-usuarios">Ver Usuários &rarr;</a>
                </div>

                <!-- Seção de Empréstimos -->
                <div class="card border-purple">
                    <h3>🔄 Empréstimos</h3>
                    <p>Controle quem pegou o quê e registre devoluções.</p>
                    <a href="listar-emprestimos">Histórico Geral &rarr;</a><br>
                    <a href="novo-emprestimo">Novo Empréstimo (Admin)</a>
                </div>

            </div>
        </div>
    </body>
</html>