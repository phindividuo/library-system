<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Meus Livros</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container container-white">
        <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #eee; margin-bottom: 15px; padding-bottom: 10px;">
            <h1 style="color: var(--primary);">📚 Meus Empréstimos</h1>
            <a href="dashboard" class="back-link" style="margin:0;">← Voltar</a>
        </div>

        <c:if test="${not empty param.msg}"><div class="msg msg-ok">${param.msg}</div></c:if>
        <c:if test="${not empty param.erro}"><div class="msg msg-err">${param.erro}</div></c:if>

        <table>
            <thead>
                <tr>
                    <th>Livro</th>
                    <th>Data Empréstimo</th>
                    <th>Devolução Prevista</th>
                    <th>Status</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="e" items="${meusEmprestimos}">
                    <tr>
                        <td><strong>${e.livro.titulo}</strong></td>
                        <td>${e.dataEmprestimoFormatada}</td>
                        <td>${e.dataDevolucaoPrevistaFormatada}</td>
                        <td>
                            <span class="${e.status == 'ATIVO' ? 'status-badge st-ativo' : 'status-badge st-fim'}">${e.status}</span>
                        </td>
                        <td>
                            <div style="display: flex; gap: 5px;">
                                <c:if test="${e.status == 'ATIVO'}">
                                    <a href="renovar-emprestimo?id=${e.id}" class="btn btn-warning btn-sm" onclick="return confirm('Renovar por +7 dias?');">↻ Renovar</a>
                                </c:if>
                                <a href="avaliar-livro.jsp?idLivro=${e.livro.id}&titulo=${e.livro.titulo}&autor=${e.livro.autor}" class="btn btn-purple btn-sm">★ Avaliar</a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty meusEmprestimos}">
                    <tr><td colspan="5" style="text-align:center; padding: 30px; color: #999;">Você não possui livros emprestados.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>