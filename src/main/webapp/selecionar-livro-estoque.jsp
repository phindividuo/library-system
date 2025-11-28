<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Selecionar Livro - Estoque</title>
        <meta charset="UTF-8">
        <style>
            body { font-family: Arial, sans-serif; margin: 20px; }
            table { width: 100%; border-collapse: collapse; margin-top: 20px; }
            th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
            th { background-color: #f2f2f2; }
            a { text-decoration: none; color: blue; }
        </style>
    </head>
    <body>
        <a href="dashboard">← Voltar ao Menu</a>
        <h1>Reposição de Estoque</h1>
        <p>Escolha um livro para adicionar unidades:</p>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Título</th>
                    <th>Estoque Atual</th>
                    <th>Ação</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="l" items="${listaLivros}">
                    <tr>
                        <td>${l.id}</td>
                        <td>${l.titulo}</td>
                        <td>${l.quantidade}</td>
                        <td>
                            <!-- Link simples que leva para a tela de adicionar -->
                            <a href="adicionar-estoque?id=${l.id}">+ Adicionar</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>