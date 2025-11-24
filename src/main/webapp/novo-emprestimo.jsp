<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Novo Empréstimo</title>
        <meta charset="UTF-8">
        <style>
            body { font-family: Arial, sans-serif; margin: 20px; }
            form { max-width: 500px; }
            label { display: block; margin-top: 15px; font-weight: bold; }
            select, input { width: 100%; padding: 8px; margin-top: 5px; }
            button { margin-top: 20px; padding: 10px 20px; background-color: #007bff; color: white; border: none; cursor: pointer; }
            
            /* Estilo novo para a caixa de erro */
            .alert-erro {
                background-color: #f8d7da;
                color: #721c24;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #f5c6cb;
                border-radius: 5px;
            }
        </style>
    </head>
    <body>
        <a href="index.html">← Voltar</a>
        <h1>Registrar Empréstimo</h1>

        <!-- Exibe mensagem de erro se o Servlet devolver uma -->
        <c:if test="${not empty mensagemErro}">
            <div class="alert-erro">
                ${mensagemErro}
            </div>
        </c:if>

        <form action="processar-emprestimo" method="POST">
            
            <label>Selecione o Usuário:</label>
            <select name="idUsuario" required>
                <option value="" disabled selected>-- Escolha um Usuário --</option>
                <c:forEach var="u" items="${listaUsuarios}">
                    <option value="${u.id}">${u.nome} (${u.email})</option>
                </c:forEach>
            </select>

            <label>Selecione o Livro:</label>
            <select name="idLivro" required>
                <option value="" disabled selected>-- Escolha um Livro --</option>
                <c:forEach var="l" items="${listaLivros}">
                    <!-- 
                        Lógica Visual: 
                        1. Se quantidade <= 0, pinta de vermelho.
                        2. Se quantidade <= 0, adiciona o atributo 'disabled' para não deixar selecionar.
                    -->
                    <option value="${l.id}" 
                            style="color: ${l.quantidade <= 0 ? 'red' : 'black'}" 
                            ${l.quantidade <= 0 ? 'disabled' : ''}>
                        ${l.titulo} (Disp: ${l.quantidade})
                    </option>
                </c:forEach>
            </select>

            <label>Dias para devolução:</label>
            <input type="number" name="dias" value="7" min="1" required>

            <button type="submit">Confirmar Empréstimo</button>
        </form>
    </body>
</html>