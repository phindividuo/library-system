/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phindividuo
 */

package br.com.biblioteca.controller;

import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.model.Usuario;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/meus-emprestimos")
public class MeusEmprestimosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Recupera a Sessão
        HttpSession session = request.getSession(false);
        
        // Verifica se está logado como segurança
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Pega o usuário da sessão
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        try {
            // Chama o DAO filtrando pelo ID do usuário logado
            EmprestimoDAO dao = new EmprestimoDAO();
            List<Emprestimo> lista = dao.listarPorUsuario(usuarioLogado.getId());

            // Envia para o JSP
            request.setAttribute("meusEmprestimos", lista);
            request.getRequestDispatcher("meus-emprestimos.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erro ao buscar meus empréstimos", e);
        }
    }
}