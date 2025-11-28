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
import java.time.LocalDate;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

    // REGISTRAR (Admin ou Leitor faz empréstimo)
    public void registrarEmprestimo(Emprestimo emprestimo) throws SQLException, ClassNotFoundException {
        // Agora incluímos 'foi_renovado' com padrão false
        String sqlInsert = "INSERT INTO emprestimos (usuario_id, livro_id, data_emprestimo, data_devolucao_prevista, status, foi_renovado) VALUES (?, ?, ?, ?, ?, FALSE)";
        String sqlUpdateLivro = "UPDATE livros SET quantidade = quantidade - 1 WHERE id = ?";

        Connection conn = null;

        try {
            conn = ConnectionFactory.getConnection();
            
            // Início da Transação
            conn.setAutoCommit(false); 

            // Inserir o Empréstimo
            try (PreparedStatement stmt1 = conn.prepareStatement(sqlInsert)) {
                stmt1.setInt(1, emprestimo.getUsuario().getId());
                stmt1.setInt(2, emprestimo.getLivro().getId());
                stmt1.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
                stmt1.setDate(4, Date.valueOf(emprestimo.getDataDevolucaoPrevista()));
                stmt1.setString(5, "ATIVO");
                stmt1.executeUpdate();
            }

            // Diminuir Estoque
            try (PreparedStatement stmt2 = conn.prepareStatement(sqlUpdateLivro)) {
                stmt2.setInt(1, emprestimo.getLivro().getId());
                stmt2.executeUpdate();
            }

            conn.commit(); // Commita

        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            throw new SQLException("Erro ao realizar empréstimo", e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // RENOVAR (Apenas Leitor)
    public boolean renovar(int idEmprestimo) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            
            // Verifica se pode renovar e pega a data atual
            String sqlSelect = "SELECT data_devolucao_prevista FROM emprestimos WHERE id = ? AND foi_renovado = FALSE AND status = 'ATIVO'";
            
            LocalDate dataAtual = null;
            
            try (PreparedStatement stmt = conn.prepareStatement(sqlSelect)) {
                stmt.setInt(1, idEmprestimo);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    Date d = rs.getDate("data_devolucao_prevista");
                    if (d != null) {
                        dataAtual = d.toLocalDate();
                    }
                }
            }
            
            // Se não achou data (ou seja, empréstimo não existe, já foi renovado, não está ativo ou qualquer outro motivo)
            if (dataAtual == null) {
                return false;
            }

            // Calcula a nova data no Java com o LOCALDATE (+7 dias)
            LocalDate novaData = dataAtual.plusDays(7);

            // Atualiza no banco
            String sqlUpdate = "UPDATE emprestimos SET data_devolucao_prevista = ?, foi_renovado = TRUE WHERE id = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {
                stmt.setDate(1, Date.valueOf(novaData)); // Converte LocalDate para SQL Date
                stmt.setInt(2, idEmprestimo);
                
                int linhas = stmt.executeUpdate();
                return linhas > 0;
            }
            
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    // LISTAR TODOS (Para o Admin)
    public List<Emprestimo> listar() throws SQLException, ClassNotFoundException {
        return listarGenerico("SELECT e.*, u.nome as u_nome, u.email as u_email, l.titulo as l_titulo, l.autor as l_autor FROM emprestimos e JOIN usuarios u ON e.usuario_id = u.id JOIN livros l ON e.livro_id = l.id ORDER BY e.data_emprestimo DESC", null);
    }

    // LISTAR POR USUÁRIO (Para o Leitor "Meus Empréstimos")
    public List<Emprestimo> listarPorUsuario(int idUsuario) throws SQLException, ClassNotFoundException {
        return listarGenerico("SELECT e.*, u.nome as u_nome, u.email as u_email, l.titulo as l_titulo, l.autor as l_autor FROM emprestimos e JOIN usuarios u ON e.usuario_id = u.id JOIN livros l ON e.livro_id = l.id WHERE e.usuario_id = ? ORDER BY e.data_emprestimo DESC", idUsuario);
    }

    // Método auxiliar privado para evitar repetição de código no listar
    private List<Emprestimo> listarGenerico(String sql, Integer idFiltro) throws SQLException, ClassNotFoundException {
        List<Emprestimo> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (idFiltro != null) {
                stmt.setInt(1, idFiltro);
            }
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Emprestimo emp = new Emprestimo();
                emp.setId(rs.getInt("id"));
                emp.setDataEmprestimo(rs.getDate("data_emprestimo").toLocalDate());
                emp.setDataDevolucaoPrevista(rs.getDate("data_devolucao_prevista").toLocalDate());
                
                Date dataReal = rs.getDate("data_devolucao_real");
                if (dataReal != null) emp.setDataDevolucaoReal(dataReal.toLocalDate());
                
                emp.setStatus(rs.getString("status"));
                // O campo 'foi_renovado' no banco é booleano
                // Precisaríamos adicionar esse campo no Modelo Emprestimo se quisermos mostrar na tela (não será mais feito)

                // Usuario Parcial
                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_id"));
                u.setNome(rs.getString("u_nome"));
                u.setEmail(rs.getString("u_email"));
                emp.setUsuario(u);

                // Livro Parcial
                Livro l = new Livro();
                l.setId(rs.getInt("livro_id"));
                l.setTitulo(rs.getString("l_titulo"));
                l.setAutor(rs.getString("l_autor"));
                emp.setLivro(l);

                lista.add(emp);
            }
        }
        return lista;
    }
    
    // CONTA QUANTOS EMPRÉSTIMOS ATIVOS O USUÁRIO TEM
    public int contarEmprestimosAtivos(int idUsuario) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM emprestimos WHERE usuario_id = ? AND status = 'ATIVO'";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // VERIFICA SE O USUÁRIO JÁ TEM ESSE LIVRO ATIVO
    public boolean jaPossuiLivro(int idUsuario, int idLivro) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM emprestimos WHERE usuario_id = ? AND livro_id = ? AND status = 'ATIVO'";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idLivro);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    
    // Buscar empréstimo por ID (Para cálculo de multas)
    public Emprestimo buscarPorId(int id) throws SQLException, ClassNotFoundException {
        // Reutiliza a lógica do listarGenerico filtrando por ID específico
        List<Emprestimo> lista = listarGenerico("SELECT e.*, u.nome as u_nome, u.email as u_email, l.titulo as l_titulo, l.autor as l_autor FROM emprestimos e JOIN usuarios u ON e.usuario_id = u.id JOIN livros l ON e.livro_id = l.id WHERE e.id = ?", id);
        return lista.isEmpty() ? null : lista.get(0);
    }

    // Registrar Devolução (Fecha empréstimo e repõe estoque)
    public void registrarDevolucao(int idEmprestimo, int idLivro) throws SQLException, ClassNotFoundException {
        String sqlUpdateEmprestimo = "UPDATE emprestimos SET data_devolucao_real = ?, status = 'FINALIZADO' WHERE id = ?";
        String sqlUpdateLivro = "UPDATE livros SET quantidade = quantidade + 1 WHERE id = ?";

        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false); // Transação

            // Atualiza Empréstimo (Data de hoje e Status)
            try (PreparedStatement stmt1 = conn.prepareStatement(sqlUpdateEmprestimo)) {
                stmt1.setDate(1, Date.valueOf(java.time.LocalDate.now())); // Hoje
                stmt1.setInt(2, idEmprestimo);
                stmt1.executeUpdate();
            }

            // Repõe Estoque do Livro (+1)
            try (PreparedStatement stmt2 = conn.prepareStatement(sqlUpdateLivro)) {
                stmt2.setInt(1, idLivro);
                stmt2.executeUpdate();
            }

            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            throw new SQLException("Erro ao registrar devolução", e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
    
    // Listar APENAS ATIVOS (Para a tela de devolução do Admin ficar mais limpa)
    public List<Emprestimo> listarAtivos() throws SQLException, ClassNotFoundException {
        return listarGenerico("SELECT e.*, u.nome as u_nome, u.email as u_email, l.titulo as l_titulo, l.autor as l_autor FROM emprestimos e JOIN usuarios u ON e.usuario_id = u.id JOIN livros l ON e.livro_id = l.id WHERE e.status = 'ATIVO' ORDER BY e.data_devolucao_prevista ASC", null);
    }
}
