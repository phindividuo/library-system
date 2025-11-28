/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.biblioteca.controller;

import br.com.biblioteca.dao.PenalidadeDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.model.Penalidade;
import br.com.biblioteca.model.Usuario;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet({"/admin-penalidades", "/minhas-penalidades", "/remover-penalidade"})
public class GerenciarPenalidadesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
        if (usuarioLogado == null) { response.sendRedirect("login.jsp"); return; }

        String acao = request.getServletPath();
        PenalidadeDAO dao = new PenalidadeDAO();

        try {
            if (acao.equals("/admin-penalidades")) {
                // ADMIN: Vê tudo
                if (!"ADMIN".equals(usuarioLogado.getPerfil())) { response.sendRedirect("dashboard"); return; }
                
                List<Penalidade> lista = dao.listarTodas();
                request.setAttribute("listaPenalidades", lista);
                request.getRequestDispatcher("admin-penalidades.jsp").forward(request, response);
                
            } else if (acao.equals("/minhas-penalidades")) {
                // LEITOR: Vê as suas
                List<Penalidade> lista = dao.listarPorUsuario(usuarioLogado.getId());
                request.setAttribute("listaPenalidades", lista);
                request.getRequestDispatcher("minhas-penalidades.jsp").forward(request, response);
                
            } else if (acao.equals("/remover-penalidade")) {
                // ADMIN: Remove punição
                if (!"ADMIN".equals(usuarioLogado.getPerfil())) return;
                
                int idPenalidade = Integer.parseInt(request.getParameter("id"));
                int idUsuarioAlvo = Integer.parseInt(request.getParameter("idUsuario"));
                
                // Remove histórico
                dao.remover(idPenalidade);
                // Desbloqueia usuário
                UsuarioDAO userDao = new UsuarioDAO();
                userDao.desbloquearUsuario(idUsuarioAlvo);
                
                response.sendRedirect("admin-penalidades?msg=Penalidade removida e usuario desbloqueado.");
            }
            
        } catch (Exception e) {
            throw new ServletException("Erro em penalidades", e);
        }
    }
}
