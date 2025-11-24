<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Controle de Empréstimos</title>
        <meta charset="UTF-8">
        <style>
            body { font-family: Arial, sans-serif; margin: 20px; }
            table { width: 100%; border-collapse: collapse; margin-top: 20px; }
            th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
            th { background-color: #f2f2f2; }
            .status-ativo { color: green; font-weight: bold; }
            .back-link { text-decoration: none; color: #007bff; }
        </style>
    </head>
    <body>
        <a href="index.html" class="back-link">← Voltar ao Menu</a>
        <h1>Controle de Empréstimos</h1>
        
        <a href="novo-emprestimo" style="background-color: #007bff; color: white; padding: 10px; text-decoration: none; border-radius: 5px;">Novo Empréstimo</a>
        <br><br>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Usuário</th>
                    <th>Livro</th>
                    <th>Data Saída</th>
                    <th>Devolução Prevista</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="e" items="${listaEmprestimos}">
                    <tr>
                        <td>${e.id}</td>
                        <td>${e.usuario.nome} <small>(${e.usuario.email})</small></td>
                        <td>${e.livro.titulo}</td>
                        
                        <!-- Chamada das propriedades formatadas de data -->
                        <td>${e.dataEmprestimoFormatada}</td>
                        <td>${e.dataDevolucaoPrevistaFormatada}</td>
                        
                        <td class="status-ativo">${e.status}</td>
                    </tr>
                </c:forEach>
                
                <c:if test="${empty listaEmprestimos}">
                    <tr><td colspan="6" style="text-align:center">Nenhum empréstimo ativo.</td></tr>
                </c:if>
            </tbody>
        </table>
    </body>
</html>