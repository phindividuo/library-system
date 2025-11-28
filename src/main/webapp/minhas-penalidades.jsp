<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Minhas Penalidades</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container container-white" style="border-top: 5px solid var(--danger);">
        <a href="dashboard" class="back-link">← Voltar</a>
        <h1 class="text-red">Minhas Penalidades</h1>
        <p>Se houver atrasos na devolução, bloqueios temporários aparecerão aqui.</p>

        <table>
            <thead>
                <tr>
                    <th class="th-red">Data</th>
                    <th class="th-red">Livro</th>
                    <th class="th-red">Dias de Bloqueio</th>
                    <th class="th-red">Motivo</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${listaPenalidades}">
                    <tr>
                        <td>${p.dataAplicacao}</td>
                        <td>${p.emprestimo.livro.titulo}</td>
                        <td style="font-weight: bold; color: var(--danger);">${p.diasBloqueio}</td>
                        <td>${p.motivo}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty listaPenalidades}">
                    <tr><td colspan="4" style="text-align: center; padding: 20px; color: var(--success);">Nenhuma penalidade registrada. Sua conta está regular! ✅</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>