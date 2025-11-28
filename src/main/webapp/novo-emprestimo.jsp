<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Novo Empréstimo</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container" style="max-width: 600px;">
        <div class="container-white">
            <a href="dashboard" class="back-link">← Voltar ao Menu</a>
            <h1>Realizar Empréstimo</h1>
            
            <c:if test="${not empty mensagemErro}"><div class="msg msg-err">${mensagemErro}</div></c:if>
            <c:if test="${not empty param.erro}"><div class="msg msg-err">${param.erro}</div></c:if>

            <form action="processar-emprestimo" method="POST">
                <!-- USUÁRIO -->
                <label>Leitor:</label>
                <c:choose>
                    <c:when test="${sessionScope.usuarioLogado.perfil == 'LEITOR'}">
                        <input type="text" value="${sessionScope.usuarioLogado.nome}" class="read-only" readonly>
                        <input type="hidden" name="idUsuario" value="${sessionScope.usuarioLogado.id}">
                    </c:when>
                    <c:otherwise>
                        <select name="idUsuario" required>
                            <option value="">Selecione o usuário...</option>
                            <c:forEach var="u" items="${listaUsuarios}">
                                <option value="${u.id}">${u.nome} (${u.email})</option>
                            </c:forEach>
                        </select>
                    </c:otherwise>
                </c:choose>

                <!-- LIVRO -->
                <label>Livro:</label>
                <c:choose>
                    <c:when test="${not empty param.livroId}">
                         <c:if test="${not empty livroAlvo}">
                            <input type="text" value="${livroAlvo.titulo}" class="read-only" readonly>
                            <input type="hidden" name="idLivro" value="${livroAlvo.id}">
                         </c:if>
                         <c:if test="${empty livroAlvo}">
                             <select name="idLivro" class="read-only">
                                 <option value="${param.livroId}" selected>Livro ID: ${param.livroId} (Selecionado)</option>
                             </select>
                         </c:if>
                    </c:when>
                    <c:otherwise>
                        <select name="idLivro" required>
                            <option value="">Selecione a obra...</option>
                            <c:forEach var="l" items="${listaLivros}">
                                <option value="${l.id}" ${l.quantidade <= 0 ? 'disabled' : ''} style="color: ${l.quantidade <= 0 ? 'red' : 'black'}">
                                    ${l.titulo} ${l.quantidade <= 0 ? '(Esgotado)' : ''}
                                </option>
                            </c:forEach>
                        </select>
                    </c:otherwise>
                </c:choose>

                <!-- PRAZO -->
                <label>Período (dias):</label>
                <input type="number" name="dias" value="7" min="1" max="30" required>

                <button type="submit" class="full-width">Confirmar Empréstimo</button>
            </form>
        </div>
    </div>
</body>
</html>