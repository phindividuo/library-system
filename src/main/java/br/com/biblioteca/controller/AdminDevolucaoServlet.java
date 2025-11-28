/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.biblioteca.controller;

import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.PenalidadeDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.model.Penalidade;
import br.com.biblioteca.model.Usuario;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.temporal.ChronoUnit; // Importação para os dias de atraso

@WebServlet({"/admin-devolucoes", "/registrar-devolucao"})
public class AdminDevolucaoServlet extends HttpServlet {

    // GET: Lista os empréstimos ATIVOS para o Admin selecionar e devolver
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Segurança: Apenas Admin
        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
        if (usuarioLogado == null || !"ADMIN".equals(usuarioLogado.getPerfil())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String acao = request.getServletPath();

        try {
            if (acao.equals("/admin-devolucoes")) {
                // LISTAR ATIVOS
                EmprestimoDAO dao = new EmprestimoDAO();
                List<Emprestimo> ativos = dao.listarAtivos();
                request.setAttribute("listaAtivos", ativos);
                request.getRequestDispatcher("admin-devolucoes.jsp").forward(request, response);
                
            } else if (acao.equals("/registrar-devolucao")) {
                // PROCESSAR DEVOLUÇÃO (Chamado pelo botão)
                processarDevolucao(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Erro no módulo de devoluções", e);
        }
    }

    private void processarDevolucao(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int idEmprestimo = Integer.parseInt(request.getParameter("id"));
        
        EmprestimoDAO empDao = new EmprestimoDAO();
        Emprestimo emp = empDao.buscarPorId(idEmprestimo);
        
        if (emp == null || !"ATIVO".equals(emp.getStatus())) {
            // CORREÇÃO: Mensagem de erro codificada em UTF-8
            String erroMsg = "Empréstimo inválido ou já finalizado";
            response.sendRedirect("admin-devolucoes?erro=" + URLEncoder.encode(erroMsg, StandardCharsets.UTF_8));
            return;
        }

        // 1. Efetiva a devolução no banco (Data Real e Estoque)
        empDao.registrarDevolucao(idEmprestimo, emp.getLivro().getId());
        
        // 2. Verifica Atraso
        LocalDate hoje = LocalDate.now();
        String msg = "Entrega do livro registrada com sucesso!";
        
        if (hoje.isAfter(emp.getDataDevolucaoPrevista())) {
            // APLICA PENALIDADE
            long diasAtraso = ChronoUnit.DAYS.between(emp.getDataDevolucaoPrevista(), hoje);
            int diasBloqueio = 90; // Regra fixa solicitada
            LocalDate dataDesbloqueio = hoje.plusDays(diasBloqueio);
            
            // Grava Histórico Penalidade
            Penalidade pen = new Penalidade();
            pen.setUsuario(emp.getUsuario());
            pen.setEmprestimo(emp);
            pen.setDataAplicacao(hoje);
            pen.setDiasBloqueio(diasBloqueio);
            pen.setMotivo("Atraso de " + diasAtraso + " dias na devolução do livro '" + emp.getLivro().getTitulo() + "'. Previsto: " + emp.getDataDevolucaoPrevistaFormatada());
            
            PenalidadeDAO penDao = new PenalidadeDAO();
            penDao.registrar(pen);
            
            // Bloqueia Usuário
            UsuarioDAO userDao = new UsuarioDAO();
            userDao.bloquearUsuario(emp.getUsuario().getId(), dataDesbloqueio);
            
            msg = "ATENÇÃO: Atraso! Usuario bloqueado por 90 dias.";
        }
        
        // CORREÇÃO: Mensagem de sucesso/alerta codificada em UTF-8
        response.sendRedirect("admin-devolucoes?msg=" + URLEncoder.encode(msg, StandardCharsets.UTF_8));
    }
}