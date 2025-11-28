<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Acervo de Livros</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container container-white">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <a href="dashboard" class="back-link">← Voltar ao Painel</a>
        </div>
        
        <h1>Acervo da Biblioteca</h1>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>ISBN</th>
                    <th>Título</th>
                    <th>Autor</th>
                    <th>Gênero</th>
                    <th>Nota</th>
                    <th style="text-align: center;">Estoque</th>
                    <th>Ações</th> <!-- Coluna de botões -->
                </tr>
            </thead>
            <tbody>
                <c:forEach var="l" items="${listaLivros}">
                    <tr>
                        <td>${l.id}</td>
                        <td><small>${l.isbn}</small></td>
                        <td><strong>${l.titulo}</strong></td>
                        <td>${l.autor}</td>
                        <td>${l.genero}</td>
                        <td>
                             <c:choose>
                                <c:when test="${l.notaMedia > 0}">
                                    <span class="badge ${l.notaMedia >= 8 ? 'bg-success' : (l.notaMedia >= 5 ? 'bg-warning' : 'bg-danger')}">
                                        ${l.notaMediaFormatada}
                                    </span>
                                </c:when>
                                <c:otherwise><span style="color:#999; font-size:0.8rem;">-</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td style="font-weight: bold; text-align: center; color: ${l.quantidade <= 0 ? 'red' : 'inherit'}">
                            ${l.quantidade}
                        </td>
                        <td>
                            <!-- Apenas Emprestar ou Esgotado -->
                            <!-- Removido a opção de Adicionar Estoque -->
                            <c:choose>
                                <c:when test="${l.quantidade > 0}">
                                    <a href="novo-emprestimo?livroId=${l.id}" class="btn btn-sm">Emprestar</a>
                                </c:when>
                                <c:otherwise>
                                    <span class="btn btn-disabled btn-sm">Esgotado</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty listaLivros}">
                    <tr><td colspan="8" style="text-align:center; padding: 20px;">Nenhum livro cadastrado.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>