/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phindividuo
 */

package br.com.biblioteca.controller;

import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/novo-emprestimo")
public class NovoEmprestimoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Busca todos usuários
            UsuarioDAO usuarioDao = new UsuarioDAO();
            List<Usuario> usuarios = usuarioDao.listar();

            // Busca todos livros
            LivroDAO livroDao = new LivroDAO();
            List<Livro> livros = livroDao.listar();

            // Coloca as listas na requisição
            request.setAttribute("listaUsuarios", usuarios);
            request.setAttribute("listaLivros", livros);

            // Manda para a tela (JSP)
            request.getRequestDispatcher("novo-emprestimo.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erro ao carregar formulário", e);
        }
    }
}
