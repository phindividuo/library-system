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

@WebServlet("/adicionar-estoque")
public class AdicionarEstoqueServlet extends HttpServlet {

    // Método GET: Prepara a tela (Carrega o livro e mostra o formulário)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            LivroDAO dao = new LivroDAO();
            Livro livro = dao.buscarPorId(id);

            request.setAttribute("livro", livro);
            request.getRequestDispatcher("adicionar-estoque.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erro ao carregar livro para estoque", e);
        }
    }

    // Método POST: Processa o formulário (Salva no banco)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int qtdAdicional = Integer.parseInt(request.getParameter("novaQuantidade"));
            String titulo = request.getParameter("titulo"); // Só para mensagem

            LivroDAO dao = new LivroDAO();
            dao.adicionarEstoque(id, qtdAdicional);

            request.setAttribute("mensagem", "Estoque do livro atualizado com sucesso! (+ " + qtdAdicional + " unidades)");
            request.getRequestDispatcher("sucesso.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erro ao atualizar estoque", e);
        }
    }
}
