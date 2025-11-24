/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phindividuo
 */

package br.com.biblioteca.dao;

import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

    public void registrarEmprestimo(Emprestimo emprestimo) throws SQLException, ClassNotFoundException {
        String sqlInsert = "INSERT INTO emprestimos (usuario_id, livro_id, data_emprestimo, data_devolucao_prevista, status) VALUES (?, ?, ?, ?, ?)";
        String sqlUpdateLivro = "UPDATE livros SET quantidade = quantidade - 1 WHERE id = ?";

        Connection conn = null;

        try {
            conn = ConnectionFactory.getConnection();
            
            // Início da Transação
            conn.setAutoCommit(false); // Desliga o salvamento automático

            // Registra o empréstimo
            try (PreparedStatement stmt1 = conn.prepareStatement(sqlInsert)) {
                stmt1.setInt(1, emprestimo.getUsuario().getId());
                stmt1.setInt(2, emprestimo.getLivro().getId());
                stmt1.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
                stmt1.setDate(4, Date.valueOf(emprestimo.getDataDevolucaoPrevista()));
                stmt1.setString(5, "ATIVO");
                stmt1.executeUpdate();
            }

            // Diminui o estoque do livro (-1)
            try (PreparedStatement stmt2 = conn.prepareStatement(sqlUpdateLivro)) {
                stmt2.setInt(1, emprestimo.getLivro().getId());
                stmt2.executeUpdate();
            }

            // Se funcionar, commita no BD
            conn.commit();
            // Fim da Transação

        } catch (Exception e) {
            // Se deu erro, desfaz tudo feito com o rollback
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new SQLException("Erro ao realizar empréstimo", e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true); // Volta ao normal
                conn.close();
            }
        }
    }

    public List<Emprestimo> listar() throws SQLException, ClassNotFoundException {
        List<Emprestimo> lista = new ArrayList<>();
        
        // SQL com JOIN para trazer o Nome do Usuário e o Título do Livro
        String sql = "SELECT e.*, u.nome as usuario_nome, u.email as usuario_email, l.titulo as livro_titulo " +
                     "FROM emprestimos e " +
                     "JOIN usuarios u ON e.usuario_id = u.id " +
                     "JOIN livros l ON e.livro_id = l.id " +
                     "ORDER BY e.data_emprestimo DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Emprestimo emp = new Emprestimo();
                emp.setId(rs.getInt("id"));
                
                // Convertendo datas do Banco (java.sql.Date) para Java (LocalDate)
                emp.setDataEmprestimo(rs.getDate("data_emprestimo").toLocalDate());
                emp.setDataDevolucaoPrevista(rs.getDate("data_devolucao_prevista").toLocalDate());
                
                // Tratamento para Data de Devolução Real (pode ser nula se ainda não devolveu)
                Date dataReal = rs.getDate("data_devolucao_real");
                if (dataReal != null) {
                    emp.setDataDevolucaoReal(dataReal.toLocalDate());
                }
                
                emp.setStatus(rs.getString("status"));

                // Preenchendo Usuário (parcialmente, só com o que veio no JOIN)
                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_id"));
                u.setNome(rs.getString("usuario_nome"));
                u.setEmail(rs.getString("usuario_email"));
                emp.setUsuario(u);

                // Preenchendo Livro Parcialmente
                Livro l = new Livro();
                l.setId(rs.getInt("livro_id"));
                l.setTitulo(rs.getString("livro_titulo"));
                emp.setLivro(l);

                lista.add(emp);
            }
        }
        return lista;
    }
}