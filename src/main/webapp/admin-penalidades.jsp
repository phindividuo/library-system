<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Penalidades Ativas</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container container-white" style="border-left: 5px solid var(--danger);">
        <a href="dashboard" class="back-link">← Voltar</a>
        <h1 class="text-red">🚫 Usuários Bloqueados</h1>
        
        <c:if test="${not empty param.msg}"><div class="msg msg-ok">${param.msg}</div></c:if>

        <table>
            <thead>
                <tr>
                    <th class="th-red">Usuário</th>
                    <th class="th-red">Livro (Causa)</th>
                    <th class="th-red">Data Bloqueio</th>
                    <th class="th-red">Dias Restantes</th>
                    <th class="th-red">Motivo</th>
                    <th class="th-red">Ação</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${listaPenalidades}">
                    <tr>
                        <td><strong>${p.usuario.nome}</strong></td>
                        <td>${p.emprestimo.livro.titulo}</td>
                        <td>${p.dataAplicacao}</td>
                        <td style="color: var(--danger); font-weight: bold;">${p.diasBloqueio}</td>
                        <td>${p.motivo}</td>
                        <td>
                            <a href="remover-penalidade?id=${p.id}&idUsuario=${p.usuario.id}" class="btn btn-warning btn-sm" onclick="return confirm('Tem certeza? Isso desbloqueará o usuário imediatamente.');">
                                🔓 Remover
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty listaPenalidades}">
                    <tr><td colspan="6" style="text-align:center;">Nenhum usuário bloqueado no momento.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>