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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
            String msg;
            
            if (sucesso) {
                // Sucesso redireciona de volta para a lista com mensagem (via URL parameter simples)
                msg = "Renovado com sucesso! (+7 dias)";
                String msgEncoded = URLEncoder.encode(msg, StandardCharsets.UTF_8.toString());
                response.sendRedirect("meus-emprestimos?msg=" + msgEncoded);
            } else {
                msg = "Limite atingido. Apenas renove uma vez cada livro.";
                String msgEncoded = URLEncoder.encode(msg, StandardCharsets.UTF_8.toString());
                response.sendRedirect("meus-emprestimos?erro=" + msgEncoded);
            }

        } catch (Exception e) {
            throw new ServletException("Erro ao renovar empréstimo", e);
        }
    }
}
