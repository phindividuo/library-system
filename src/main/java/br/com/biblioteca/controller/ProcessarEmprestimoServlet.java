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
import br.com.biblioteca.dao.LivroDAO; // Buscar o livro e verificar estoque
import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter; // Formatar a data na mensagem
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/processar-emprestimo")
public class ProcessarEmprestimoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Evitar os erros com acentos e Ç

        try {
            // Recebe os IDs do formulário
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            int idLivro = Integer.parseInt(request.getParameter("idLivro"));
            int dias = Integer.parseInt(request.getParameter("dias"));

            // Validar Estoque
            LivroDAO livroDao = new LivroDAO();
            Livro livroBanco = livroDao.buscarPorId(idLivro);

            if (livroBanco.getQuantidade() <= 0) {
                // Se não tem estoque
                request.setAttribute("mensagemErro", "Erro: O livro '" + livroBanco.getTitulo() + "' está sem estoque no momento!");
                
                // Devolve o usuário para a tela de novo empréstimo (recarregando as listas)
                request.getRequestDispatcher("novo-emprestimo").forward(request, response);
                return;
            }

            // Monta o objeto de Empréstimo
            Usuario u = new Usuario();
            u.setId(idUsuario);

            Livro l = new Livro();
            l.setId(idLivro);

            Emprestimo emp = new Emprestimo();
            emp.setUsuario(u);
            emp.setLivro(l);
            emp.setDataEmprestimo(LocalDate.now());
            emp.setDataDevolucaoPrevista(LocalDate.now().plusDays(dias));

            // Chama DAO para gravar
            EmprestimoDAO dao = new EmprestimoDAO();
            dao.registrarEmprestimo(emp);

            // Formatação da data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = emp.getDataDevolucaoPrevista().format(formatter);

            request.setAttribute("mensagem", "Empréstimo realizado! Devolução prevista: " + dataFormatada);
            request.getRequestDispatcher("sucesso.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erro ao processar empréstimo", e);
        }
    }
}