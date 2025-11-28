<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
    <title>Avaliar Obra</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <style>
        .header-avaliacao { 
            background: var(--purple); 
            color: white; 
            padding: 20px; 
            border-radius: 8px 8px 0 0; 
            /* As margens negativas puxam o header para a borda do container-white */
            margin: -30px -30px 30px -30px; 
            text-align: center; 
        }
        /* Ajuste para o link ficar fora e visível */
        .top-nav-aval {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="container" style="max-width: 600px;">
        
        <!-- CORREÇÃO LAYOUT: Link fora do container branco para não sobrepor o header roxo -->
        <div class="top-nav-aval">
            <a href="meus-emprestimos" class="back-link">← Cancelar</a>
        </div>

        <div class="container-white">
            
            <div class="header-avaliacao">
                <span style="font-size: 1.5rem; display: block;">${empty livro ? param.titulo : livro.titulo}</span>
                <span style="font-size: 0.9rem; opacity: 0.9;">${empty livro ? param.autor : livro.autor}</span>
            </div>

            <!-- CORREÇÃO ENCODING: Mensagens de erro/sucesso agora devem aparecer com acentos corretos -->
            <c:if test="${not empty param.msg}"><div class="msg msg-ok">${param.msg}</div></c:if>

            <form action="avaliar-livro" method="POST">
                <input type="hidden" name="idLivro" value="${empty livro ? param.idLivro : livro.id}">

                <label>Qual nota você dá para este livro?</label>
                <select name="nota" required>
                    <option value="" disabled selected>Selecione de 0 a 10...</option>
                    <c:forEach begin="0" end="10" var="i">
                        <option value="${i}" ${avaliacaoAnterior.nota == i ? 'selected' : ''}>${i} ${i==10 ? '⭐ Excelente' : ''}</option>
                    </c:forEach>
                </select>

                <label>Deixe um comentário (opcional):</label>
                <textarea name="comentario" rows="5" maxlength="500" placeholder="O que você achou da história?">${avaliacaoAnterior.comentario}</textarea>

                <button type="submit" class="btn-purple full-width">Enviar Avaliação</button>
            </form>
        </div>
    </div>
</body>
</html>