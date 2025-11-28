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
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/renovar-emprestimo")
public class RenovarEmprestimoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            int idEmprestimo = Integer.parseInt(request.getParameter("id"));
            
            EmprestimoDAO dao = new EmprestimoDAO();
            boolean sucesso = dao.renovar(idEmprestimo);
            
            if (sucesso) {
                // Sucesso redireciona de volta para a lista com mensagem (via URL parameter simples)
                response.sendRedirect("meus-emprestimos?msg=Emprestimo renovado com sucesso! (+7 dias)");
            } else {
                // Falha se já foi renovado ou não está ativo
                response.sendRedirect("meus-emprestimos?erro=Nao foi possivel renovar. Limite atingido ou emprestimo inativo.");
            }

        } catch (Exception e) {
            throw new ServletException("Erro ao renovar empréstimo", e);
        }
    }
}
