<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Acervo de Livros</title>
        <meta charset="UTF-8">
        <style>
            body { font-family: Arial, sans-serif; margin: 20px; background-color: #f8f9fa; }
            h1 { color: #333; }
            table { width: 100%; border-collapse: collapse; margin-top: 20px; background: white; box-shadow: 0 2px 5px rgba(0,0,0,0.05); }
            th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
            th { background-color: #007bff; color: white; text-transform: uppercase; font-size: 0.9em; }
            tr:nth-child(even) { background-color: #f9f9f9; }
            
            .back-link { text-decoration: none; color: #007bff; font-weight: bold; }
            .badge { padding: 5px 10px; border-radius: 15px; font-size: 0.85em; color: white; font-weight: bold; display: inline-block; min-width: 30px; text-align: center; }
            .bg-green { background-color: #28a745; } 
            .bg-yellow { background-color: #ffc107; color: #333; }
            .bg-red { background-color: #dc3545; }
            .bg-gray { background-color: #6c757d; } /* Para sem nota */
            
            .btn-action { text-decoration: none; padding: 6px 12px; border-radius: 4px; color: white; font-weight: bold; font-size: 0.9em; display: inline-block; }
            .btn-emprestar { background-color: #007bff; }
            .btn-emprestar:hover { background-color: #0056b3; }
            .btn-estoque { background-color: #17a2b8; }
            .btn-disabled { background-color: #e9ecef; color: #aaa; cursor: not-allowed; border: 1px solid #ddd; }
        </style>
    </head>
    <body>
        <a href="dashboard" class="back-link">← Voltar ao Menu</a>
        <h1>Acervo da Biblioteca</h1>
        
        <!-- Botão Novo Livro (Apenas Admin) -->
        <c:if test="${sessionScope.usuarioLogado.perfil == 'ADMIN'}">
            <a href="cadastro-livro.html" style="background-color: #28a745; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; font-weight: bold;">+ Novo Livro</a>
            <br><br>
        </c:if>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>ISBN</th>
                    <th>Título</th>
                    <th>Autor</th>
                    <th>Gênero</th>
                    <th>Editora</th>
                    <th>Nota Média</th> <!-- Título atualizado -->
                    <th>Qtd</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="l" items="${listaLivros}">
                    <tr>
                        <td>${l.id}</td>
                        <td>${l.isbn}</td>
                        <td><strong>${l.titulo}</strong></td>
                        <td>${l.autor}</td>
                        <td>${l.genero}</td>
                        <td>${l.editora}</td>
                        
                        <!-- Lógica de Exibição da Nota Dinâmica -->
                        <td>
                            <c:choose>
                                <%-- Se a média for maior que 0, exibe colorida --%>
                                <c:when test="${l.notaMedia > 0}">
                                    <span class="badge ${l.notaMedia >= 8 ? 'bg-green' : (l.notaMedia >= 5 ? 'bg-yellow' : 'bg-red')}">
                                        ${l.notaMediaFormatada}
                                    </span>
                                </c:when>
                                <%-- Se for 0, mostra que não tem avaliações ou usa a nota inicial do cadastro --%>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${l.classificacao > 0}">
                                            <!-- Mostra a nota inicial do Admin com um indicativo visual diferente (opcional) -->
                                            <span class="badge bg-gray" title="Nota inicial do sistema">${l.classificacao}.0</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color: #999; font-size: 0.9em;">Sem nota</span>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        
                        <td style="font-weight: bold; color: ${l.quantidade <= 0 ? 'red' : 'black'}">
                            ${l.quantidade}
                        </td>

                        <!-- Coluna de Ações -->
                        <td>
                            <c:choose>
                                <%-- ADMIN: Botão Estoque --%>
                                <c:when test="${sessionScope.usuarioLogado.perfil == 'ADMIN'}">
                                    <a href="adicionar-estoque?id=${l.id}" class="btn-action btn-estoque">
                                        + Estoque
                                    </a>
                                </c:when>
                                
                                <%-- LEITOR: Botão Emprestar --%>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${l.quantidade > 0}">
                                            <a href="novo-emprestimo?livroId=${l.id}" class="btn-action btn-emprestar">
                                                Emprestar
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="btn-action btn-disabled">Esgotado</span>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                
                <c:if test="${empty listaLivros}">
                    <tr><td colspan="9" style="text-align:center; padding: 20px; color: gray;">Nenhum livro encontrado.</td></tr>
                </c:if>
            </tbody>
        </table>
    </body>
</html>