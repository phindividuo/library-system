<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Avaliar Livro</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f6f9; margin: 0; padding: 0; }
            
            /* Barra Superior Simples */
            .header { background-color: #007bff; color: white; padding: 15px; text-align: center; margin-bottom: 30px; }
            .header h1 { margin: 0; font-size: 1.5rem; font-weight: 300; }

            .container { max-width: 600px; margin: 0 auto 40px auto; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
            
            h2 { color: #333; margin-top: 0; border-bottom: 2px solid #eee; padding-bottom: 10px; margin-bottom: 20px; }
            
            label { display: block; margin-top: 20px; font-weight: 600; color: #555; margin-bottom: 5px; }
            
            select, textarea { 
                width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 6px; 
                box-sizing: border-box; font-family: inherit; font-size: 1rem; transition: border-color 0.3s; 
            }
            select:focus, textarea:focus { border-color: #007bff; outline: none; }
            
            button { 
                margin-top: 30px; padding: 15px; background-color: #28a745; color: white; 
                border: none; cursor: pointer; width: 100%; font-size: 1.1rem; font-weight: bold; 
                border-radius: 6px; transition: background-color 0.3s; 
            }
            button:hover { background-color: #218838; }
            
            .back-link { text-decoration: none; color: #6c757d; display: inline-block; margin-bottom: 20px; font-weight: 500; transition: color 0.2s; }
            .back-link:hover { color: #333; }
            
            .book-info { background: #e9ecef; padding: 20px; border-radius: 8px; margin-bottom: 25px; border-left: 5px solid #007bff; }
            .book-info strong { color: #333; }
            .book-title { font-size: 1.2rem; display: block; margin-bottom: 5px; color: #007bff; }
        </style>
    </head>
    <body>
        <div class="header">
            <h1>Sistema de Biblioteca</h1>
        </div>

        <div class="container">
            <a href="meus-emprestimos" class="back-link">← Voltar para Meus Empréstimos</a>
            
            <h2>Avaliar Obra</h2>
            
            <div class="book-info">
                <span class="book-title">${livro.titulo}</span>
                <strong>Autor:</strong> ${livro.autor}
            </div>

            <form action="avaliar-livro" method="POST">
                <input type="hidden" name="idLivro" value="${livro.id}">

                <label for="nota">Sua Nota (0 a 10):</label>
                <select name="nota" id="nota" required>
                    <option value="" disabled ${avaliacaoAnterior == null ? 'selected' : ''}>Selecione uma nota...</option>
                    <c:forEach begin="0" end="10" var="i">
                        <!-- Se já tiver nota anterior, deixa selecionado -->
                        <option value="${i}" ${avaliacaoAnterior.nota == i ? 'selected' : ''}>${i}</option>
                    </c:forEach>
                </select>

                <label for="comentario">Comentário (Opcional):</label>
                <textarea name="comentario" id="comentario" rows="5" placeholder="Escreva aqui o que você achou do livro...">${avaliacaoAnterior.comentario}</textarea>

                <button type="submit">Salvar Avaliação</button>
            </form>
        </div>
    </body>
</html>