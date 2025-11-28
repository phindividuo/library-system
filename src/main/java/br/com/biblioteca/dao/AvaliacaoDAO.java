/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.biblioteca.dao;

import br.com.biblioteca.model.Avaliacao;
import br.com.biblioteca.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AvaliacaoDAO {

    // Salva a avaliação e atualiza a média do livro (Transação)
    public void salvarAvaliacao(Avaliacao avaliacao) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false); // Início da Transação

            // 1. Verifica se já existe avaliação desse usuário para esse livro
            String sqlCheck = "SELECT id FROM avaliacoes WHERE usuario_id = ? AND livro_id = ?";
            boolean existe = false;
            try (PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
                stmtCheck.setInt(1, avaliacao.getUsuario().getId());
                stmtCheck.setInt(2, avaliacao.getLivro().getId());
                if (stmtCheck.executeQuery().next()) {
                    existe = true;
                }
            }

            // 2. Insere ou Atualiza a Avaliação
            String sqlAcao;
            if (existe) {
                // Atualiza a nota existente
                sqlAcao = "UPDATE avaliacoes SET nota = ?, comentario = ? WHERE usuario_id = ? AND livro_id = ?";
            } else {
                // Insere nova nota
                sqlAcao = "INSERT INTO avaliacoes (nota, comentario, usuario_id, livro_id) VALUES (?, ?, ?, ?)";
            }

            try (PreparedStatement stmtAcao = conn.prepareStatement(sqlAcao)) {
                stmtAcao.setInt(1, avaliacao.getNota());
                stmtAcao.setString(2, avaliacao.getComentario());
                stmtAcao.setInt(3, avaliacao.getUsuario().getId());
                stmtAcao.setInt(4, avaliacao.getLivro().getId());
                stmtAcao.executeUpdate();
            }

            // 3. Recalcula a Média do Livro (Agora forçando DOUBLE para precisão)
            // COALESCE(..., 0.0) garante que se não houver notas (improvável aqui), retorne 0.0
            String sqlMedia = "SELECT COALESCE(AVG(CAST(nota AS DOUBLE)), 0.0) FROM avaliacoes WHERE livro_id = ?";
            double novaMedia = 0.0;
            try (PreparedStatement stmtMedia = conn.prepareStatement(sqlMedia)) {
                stmtMedia.setInt(1, avaliacao.getLivro().getId());
                ResultSet rs = stmtMedia.executeQuery();
                if (rs.next()) {
                    novaMedia = rs.getDouble(1);
                }
            }

            // 4. Atualiza a tabela Livros com a nova média calculada
            String sqlUpdateLivro = "UPDATE livros SET nota_media = ? WHERE id = ?";
            try (PreparedStatement stmtLivro = conn.prepareStatement(sqlUpdateLivro)) {
                stmtLivro.setDouble(1, novaMedia);
                stmtLivro.setInt(2, avaliacao.getLivro().getId());
                stmtLivro.executeUpdate();
            }

            conn.commit(); // Confirma tudo

        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            throw new SQLException("Erro ao salvar avaliação e atualizar média", e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
    
    // Busca avaliação existente (para preencher o formulário)
    public Avaliacao buscarPorUsuarioELivro(int idUsuario, int idLivro) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM avaliacoes WHERE usuario_id = ? AND livro_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idLivro);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Avaliacao a = new Avaliacao();
                a.setId(rs.getInt("id"));
                a.setNota(rs.getInt("nota"));
                a.setComentario(rs.getString("comentario"));
                return a;
            }
        }
        return null;
    }
    
    // Método Listar (Mantido para a tela de listagem de opiniões)
    public java.util.List<br.com.biblioteca.model.Avaliacao> listar(Integer idLivroFiltro) throws SQLException, ClassNotFoundException {
        java.util.List<br.com.biblioteca.model.Avaliacao> lista = new java.util.ArrayList<>();
        String sql = "SELECT a.*, u.nome as u_nome, l.titulo as l_titulo FROM avaliacoes a JOIN usuarios u ON a.usuario_id = u.id JOIN livros l ON a.livro_id = l.id ";
        if (idLivroFiltro != null) sql += "WHERE a.livro_id = ? ";
        sql += "ORDER BY a.id DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (idLivroFiltro != null) stmt.setInt(1, idLivroFiltro);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                br.com.biblioteca.model.Avaliacao a = new br.com.biblioteca.model.Avaliacao();
                a.setId(rs.getInt("id"));
                a.setNota(rs.getInt("nota"));
                a.setComentario(rs.getString("comentario"));
                br.com.biblioteca.model.Usuario u = new br.com.biblioteca.model.Usuario();
                u.setId(rs.getInt("usuario_id"));
                u.setNome(rs.getString("u_nome"));
                a.setUsuario(u);
                br.com.biblioteca.model.Livro l = new br.com.biblioteca.model.Livro();
                l.setId(rs.getInt("livro_id"));
                l.setTitulo(rs.getString("l_titulo"));
                a.setLivro(l);
                lista.add(a);
            }
        }
        return lista;
    }
}