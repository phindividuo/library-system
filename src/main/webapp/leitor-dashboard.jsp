<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Minha Biblioteca</title>
        <meta charset="UTF-8">
        <style>
            body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; margin: 0; }
            
            .header { background-color: #007bff; color: white; padding: 20px; text-align: center; }
            .header h1 { margin: 0; font-weight: 300; }
            .user-info { margin-top: 10px; font-size: 0.9rem; }
            .logout { color: rgba(255,255,255,0.8); text-decoration: underline; }

            .main-content { max-width: 800px; margin: 40px auto; padding: 0 20px; }
            
            .action-btn {
                display: block;
                background: white;
                padding: 20px;
                margin-bottom: 15px;
                border-radius: 10px;
                text-decoration: none;
                color: #333;
                box-shadow: 0 2px 4px rgba(0,0,0,0.05);
                transition: 0.2s;
                border-left: 8px solid #ddd;
            }
            .action-btn:hover { transform: scale(1.02); box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
            
            .btn-blue { border-left-color: #007bff; }
            .btn-green { border-left-color: #28a745; }
            .btn-orange { border-left-color: #fd7e14; }

            .action-btn h3 { margin: 0 0 5px 0; }
            .action-btn span { color: #666; font-size: 0.9rem; }
        </style>
    </head>
    <body>
        <div class="header">
            <h1>Minha Biblioteca</h1>
            <div class="user-info">
                Bem-vindo, <b>${sessionScope.usuarioLogado.nome}</b>!
                <br>
                <a href="login" class="logout">Sair do sistema</a>
            </div>
        </div>

        <div class="main-content">
            
            <!-- Botão para ver livros disponíveis -->
            <a href="listar-livros" class="action-btn btn-blue">
                <h3>📖 Consultar Acervo</h3>
                <span>Veja os livros disponíveis, notas e detalhes.</span>
            </a>

            <a href="meus-emprestimos" class="action-btn btn-green">
                <h3>📚 Meus Livros Emprestados</h3>
                <span>Veja seus prazos, renove empréstimos e avalie obras.</span>
            </a>

            <!-- Novo Botão de Avaliações -->
            <a href="listar-avaliacoes" class="action-btn btn-orange">
                <h3>⭐ Ver Avaliações da Comunidade</h3>
                <span>Veja o que outros leitores estão achando dos livros.</span>
            </a>

        </div>
    </body>
</html>