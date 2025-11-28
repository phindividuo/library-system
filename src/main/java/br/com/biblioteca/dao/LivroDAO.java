/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phindividuo
 */

package br.com.biblioteca.dao;

import br.com.biblioteca.model.Livro;
import br.com.biblioteca.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    // ADICIONAR (Agora inclui nota_media com valor padrão 0)
    public void adicionar(Livro livro) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO livros (isbn, titulo, autor, editora, genero, num_paginas, ano_publicacao, classificacao, quantidade, nota_media) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, livro.getIsbn());
            stmt.setString(2, livro.getTitulo());
            stmt.setString(3, livro.getAutor());
            stmt.setString(4, livro.getEditora());
            stmt.setString(5, livro.getGenero());
            stmt.setInt(6, livro.getNumPaginas());
            stmt.setInt(7, livro.getAnoPublicacao());
            stmt.setInt(8, livro.getClassificacao());
            stmt.setInt(9, livro.getQuantidade());
            stmt.setDouble(10, 0.0); // Livro novo começa com média 0.0

            stmt.execute();
        }
    }

    // LISTAR TODOS
    public List<Livro> listar() throws SQLException, ClassNotFoundException {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT * FROM livros";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montarLivro(rs));
            }
        }
        return lista;
    }
    
    // BUSCAR POR ID
    public Livro buscarPorId(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM livros WHERE id = ?";
        Livro livro = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                livro = montarLivro(rs);
            }
        }
        return livro;
    }

    // ADICIONAR ESTOQUE
    public void adicionarEstoque(int idLivro, int quantidadeAdicional) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE livros SET quantidade = quantidade + ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, quantidadeAdicional);
            stmt.setInt(2, idLivro);
            stmt.executeUpdate();
        }
    }

    // Método auxiliar para evitar repetição de código
    private Livro montarLivro(ResultSet rs) throws SQLException {
        Livro l = new Livro();
        l.setId(rs.getInt("id"));
        l.setIsbn(rs.getString("isbn"));
        l.setTitulo(rs.getString("titulo"));
        l.setAutor(rs.getString("autor"));
        l.setEditora(rs.getString("editora"));
        l.setGenero(rs.getString("genero"));
        l.setNumPaginas(rs.getInt("num_paginas"));
        l.setAnoPublicacao(rs.getInt("ano_publicacao"));
        l.setClassificacao(rs.getInt("classificacao"));
        l.setQuantidade(rs.getInt("quantidade"));
        l.setNotaMedia(rs.getDouble("nota_media")); 
        return l;
    }
}