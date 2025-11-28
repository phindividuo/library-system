/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phindividuo
 */

package br.com.biblioteca.controller;

import br.com.biblioteca.dao.AvaliacaoDAO;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.model.Avaliacao;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/avaliar-livro")
public class AvaliarLivroServlet extends HttpServlet {

    // GET: Prepara a tela de avaliação
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
        if (usuarioLogado == null) { response.sendRedirect("login.jsp"); return; }

        try {
            int idLivro = Integer.parseInt(request.getParameter("id"));
            
            // Busca dados do livro
            LivroDAO livroDao = new LivroDAO();
            Livro livro = livroDao.buscarPorId(idLivro);
            
            // Busca se já existe avaliação anterior deste usuário
            AvaliacaoDAO avaliacaoDao = new AvaliacaoDAO();
            Avaliacao avaliacaoExistente = avaliacaoDao.buscarPorUsuarioELivro(usuarioLogado.getId(), idLivro);

            request.setAttribute("livro", livro);
            request.setAttribute("avaliacaoAnterior", avaliacaoExistente);
            
            request.getRequestDispatcher("avaliar-livro.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erro ao carregar avaliação", e);
        }
    }

    // POST: Salva a nota
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
        if (usuarioLogado == null) { response.sendRedirect("login.jsp"); return; }

        try {
            int idLivro = Integer.parseInt(request.getParameter("idLivro"));
            int nota = Integer.parseInt(request.getParameter("nota"));
            String comentario = request.getParameter("comentario");

            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setUsuario(usuarioLogado);
            
            Livro l = new Livro();
            l.setId(idLivro);
            avaliacao.setLivro(l);
            
            avaliacao.setNota(nota);
            avaliacao.setComentario(comentario);

            AvaliacaoDAO dao = new AvaliacaoDAO();
            dao.salvarAvaliacao(avaliacao);

            // Redireciona para "Meus Empréstimos" com mensagem
            response.sendRedirect("meus-emprestimos?msg=Avaliacao registrada com sucesso!");

        } catch (Exception e) {
            throw new ServletException("Erro ao salvar avaliação", e);
        }
    }
}