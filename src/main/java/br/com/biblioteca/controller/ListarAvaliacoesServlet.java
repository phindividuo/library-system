/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phindividuo
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.biblioteca.controller;

import br.com.biblioteca.dao.AvaliacaoDAO;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.model.Avaliacao;
import br.com.biblioteca.model.Livro;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/listar-avaliacoes")
public class ListarAvaliacoesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Verifica se tem filtro na URL (?livroId=5)
            String livroIdParam = request.getParameter("livroId");
            Integer idFiltro = null;
            if (livroIdParam != null && !livroIdParam.isEmpty()) {
                try {
                    idFiltro = Integer.parseInt(livroIdParam);
                } catch (NumberFormatException e) {
                    // Ignora ID inválido
                }
            }

            // Busca as avaliações
            AvaliacaoDAO dao = new AvaliacaoDAO();
            List<Avaliacao> listaAvaliacoes = dao.listar(idFiltro);

            // Busca lista de livros para popular o dropdown de filtro
            LivroDAO livroDao = new LivroDAO();
            List<Livro> todosLivros = livroDao.listar();

            // Envia tudo para o JSP
            request.setAttribute("listaAvaliacoes", listaAvaliacoes);
            request.setAttribute("listaLivros", todosLivros);
            request.setAttribute("filtroAtual", idFiltro); // Para manter o select marcado

            request.getRequestDispatcher("listar-avaliacoes.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erro ao listar avaliações", e);
        }
    }
}