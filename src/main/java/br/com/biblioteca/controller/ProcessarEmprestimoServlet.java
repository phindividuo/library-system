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
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/processar-emprestimo")
public class ProcessarEmprestimoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Garante que a entrada esteja correta

        try {
            // --- 1. SEGURANÇA ---
            HttpSession session = request.getSession(false);
            Usuario usuarioLogado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
            
            if (usuarioLogado == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // --- 2. RECEBE DADOS ---
            int idUsuarioAlvo = Integer.parseInt(request.getParameter("idUsuario"));
            int idLivro = Integer.parseInt(request.getParameter("idLivro"));
            int dias = Integer.parseInt(request.getParameter("dias"));

            LivroDAO livroDao = new LivroDAO();
            EmprestimoDAO emprestimoDao = new EmprestimoDAO();

            // --- 3. VALIDAÇÕES DE REGRA DE NEGÓCIO ---

            // Regra A: Estoque
            Livro livroBanco = livroDao.buscarPorId(idLivro);
            if (livroBanco.getQuantidade() <= 0) {
                enviarErro(request, response, "Erro: O livro '" + livroBanco.getTitulo() + "' está sem estoque no momento!", idLivro, usuarioLogado);
                return;
            }

            // Regra B: Limite de 3 Empréstimos Ativos
            int qtdAtivos = emprestimoDao.contarEmprestimosAtivos(idUsuarioAlvo);
            if (qtdAtivos >= 3) {
                enviarErro(request, response, "Erro: Limite atingido. O usuário já possui 3 empréstimos ativos.", idLivro, usuarioLogado);
                return;
            }

            // Regra C: Duplicidade
            if (emprestimoDao.jaPossuiLivro(idUsuarioAlvo, idLivro)) {
                enviarErro(request, response, "Erro: O usuário já possui um exemplar ativo deste livro.", idLivro, usuarioLogado);
                return;
            }

            // Regra D: Bloqueio por Penalidade
            if (usuarioLogado.getId() == idUsuarioAlvo && usuarioLogado.getDataDesbloqueio() != null) {
                if (usuarioLogado.getDataDesbloqueio().isAfter(LocalDate.now())) {
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    enviarErro(request, response, "Erro: Usuário bloqueado até " + usuarioLogado.getDataDesbloqueio().format(fmt), idLivro, usuarioLogado);
                    return;
                }
            }

            // --- 4. SUCESSO ---
            Usuario u = new Usuario();
            u.setId(idUsuarioAlvo);

            Livro l = new Livro();
            l.setId(idLivro);

            Emprestimo emp = new Emprestimo();
            emp.setUsuario(u);
            emp.setLivro(l);
            emp.setDataEmprestimo(LocalDate.now());
            emp.setDataDevolucaoPrevista(LocalDate.now().plusDays(dias));

            emprestimoDao.registrarEmprestimo(emp);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = emp.getDataDevolucaoPrevista().format(formatter);

            request.setAttribute("mensagem", "Empréstimo realizado com sucesso! Devolução prevista: " + dataFormatada);
            request.getRequestDispatcher("sucesso.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erro crítico ao processar empréstimo", e);
        }
    }

    // Método auxiliar REFORMULADO: Usa FORWARD em vez de Redirect para evitar erro de encoding
    // Precisamos recarregar as listas (livros/usuários) aqui porque o forward não volta para o NovoEmprestimoServlet
    private void enviarErro(HttpServletRequest request, HttpServletResponse response, String msg, int idLivro, Usuario usuarioLogado) throws ServletException, IOException {
        try {
            // 1. Define a mensagem de erro no request (UTF-8 garantido)
            request.setAttribute("mensagemErro", msg);
            
            // 2. Recarrega as listas necessárias para o JSP (exatamente como no NovoEmprestimoServlet)
            UsuarioDAO usuarioDao = new UsuarioDAO();
            List<Usuario> usuarios;

            if ("ADMIN".equals(usuarioLogado.getPerfil())) {
                usuarios = usuarioDao.listar();
                usuarios.removeIf(u -> u.getId() == usuarioLogado.getId());
            } else {
                usuarios = new ArrayList<>();
                usuarios.add(usuarioLogado);
            }

            LivroDAO livroDao = new LivroDAO();
            List<Livro> livros = livroDao.listar();
            
            // Busca o livro alvo para manter travado
            Livro livroAlvo = livroDao.buscarPorId(idLivro);

            // 3. Configura atributos para o JSP
            request.setAttribute("listaUsuarios", usuarios);
            request.setAttribute("listaLivros", livros);
            request.setAttribute("livroAlvo", livroAlvo);

            // 4. Encaminha internamente (mantém URL limpa e dados seguros)
            request.getRequestDispatcher("novo-emprestimo.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erro ao recarregar formulário de erro", e);
        }
    }
}