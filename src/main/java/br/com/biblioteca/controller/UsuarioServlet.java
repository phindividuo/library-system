/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phindividuo
 */

package br.com.biblioteca.controller;

import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.model.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cadastrar-usuario")
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Garantir que vem do form é UTF-8
        request.setCharacterEncoding("UTF-8");
        // Garantir o que vai para a tela é UTF
        response.setCharacterEncoding("UTF-8");
        
        // Recebe os dados do formulário HTML
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        // Preenche o Modelo
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        try {
            // Chama o DAO para salvar no banco
            UsuarioDAO dao = new UsuarioDAO();
            dao.adicionar(usuario);

            // Se Sucesso: Redireciona para uma página de aviso

            request.setAttribute("mensagem", "Usuário " + nome + " cadastrado com sucesso!");
            request.getRequestDispatcher("sucesso.jsp").forward(request, response);

        } catch (Exception e) {
            // Se Erro:
            throw new ServletException("Erro ao cadastrar usuário", e);
        }
    }
}