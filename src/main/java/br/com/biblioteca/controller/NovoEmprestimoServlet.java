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
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/novo-emprestimo")
public class NovoEmprestimoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Recupera a Sessão e o Usuário Logado
        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        // Segurança
        if (usuarioLogado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            UsuarioDAO usuarioDao = new UsuarioDAO();
            List<Usuario> usuariosParaEmprestimo;

            // 2. Define lista de usuários (Se for LEITOR, só vê a si mesmo)
            if ("ADMIN".equals(usuarioLogado.getPerfil())) {
                usuariosParaEmprestimo = usuarioDao.listar();
                // Remove o próprio admin da lista (opcional, regra de negócio)
                usuariosParaEmprestimo.removeIf(u -> u.getId() == usuarioLogado.getId());
            } else {
                // É LEITOR: Verifica se está bloqueado
                if (usuarioLogado.getDataDesbloqueio() != null && usuarioLogado.getDataDesbloqueio().isAfter(LocalDate.now())) {
                    // Se estiver bloqueado, nem carrega o formulário, manda erro direto ou avisa
                    request.setAttribute("mensagemErro", "Você está bloqueado de realizar empréstimos até " + 
                            usuarioLogado.getDataDesbloqueio().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
                usuariosParaEmprestimo = new ArrayList<>();
                usuariosParaEmprestimo.add(usuarioLogado);
            }

            LivroDAO livroDao = new LivroDAO();
            List<Livro> livros = livroDao.listar();

            // 3. Lógica de Pré-Seleção (FIXAR O LIVRO)
            String livroIdParam = request.getParameter("livroId");
            Livro livroAlvo = null;

            if (livroIdParam != null && !livroIdParam.isEmpty()) {
                try {
                    int id = Integer.parseInt(livroIdParam);
                    livroAlvo = livroDao.buscarPorId(id); // Busca o objeto completo para mostrar o Título
                } catch (Exception e) {
                    // ID inválido, ignora
                }
            }

            // 4. Envia tudo para o JSP
            request.setAttribute("listaUsuarios", usuariosParaEmprestimo);
            request.setAttribute("listaLivros", livros);
            request.setAttribute("livroAlvo", livroAlvo); // Objeto Livro específico (se veio da lista)

            request.getRequestDispatcher("novo-emprestimo.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erro ao carregar formulário de novo empréstimo", e);
        }
    }
}