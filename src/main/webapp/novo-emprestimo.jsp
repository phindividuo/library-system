<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Novo Empréstimo</title>
        <meta charset="UTF-8">
        <style>
            body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f6f9; }
            .container { max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
            
            label { display: block; margin-top: 15px; font-weight: bold; color: #333; }
            select, input { width: 100%; padding: 10px; margin-top: 5px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
            
            /* Estilo para campos "travados" (Read Only visual) */
            .campo-travado {
                background-color: #e9ecef;
                padding: 10px;
                border: 1px solid #ced4da;
                border-radius: 4px;
                color: #495057;
                font-weight: bold;
                margin-top: 5px;
            }

            button { margin-top: 25px; padding: 12px; background-color: #28a745; color: white; border: none; cursor: pointer; width: 100%; font-size: 16px; font-weight: bold; border-radius: 4px; }
            button:hover { background-color: #218838; }
            
            .alert-erro { background-color: #f8d7da; color: #721c24; padding: 10px; margin-bottom: 15px; border: 1px solid #f5c6cb; border-radius: 5px; }
            .back-link { text-decoration: none; color: #007bff; font-weight: bold; display: block; margin-bottom: 20px; }
        </style>
    </head>
    <body>
        <div class="container">
            <a href="dashboard" class="back-link">← Voltar ao Painel</a>
            <h1>Registrar Empréstimo</h1>

            <!-- CORREÇÃO AQUI: Ler mensagemErro (do request) OU param.erro (da URL) -->
            <c:if test="${not empty mensagemErro}">
                <div class="alert-erro">${mensagemErro}</div>
            </c:if>
            
            <c:if test="${not empty param.erro}">
                <div class="alert-erro">${param.erro}</div>
            </c:if>

            <form action="processar-emprestimo" method="POST">
                
                <!-- CAMPO 1: USUÁRIO -->
                <label>Usuário:</label>
                <c:choose>
                    <%-- Se for LEITOR, trava o campo nele mesmo --%>
                    <c:when test="${sessionScope.usuarioLogado.perfil == 'LEITOR'}">
                        <div class="campo-travado">
                            ${sessionScope.usuarioLogado.nome}
                        </div>
                        <!-- Input Hidden para enviar o ID mesmo sem mostrar o select -->
                        <input type="hidden" name="idUsuario" value="${sessionScope.usuarioLogado.id}">
                    </c:when>
                    
                    <%-- Se for ADMIN, mostra dropdown para escolher --%>
                    <c:otherwise>
                        <select name="idUsuario" required>
                            <option value="" disabled selected>-- Selecione o Usuário --</option>
                            <c:forEach var="u" items="${listaUsuarios}">
                                <option value="${u.id}">${u.nome} (${u.email})</option>
                            </c:forEach>
                        </select>
                    </c:otherwise>
                </c:choose>

                <!-- CAMPO 2: LIVRO -->
                <label>Livro:</label>
                <c:choose>
                    <%-- Se veio da lista (clicou em Emprestar), trava o livro --%>
                    <c:when test="${not empty livroAlvo}">
                        <div class="campo-travado">
                            ${livroAlvo.titulo}
                        </div>
                        <input type="hidden" name="idLivro" value="${livroAlvo.id}">
                    </c:when>
                    
                    <%-- Se veio do menu "Novo Empréstimo", mostra dropdown --%>
                    <c:otherwise>
                        <select name="idLivro" required>
                            <option value="" disabled selected>-- Selecione o Livro --</option>
                            <c:forEach var="l" items="${listaLivros}">
                                <option value="${l.id}" 
                                        style="color: ${l.quantidade <= 0 ? 'red' : 'black'}" 
                                        ${l.quantidade <= 0 ? 'disabled' : ''}>
                                    ${l.titulo} (Disp: ${l.quantidade})
                                </option>
                            </c:forEach>
                        </select>
                    </c:otherwise>
                </c:choose>

                <!-- CAMPO 3: PRAZO -->
                <label>Dias para devolução:</label>
                <input type="number" name="dias" value="7" min="1" max="30" required>

                <button type="submit">Confirmar Empréstimo</button>
            </form>
        </div>
    </body>
</html>