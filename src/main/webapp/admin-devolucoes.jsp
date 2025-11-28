<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registrar Devolução</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container container-white">
        <a href="dashboard" class="back-link">← Voltar ao Painel</a>
        <h1>Registrar Devoluções</h1>
        <p style="color: #666;">Apenas empréstimos <strong>ATIVOS</strong> são mostrados aqui.</p>

        <c:if test="${not empty param.msg}"><div class="alert alert-success">${param.msg}</div></c:if>
        <c:if test="${not empty param.erro}"><div class="alert alert-danger">${param.erro}</div></c:if>

        <table>
            <thead>
                <tr>
                    <th class="th-dark">ID</th>
                    <th class="th-dark">Usuário</th>
                    <th class="th-dark">Livro</th>
                    <th class="th-dark">Previsão</th>
                    <th class="th-dark">Ação</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="e" items="${listaAtivos}">
                    <tr>
                        <td>${e.id}</td>
                        <td>${e.usuario.nome}</td>
                        <td>${e.livro.titulo}</td>
                        <td>${e.dataDevolucaoPrevistaFormatada}</td>
                        <td>
                            <a href="registrar-devolucao?id=${e.id}" class="btn btn-success btn-sm" onclick="return confirm('Confirmar recepção do livro?');">
                                ✔ Receber
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty listaAtivos}">
                    <tr><td colspan="5" style="text-align: center; color: #999;">Tudo devolvido! Nenhum empréstimo pendente.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>