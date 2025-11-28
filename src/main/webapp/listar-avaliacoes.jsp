<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Opiniões dos Leitores</title>
        <meta charset="UTF-8">
        <style>
            body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f6f9; margin: 0; padding: 20px; }
            .container { max-width: 900px; margin: 0 auto; }
            
            .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
            .header h1 { margin: 0; color: #333; }
            .back-link { text-decoration: none; color: #007bff; font-weight: bold; }

            /* Filtro */
            .filter-box { background: white; padding: 15px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); margin-bottom: 20px; display: flex; gap: 10px; align-items: center; }
            select { padding: 8px; border-radius: 4px; border: 1px solid #ddd; flex-grow: 1; }
            .btn-filter { padding: 8px 15px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; }

            /* Cards de Avaliação */
            .review-card { background: white; border-radius: 8px; padding: 20px; margin-bottom: 15px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); border-left: 5px solid #ddd; }
            .review-card.nota-high { border-left-color: #28a745; } /* Notas 8-10 */
            .review-card.nota-mid { border-left-color: #ffc107; }  /* Notas 5-7 */
            .review-card.nota-low { border-left-color: #dc3545; }  /* Notas 0-4 */

            .card-header { display: flex; justify-content: space-between; margin-bottom: 10px; border-bottom: 1px solid #eee; padding-bottom: 10px; }
            .book-title { font-size: 1.1rem; font-weight: bold; color: #007bff; }
            .user-name { color: #666; font-size: 0.9rem; }
            
            .rating-badge { background: #333; color: white; padding: 5px 10px; border-radius: 15px; font-weight: bold; font-size: 0.9rem; }
            .rating-badge.high { background: #28a745; }
            .rating-badge.mid { background: #ffc107; color: #333; }
            .rating-badge.low { background: #dc3545; }

            .comment { font-style: italic; color: #555; line-height: 1.5; }
            .no-comment { color: #999; font-size: 0.9rem; }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <h1>🗣️ Opiniões dos Leitores</h1>
                <a href="dashboard" class="back-link">← Voltar ao Painel</a>
            </div>

            <!-- Área de Filtro -->
            <form action="listar-avaliacoes" method="GET" class="filter-box">
                <label>Filtrar por Livro:</label>
                <select name="livroId">
                    <option value="">Todas as Avaliações</option>
                    <c:forEach var="l" items="${listaLivros}">
                        <option value="${l.id}" ${filtroAtual == l.id ? 'selected' : ''}>
                            ${l.titulo}
                        </option>
                    </c:forEach>
                </select>
                <button type="submit" class="btn-filter">Filtrar</button>
            </form>

            <!-- Lista de Avaliações -->
            <c:forEach var="a" items="${listaAvaliacoes}">
                <div class="review-card ${a.nota >= 8 ? 'nota-high' : (a.nota >= 5 ? 'nota-mid' : 'nota-low')}">
                    <div class="card-header">
                        <div>
                            <span class="book-title">${a.livro.titulo}</span>
                            <br>
                            <span class="user-name">Avaliado por: <strong>${a.usuario.nome}</strong></span>
                        </div>
                        <div>
                            <span class="rating-badge ${a.nota >= 8 ? 'high' : (a.nota >= 5 ? 'mid' : 'low')}">
                                Nota: ${a.nota}/10
                            </span>
                        </div>
                    </div>
                    
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty a.comentario}">
                                <p class="comment">"${a.comentario}"</p>
                            </c:when>
                            <c:otherwise>
                                <p class="no-comment">(Sem comentário escrito)</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:forEach>

            <c:if test="${empty listaAvaliacoes}">
                <div style="text-align: center; color: #666; margin-top: 40px;">
                    <h3>Nenhuma avaliação encontrada.</h3>
                    <p>Seja o primeiro a avaliar um livro!</p>
                </div>
            </c:if>
        </div>
    </body>
</html>