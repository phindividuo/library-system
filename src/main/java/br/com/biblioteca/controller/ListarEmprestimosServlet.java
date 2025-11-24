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
import java.io.IOException;
import java.util.List;
import java.time.format.DateTimeFormatter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/listar-emprestimos")
public class ListarEmprestimosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            EmprestimoDAO dao = new EmprestimoDAO();
            List<Emprestimo> lista = dao.listar();
            
            request.setAttribute("listaEmprestimos", lista);
            request.getRequestDispatcher("lista-emprestimos.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erro ao listar empréstimos", e);
        }
    }
}