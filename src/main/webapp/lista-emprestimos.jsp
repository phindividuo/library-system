<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Histórico de Empréstimos</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container container-white">
        <div style="display: flex; justify-content: space-between; align-items: center;">
            <a href="dashboard" class="back-link">← Voltar ao Menu</a>
        </div>

        <h1 class="text-purple">Histórico Completo</h1>
        
        <table>
            <thead>
                <tr>
                    <th class="th-purple">ID</th>
                    <th class="th-purple">Usuário</th>
                    <th class="th-purple">Livro</th>
                    <th class="th-purple">Data Saída</th>
                    <th class="th-purple">Previsão</th>
                    <th class="th-purple">Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="e" items="${listaEmprestimos}">
                    <tr>
                        <td>${e.id}</td>
                        <td>${e.usuario.nome}</td>
                        <td>${e.livro.titulo}</td>
                        <td>${e.dataEmprestimoFormatada}</td>
                        <td>${e.dataDevolucaoPrevistaFormatada}</td>
                        <td>
                            <span class="${e.status == 'ATIVO' ? 'status-badge st-ativo' : 'status-badge st-inativo'}">
                                ${e.status}
                            </span>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty listaEmprestimos}">
                    <tr><td colspan="6" style="text-align:center">Nenhum registro encontrado.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>