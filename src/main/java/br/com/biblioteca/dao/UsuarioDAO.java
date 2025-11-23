/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phindividuo
 */

package br.com.biblioteca.dao;

import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Método para Adicionar (CREATE)
    public void adicionar(Usuario usuario) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";

        // Abre a conexão e prepara o SQL
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Substitui os '?' pelos dados do objeto
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());

            // Executa
            stmt.execute();
        }
    } 

    // Método para Listar (READ)
    public List<Usuario> listar() throws SQLException, ClassNotFoundException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                // u.setSenha(rs.getString("senha")); // Opcional retornar a senha
                
                lista.add(u);
            }
        }
        return lista;
    }

}