<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
    <title>Novo Livro</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <style>
        .row { display: flex; gap: 15px; }
        .col { flex: 1; }
        /* Pequeno ajuste para garantir que inputs number não tenham setas em alguns browsers se desejar, mas padrão ajuda */
    </style>
</head>
<body>
    <div class="container" style="max-width: 700px;">
        <div class="container-white">
            <a href="dashboard" class="back-link">← Voltar ao Menu</a>
            <h1 style="border-bottom: 1px solid #eee; padding-bottom: 15px;">Cadastrar Livro</h1>
            
            <form action="cadastrar-livro" method="POST">
                <label>Título da Obra:</label>
                <!-- Limite de 100 caracteres para evitar erro de banco de dados -->
                <input type="text" name="titulo" required maxlength="100" placeholder="Insira o título completo">
                
                <div class="row">
                    <div class="col">
                        <label>Autor:</label>
                        <input type="text" name="autor" required maxlength="100">
                    </div>
                    <div class="col">
                        <label>Editora:</label>
                        <input type="text" name="editora" maxlength="50">
                    </div>
                </div>

                <div class="row">
                    <div class="col">
                        <label>ISBN-13:</label>
                        <!-- type="text" para permitir traços, mas limitado a 17 chars (13 numeros + traços) -->
                        <input type="text" name="isbn" placeholder="Ex: 978-3-16-148410-0" required maxlength="20">
                    </div>
                    <div class="col">
                        <label>Gênero:</label>
            <select name="genero" required>
                <option value="" disabled selected>Selecione um gênero...</option>
                
                <optgroup label="Ficção Literária e Clássicos">
                    <option value="Clássicos">Clássicos da Literatura</option>
                    <option value="Ficção Contemporânea">Ficção Contemporânea</option>
                    <option value="Poesia">Poesia</option>
                    <option value="Teatro">Teatro / Dramaturgia</option>
                    <option value="Contos">Contos e Crônicas</option>
                </optgroup>

                <optgroup label="Fantasia e Sci-Fi">
                    <option value="Alta Fantasia">Alta Fantasia (High Fantasy)</option>
                    <option value="Fantasia Contemporânea">Fantasia Contemporânea</option>
                    <option value="Realismo Mágico">Realismo Mágico</option>
                    <option value="Ficção Científica">Ficção Científica (Sci-Fi)</option>
                    <option value="Cyberpunk">Cyberpunk / Steampunk</option>
                    <option value="Distopia">Distopia / Pós-Apocalíptico</option>
                    <option value="Space Opera">Space Opera</option>
                </optgroup>

                <optgroup label="Suspense">
                    <option value="Policial">Policial / Detetive</option>
                    <option value="Thriller">Thriller</option>
                    <option value="Mistério">Mistério</option>
                    <option value="Terror">Terror / Horror</option>
                </optgroup>

                <optgroup label="Romance">
                    <option value="Romance Contemporâneo">Romance Contemporâneo</option>
                    <option value="Romance de Época">Romance de Época</option>
                    <option value="Comédia Romântica">Comédia Romântica</option>
                    <option value="Romance Dark">Romance Erótico / Dark Romance</option>
                    <option value="Romance LGBTQIA+">Romance LGBTQIA+</option>
                </optgroup>

                <optgroup label="Outros de Ficção">
                    <option value="Ficção Histórica">Ficção Histórica</option>
                    <option value="Aventura">Aventura / Ação</option>
                    <option value="Humor">Humor / Sátira</option>
                    <option value="Ficção Religiosa">Ficção Cristã / Religiosa</option>
                </optgroup>

                <optgroup label="Biografia e Autoajuda">
                    <option value="Biografia">Biografia / Autobiografia</option>
                    <option value="Memórias">Diários / Memórias</option>
                    <option value="Autoajuda">Autoajuda / Desenvolvimento Pessoal</option>
                    <option value="Produtividade">Produtividade e Gestão</option>
                    <option value="Relacionamentos">Relacionamentos</option>
                    <option value="Espiritualidade">Espiritualidade</option>
                </optgroup>

                <optgroup label="Negócios">
                    <option value="Administração">Administração e Liderança</option>
                    <option value="Economia">Economia</option>
                    <option value="Finanças">Finanças Pessoais / Investimentos</option>
                    <option value="Marketing">Marketing e Vendas</option>
                    <option value="Empreendedorismo">Empreendedorismo</option>
                </optgroup>

                <optgroup label="Sociedade e História">
                    <option value="História Geral">História Geral</option>
                    <option value="História Brasil">História do Brasil</option>
                    <option value="Política">Política</option>
                    <option value="Sociologia">Sociologia e Antropologia</option>
                    <option value="Filosofia">Filosofia</option>
                    <option value="True Crime">True Crime</option>
                    <option value="Direito">Direito</option>
                </optgroup>

                <optgroup label="Ciência e Saúde">
                    <option value="Ciência">Divulgação Científica</option>
                    <option value="Tecnologia">Tecnologia da Informação</option>
                    <option value="Medicina">Medicina e Saúde</option>
                    <option value="Psicologia">Psicologia</option>
                </optgroup>

                <optgroup label="Estilo de Vida e Artes">
                    <option value="Gastronomia">Culinária e Gastronomia</option>
                    <option value="Viagem">Viagem e Turismo</option>
                    <option value="Esportes">Esportes</option>
                    <option value="Artes">Artes</option>
                    <option value="Moda">Moda e Beleza</option>
                    <option value="Música">Música</option>
                    <option value="Cinema">Cinema</option>
                </optgroup>

                <optgroup label="Religião">
                    <option value="Teologia">Teologia</option>
                    <option value="Esoterismo">Esoterismo</option>
                    <option value="Religiões">Religiões</option>
                </optgroup>

                <optgroup label="Infantil e Jovem Adulto">
                    <option value="Infantil">Infantil (0-8 anos)</option>
                    <option value="Infantojuvenil">Infantojuvenil (8-12 anos)</option>
                    <option value="Jovem Adulto">Jovem Adulto (YA)</option>
                </optgroup>

                <optgroup label="Formatos e Referência">
                    <option value="Mangá">Mangás</option>
                    <option value="Quadrinhos">HQs / Comics</option>
                    <option value="Graphic Novel">Graphic Novels</option>
                    <option value="Didático">Livros Didáticos / Escolares</option>
                    <option value="Dicionário">Dicionários / Enciclopédias</option>
                </optgroup>
            </select>
                    </div>
                </div>

                <div class="row">
                    <div class="col">
                        <label>Páginas:</label>
                        <!-- type="number" impede letras -->
                        <input type="number" name="numPaginas" min="1" max="5000">
                    </div>
                    <div class="col">
                        <label>Ano:</label>
                        <!-- Limita o ano para evitar erros de int overflow ou datas irreais -->
                        <input type="number" name="anoPublicacao" min="1000" max="2099" placeholder="Ex: 2024">
                    </div>
                </div>

                <div style="background: #e9ecef; padding: 15px; border-radius: 5px; margin-top: 15px;">
                    <label style="margin-top:0;">Quantidade Inicial em Estoque:</label>
                    <input type="number" name="quantidade" required min="1" max="999" value="1" style="margin-bottom:0;">
                </div>

                <button type="submit" class="btn btn-success full-width">Salvar no Acervo</button>
            </form>
        </div>
    </div>
</body>
</html>