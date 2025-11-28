<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reposição de Estoque</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container container-white">
        <a href="dashboard" class="back-link">← Voltar ao Menu</a>
        <h1 class="text-green">Selecionar Livro para Reposição</h1>

        <table>
            <thead>
                <tr>
                    <th class="th-green">ID</th>
                    <th class="th-green">Título</th>
                    <th class="th-green">Estoque Atual</th>
                    <th class="th-green">Ação</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="l" items="${listaLivros}">
                    <tr>
                        <td>${l.id}</td>
                        <td>${l.titulo}</td>
                        <td style="font-weight: bold;">${l.quantidade}</td>
                        <td>
                            <a href="adicionar-estoque.jsp?id=${l.id}&titulo=${l.titulo}&isbn=${l.isbn}&qtd=${l.quantidade}" class="btn btn-success btn-sm">+ Adicionar</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>