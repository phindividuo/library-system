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
import java.sql.Date; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Método para Adicionar (CREATE)
    public void adicionar(Usuario usuario) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO usuarios (nome, email, senha, perfil) VALUES (?, ?, ?, ?)";

        // Abre a conexão e prepara o SQL
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Substitui os '?' pelos dados do objeto
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, "LEITOR"); // Todo cadastro público é LEITOR por segurança

            // Executa
            stmt.execute();
        }
    } 

    public Usuario autenticar(String email, String senha) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        Usuario usuarioEncontrado = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            stmt.setString(2, senha);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuarioEncontrado = new Usuario();
                usuarioEncontrado.setId(rs.getInt("id"));
                usuarioEncontrado.setNome(rs.getString("nome"));
                usuarioEncontrado.setEmail(rs.getString("email"));
                usuarioEncontrado.setPerfil(rs.getString("perfil")); // Controle de acesso
                
                Date dataBloq = rs.getDate("data_desbloqueio");
                if (dataBloq != null) {
                    usuarioEncontrado.setDataDesbloqueio(dataBloq.toLocalDate());
                }
            }
        }
        return usuarioEncontrado; // Retorna null se não achar
    }

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
                u.setPerfil(rs.getString("perfil")); // Incluindo perfil na listagem
                lista.add(u);
            }
        }
        return lista;
    }
}