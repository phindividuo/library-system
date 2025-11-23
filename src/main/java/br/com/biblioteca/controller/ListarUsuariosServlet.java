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
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/listar-usuarios")
public class ListarUsuariosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            List<Usuario> lista = dao.listar();

            // Coloca a lista na "pool" da requisição
            request.setAttribute("listaUsuarios", lista);

            // Manda para o JSP exibir
            request.getRequestDispatcher("lista-usuarios.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erro ao listar usuários", e);
        }
    }
}
