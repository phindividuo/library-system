<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Opiniões dos Leitores</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <style>
        .filter-box { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); margin-bottom: 20px; display: flex; gap: 10px; align-items: center; }
        .card-top { display: flex; justify-content: space-between; margin-bottom: 10px; border-bottom: 1px solid #eee; padding-bottom: 10px; }
    </style>
</head>
<body>
    <div class="container" style="max-width: 900px;">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px;">
            <h1 style="margin:0;">🗣️ O que estão dizendo</h1>
            <a href="dashboard" class="back-link" style="margin:0;">← Voltar</a>
        </div>

        <form action="listar-avaliacoes" method="GET" class="filter-box">
            <select name="livroId" style="margin:0; flex-grow:1;">
                <option value="">Todas as Avaliações</option>
                <c:forEach var="l" items="${listaLivros}">
                    <option value="${l.id}" ${filtroAtual == l.id ? 'selected' : ''}>${l.titulo}</option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn-sm">Filtrar</button>
        </form>

        <c:forEach var="a" items="${listaAvaliacoes}">
            <div class="review-card ${a.nota >= 8 ? 'border-green' : (a.nota >= 5 ? 'border-orange' : 'border-red')}">
                <div class="card-top">
                    <div>
                        <div style="font-size: 1.1rem; font-weight: bold; color: #333;">${a.livro.titulo}</div>
                        <div style="color: #888; font-size: 0.9rem;">por ${a.usuario.nome}</div>
                    </div>
                    <div>
                        <span class="badge ${a.nota >= 8 ? 'bg-success' : (a.nota >= 5 ? 'bg-warning' : 'bg-danger')}">
                            ★ ${a.nota}/10
                        </span>
                    </div>
                </div>
                <div style="font-style: italic; color: #555;">
                    "${empty a.comentario ? 'Sem comentário adicional.' : a.comentario}"
                </div>
            </div>
        </c:forEach>
        
        <c:if test="${empty listaAvaliacoes}">
            <div style="text-align:center; margin-top: 50px; color: #999;">
                <h3>Nenhuma avaliação encontrada.</h3>
            </div>
        </c:if>
    </div>
</body>
</html>