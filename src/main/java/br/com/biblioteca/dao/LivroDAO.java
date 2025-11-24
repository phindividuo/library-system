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

    // ADICIONAR LIVRO
    public void adicionar(Livro livro) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO livros (isbn, titulo, autor, editora, genero, num_paginas, ano_publicacao, classificacao, quantidade) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

            stmt.execute();
        }
    }

    // LISTAR LIVROS
    public List<Livro> listar() throws SQLException, ClassNotFoundException {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT * FROM livros";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
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
                
                lista.add(l);
            }
        }
        return lista;
    }
    
    // Buscar um livro específico pelo ID
    public Livro buscarPorId(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM livros WHERE id = ?";
        Livro l = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                l = new Livro();
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
            }
        }
        return l;
    }

    // Aumentar o estoque
    public void adicionarEstoque(int idLivro, int quantidadeAdicional) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE livros SET quantidade = quantidade + ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, quantidadeAdicional);
            stmt.setInt(2, idLivro);
            
            stmt.executeUpdate();
        }
    }
}
