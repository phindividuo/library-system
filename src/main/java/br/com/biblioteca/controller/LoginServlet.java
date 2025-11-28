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
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        try {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario usuario = dao.autenticar(email, senha);

            if (usuario != null) {
                // Sucesso -> cria a sessão
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", usuario); // Guarda o objeto inteiro

                // Redireciona com base no perfil
                if ("ADMIN".equals(usuario.getPerfil())) {
                    response.sendRedirect("admin-dashboard.jsp"); 
                } else {
                    response.sendRedirect("leitor-dashboard.jsp"); 
                }
            } else {
                request.setAttribute("erro", "Email ou senha inválidos");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            throw new ServletException("Erro no login", e);
        }
    }
    
    // Logout (GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Destrói a sessão
        }
        response.sendRedirect("login.jsp");
    }
}