<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Lista de Usuários</title>
        <style>
            table, th, td { border: 1px solid black; border-collapse: collapse; padding: 5px; }
        </style>
    </head>
    <body>
        <h1>Usuários Cadastrados</h1>
        
        <a href="index.html">+ Novo Usuário</a>
        <br><br>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>E-mail</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="u" items="${listaUsuarios}">
                    <tr>
                        <td>${u.id}</td>
                        <td>${u.nome}</td>
                        <td>${u.email}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>