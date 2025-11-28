/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.biblioteca.dao;

import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Penalidade;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PenalidadeDAO {

    public void registrar(Penalidade p) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO penalidades (usuario_id, emprestimo_id, data_aplicacao, dias_bloqueio, motivo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getUsuario().getId());
            stmt.setInt(2, p.getEmprestimo().getId());
            stmt.setDate(3, Date.valueOf(p.getDataAplicacao()));
            stmt.setInt(4, p.getDiasBloqueio());
            stmt.setString(5, p.getMotivo());
            stmt.executeUpdate();
        }
    }

    public void remover(int idPenalidade) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM penalidades WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPenalidade);
            stmt.executeUpdate();
        }
    }

    // Listar TODAS (Admin)
    public List<Penalidade> listarTodas() throws SQLException, ClassNotFoundException {
        return listarGenerico("SELECT p.*, u.nome as u_nome, l.titulo as l_titulo FROM penalidades p JOIN usuarios u ON p.usuario_id = u.id JOIN emprestimos e ON p.emprestimo_id = e.id JOIN livros l ON e.livro_id = l.id ORDER BY p.data_aplicacao DESC", null);
    }

    // Listar POR USUÁRIO (Leitor)
    public List<Penalidade> listarPorUsuario(int idUsuario) throws SQLException, ClassNotFoundException {
        return listarGenerico("SELECT p.*, u.nome as u_nome, l.titulo as l_titulo FROM penalidades p JOIN usuarios u ON p.usuario_id = u.id JOIN emprestimos e ON p.emprestimo_id = e.id JOIN livros l ON e.livro_id = l.id WHERE p.usuario_id = ? ORDER BY p.data_aplicacao DESC", idUsuario);
    }

    private List<Penalidade> listarGenerico(String sql, Integer idUsuario) throws SQLException, ClassNotFoundException {
        List<Penalidade> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (idUsuario != null) stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Penalidade p = new Penalidade();
                p.setId(rs.getInt("id"));
                p.setDataAplicacao(rs.getDate("data_aplicacao").toLocalDate());
                p.setDiasBloqueio(rs.getInt("dias_bloqueio"));
                p.setMotivo(rs.getString("motivo"));
                
                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_id"));
                u.setNome(rs.getString("u_nome"));
                p.setUsuario(u);
                
                // Empréstimo e Livro (Simplificado para exibição)
                Emprestimo e = new Emprestimo();
                e.setId(rs.getInt("emprestimo_id"));
                Livro l = new Livro();
                l.setTitulo(rs.getString("l_titulo"));
                e.setLivro(l);
                p.setEmprestimo(e);
                
                lista.add(p);
            }
        }
        return lista;
    }
}