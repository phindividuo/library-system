<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Adicionar Estoque</title>
        <meta charset="UTF-8">
        <style>
            body { font-family: Arial, sans-serif; margin: 20px; }
            .card { border: 1px solid #ccc; padding: 20px; max-width: 400px; border-radius: 5px; background-color: #f9f9f9; }
            label { display: block; margin-top: 15px; font-weight: bold; }
            input[type="number"] { width: 100%; padding: 8px; margin-top: 5px; box-sizing: border-box; }
            button { margin-top: 20px; padding: 10px 20px; background-color: #28a745; color: white; border: none; cursor: pointer; width: 100%; font-size: 1.1em; }
            .info { color: #555; margin-bottom: 5px; }
        </style>
    </head>
    <body>
        <a href="listar-livros" style="text-decoration: none;">← Voltar para Lista</a>
        <h1>Gerenciar Estoque</h1>

        <div class="card">
            <h2>${livro.titulo}</h2>
            <p class="info"><strong>ISBN:</strong> ${livro.isbn}</p>
            <p class="info"><strong>Estoque Atual:</strong> ${livro.quantidade} unidades</p>
            <hr>

            <form action="adicionar-estoque" method="POST">
                <!-- Passamos o ID para saber qual livro atualizar -->
                <input type="hidden" name="id" value="${livro.id}">
                <input type="hidden" name="titulo" value="${livro.titulo}">

                <label>Quantidade a Adicionar:</label>
                <input type="number" name="novaQuantidade" min="1" required placeholder="Ex: 5">

                <button type="submit">Confirmar Entrada</button>
            </form>
        </div>
    </body>
</html>