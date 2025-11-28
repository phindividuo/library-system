<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Usuários</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container container-white">
        <div style="display: flex; justify-content: space-between;">
            <a href="dashboard" class="back-link">← Voltar ao Painel</a>
            <a href="cadastro-usuario.jsp" class="btn btn-success btn-sm">+ Novo Usuário</a>
        </div>
        
        <h1 style="margin-top: 10px;">Usuários Cadastrados</h1>

        <table>
            <thead>
                <tr>
                    <th class="th-orange">ID</th>
                    <th class="th-orange">Nome</th>
                    <th class="th-orange">E-mail</th>
                    <th class="th-orange">Perfil</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="u" items="${listaUsuarios}">
                    <tr>
                        <td>${u.id}</td>
                        <td><strong>${u.nome}</strong></td>
                        <td>${u.email}</td>
                        <td>${u.perfil}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>