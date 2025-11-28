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
        
        // Garantir que vem do form é UTF-8
        request.setCharacterEncoding("UTF-8");
        // Garantir o que vai para a tela é UTF
        response.setCharacterEncoding("UTF-8");
        
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

        // Preenche o Model com dados de texto
        Livro livro = new Livro();
        livro.setIsbn(isbn);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setEditora(editora);
        livro.setGenero(genero);
        
        // Converte os números com valores padrão em caso de erro/vazio
        try {
            int paginas = (paginasStr == null || paginasStr.isEmpty()) ? 0 : Integer.parseInt(paginasStr);
            livro.setNumPaginas(paginas);
        } catch (NumberFormatException e) {
            livro.setNumPaginas(0);
        }

        try {
            int ano = (anoStr == null || anoStr.isEmpty()) ? 0 : Integer.parseInt(anoStr);
            livro.setAnoPublicacao(ano);
        } catch (NumberFormatException e) {
            livro.setAnoPublicacao(0);
        }

        try {
            int classificacao = (classifStr == null || classifStr.isEmpty()) ? 0 : Integer.parseInt(classifStr);
            livro.setClassificacao(classificacao);
        } catch (NumberFormatException e) {
            livro.setClassificacao(0);
        }

        try {
            // AQUI ESTAVA O PROBLEMA: Se qtdStr viesse nulo ou erro em outros campos, podia falhar
            // Agora garantimos que se for inválido, assume 1 (ou 0 se preferir)
            int quantidade = (qtdStr == null || qtdStr.isEmpty()) ? 0 : Integer.parseInt(qtdStr);
            livro.setQuantidade(quantidade);
        } catch (NumberFormatException e) {
            livro.setQuantidade(0); // Valor padrão seguro
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