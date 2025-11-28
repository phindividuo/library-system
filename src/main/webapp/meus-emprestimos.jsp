<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Meus Livros Emprestados</title>
        <meta charset="UTF-8">
        <style>
            body { font-family: Arial, sans-serif; margin: 20px; background-color: #f8f9fa; }
            .container { max-width: 950px; margin: 0 auto; }
            
            table { width: 100%; border-collapse: collapse; margin-top: 20px; background: white; box-shadow: 0 2px 4px rgba(0,0,0,0.1); border-radius: 8px; overflow: hidden; }
            th, td { border-bottom: 1px solid #eee; padding: 15px; text-align: left; vertical-align: middle; }
            th { background-color: #007bff; color: white; text-transform: uppercase; font-size: 0.85rem; letter-spacing: 1px; }
            tr:hover { background-color: #f1f1f1; }
            
            /* Status Badges */
            .status-ativo { color: #155724; background-color: #d4edda; padding: 6px 12px; border-radius: 20px; font-size: 0.85rem; font-weight: bold; border: 1px solid #c3e6cb; }
            .status-finalizado { color: #6c757d; background-color: #e2e3e5; padding: 6px 12px; border-radius: 20px; font-size: 0.85rem; font-weight: bold; border: 1px solid #d6d8db; }
            
            /* Botões de Ação */
            .action-group { display: flex; gap: 8px; align-items: center; }

            .btn { 
                text-decoration: none; padding: 6px 12px; border-radius: 4px; 
                font-size: 0.85rem; font-weight: bold; transition: all 0.2s; display: inline-block; text-align: center;
                border: 1px solid transparent;
            }

            /* Botão Renovar (Amarelo) */
            .btn-renovar { background-color: #ffc107; color: #333; border-color: #e0a800; }
            .btn-renovar:hover { background-color: #e0a800; }

            /* Botão Avaliar (Roxo/Azul) */
            .btn-avaliar { background-color: #6f42c1; color: white; border-color: #59359a; }
            .btn-avaliar:hover { background-color: #59359a; color: #fff; }

            /* Botão Desabilitado */
            .btn-disabled { opacity: 0.6; cursor: not-allowed; background-color: #e9ecef; border-color: #dee2e6; color: #adb5bd; pointer-events: none; }
            
            .back-link { text-decoration: none; color: #007bff; font-size: 1rem; display: inline-block; margin-bottom: 15px; font-weight: bold; }
            .back-link:hover { text-decoration: underline; }
            
            /* Mensagens de Feedback */
            .alert { padding: 15px; margin-bottom: 20px; border: 1px solid transparent; border-radius: 4px; }
            .alert-success { color: #155724; background-color: #d4edda; border-color: #c3e6cb; }
            .alert-danger { color: #721c24; background-color: #f8d7da; border-color: #f5c6cb; }
        </style>
    </head>
    <body>
        <div class="container">
            <a href="dashboard" class="back-link">← Voltar ao Painel</a>
            <h1>📚 Meus Empréstimos</h1>

            <!-- Exibe Mensagens de Sucesso/Erro da URL -->
            <c:if test="${not empty param.msg}">
                <div class="alert alert-success">${param.msg}</div>
            </c:if>
            <c:if test="${not empty param.erro}">
                <div class="alert alert-danger">${param.erro}</div>
            </c:if>

            <table>
                <thead>
                    <tr>
                        <th>Livro</th>
                        <th>Data Empréstimo</th>
                        <th>Previsão Devolução</th>
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
                                <span class="${e.status == 'ATIVO' ? 'status-ativo' : 'status-finalizado'}">
                                    ${e.status}
                                </span>
                            </td>
                            
                            <td>
                                <div class="action-group">
                                    <!-- Botão Renovar -->
                                    <c:choose>
                                        <c:when test="${e.status == 'ATIVO'}">
                                            <a href="renovar-emprestimo?id=${e.id}" class="btn btn-renovar" onclick="return confirm('Deseja renovar este livro por mais 7 dias?');">
                                                ↻ Renovar
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="btn btn-disabled">Finalizado</span>
                                        </c:otherwise>
                                    </c:choose>

                                    <!-- Botão Avaliar  -->
                                    <a href="avaliar-livro?id=${e.livro.id}" class="btn btn-avaliar">
                                        ★ Avaliar
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    
                    <c:if test="${empty meusEmprestimos}">
                        <tr><td colspan="5" style="text-align:center; padding: 30px; color: #666;">Você não possui empréstimos ativos no momento.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </body>
</html>