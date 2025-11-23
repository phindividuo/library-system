<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Importante: Biblioteca JSTL para fazer o loop -->
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Acervo de Livros</title>
        <meta charset="UTF-8">
        <style>
            body { font-family: Arial, sans-serif; margin: 20px; }
            table { width: 100%; border-collapse: collapse; margin-top: 20px; }
            th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
            th { background-color: #f2f2f2; }
            tr:nth-child(even) { background-color: #f9f9f9; }
            .back-link { text-decoration: none; color: #007bff; font-weight: bold; }
            .badge { padding: 4px 8px; border-radius: 4px; font-size: 0.9em; color: white; }
            .bg-green { background-color: #28a745; } /* Notas altas */
            .bg-yellow { background-color: #ffc107; color: black; } /* Notas médias */
            .bg-red { background-color: #dc3545; } /* Notas baixas */
        </style>
    </head>
    <body>
        <a href="index.html" class="back-link">← Voltar ao Menu</a>
        <h1>Acervo da Biblioteca</h1>
        
        <a href="cadastro-livro.html" style="background-color: #28a745; color: white; padding: 10px; text-decoration: none; border-radius: 5px;">+ Novo Livro</a>
        <br><br>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>ISBN</th>
                    <th>Título</th>
                    <th>Autor</th>
                    <th>Gênero</th>
                    <th>Editora</th>
                    <th>Págs</th>
                    <th>Ano</th>
                    <th>Nota</th>
                    <th>Qtd</th>
                </tr>
            </thead>
            <tbody>
                <!-- Loop percorrendo a lista enviada pelo Servlet -->
                <c:forEach var="l" items="${listaLivros}">
                    <tr>
                        <td>${l.id}</td>
                        <td>${l.isbn}</td>
                        <td><strong>${l.titulo}</strong></td>
                        <td>${l.autor}</td>
                        <td>${l.genero}</td>
                        <td>${l.editora}</td>
                        <td>${l.numPaginas}</td>
                        <td>${l.anoPublicacao}</td>
                        
                        <!-- Lógica visual para a nota -->
                        <td>
                            <span class="badge ${l.classificacao >= 8 ? 'bg-green' : (l.classificacao >= 5 ? 'bg-yellow' : 'bg-red')}">
                                ${l.classificacao}/10
                            </span>
                        </td>
                        
                        <td>${l.quantidade}</td>
                    </tr>
                </c:forEach>
                
                <!-- Caso a lista esteja vazia -->
                <c:if test="${empty listaLivros}">
                    <tr>
                        <td colspan="10" style="text-align:center; color: gray;">
                            Nenhum livro cadastrado ainda.
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </body>
</html>