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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cadastrar-livro")
public class LivroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Recebe dados (texto) do formulário
        String isbn = request.getParameter("isbn");
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String editora = request.getParameter("editora");
        String genero = request.getParameter("genero");
        
        // Recebe os os dados numéricos como texto
        String paginasStr = request.getParameter("numPaginas");
        String anoStr = request.getParameter("anoPublicacao");
        String classifStr = request.getParameter("classificacao");
        String qtdStr = request.getParameter("quantidade");

        // Preenche o Model
        Livro livro = new Livro();
        livro.setIsbn(isbn);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setEditora(editora);
        livro.setGenero(genero);
        
        // Converte os números
        try {
            livro.setNumPaginas(Integer.parseInt(paginasStr));
            livro.setAnoPublicacao(Integer.parseInt(anoStr));
            livro.setClassificacao(Integer.parseInt(classifStr));
            livro.setQuantidade(Integer.parseInt(qtdStr));
        } catch (NumberFormatException e) {
            // Se der erro na conversão, define 0 no número de páginas
            System.out.println("Erro ao converter número: " + e.getMessage());
            livro.setNumPaginas(0); 
        }

        try {
            // DAO
            LivroDAO dao = new LivroDAO();
            dao.adicionar(livro);

            request.setAttribute("mensagem", "Livro '" + titulo + "' cadastrado com sucesso!");
            request.getRequestDispatcher("sucesso.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erro ao cadastrar livro", e);
        }
    }
}
