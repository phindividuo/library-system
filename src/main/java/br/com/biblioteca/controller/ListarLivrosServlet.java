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
import br.com.biblioteca.model.Livro;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/listar-livros")
public class ListarLivrosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Usa DAO para buscar a lista
            LivroDAO dao = new LivroDAO();
            List<Livro> lista = dao.listar();

            // Coloca a lista na requisição para o JSP ler
            request.setAttribute("listaLivros", lista);

            // Encaminha para a página
            request.getRequestDispatcher("lista-livros.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erro ao listar livros", e);
        }
    }
}
