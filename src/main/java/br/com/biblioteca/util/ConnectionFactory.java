/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phindividuo
 */

package br.com.biblioteca.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
    // Configurações do Banco de Dados (bibliotecaDB)
    private static final String URL = "jdbc:derby://localhost:1527/bibliotecaDB";
    private static final String USUARIO = "usuario";
    private static final String SENHA = "biblioteca123";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Carrega o driver na memória (importante para Derby/Web)
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        
        // Retorna a conexão aberta
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
    
    // Método main apenas para testar se conecta
    public static void main(String[] args) {
        try {
            Connection conn = ConnectionFactory.getConnection();
            System.out.println("Conexão realizada com sucesso! Parabéns!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Falha na conexão. Verifique se o banco está rodando na aba Services.");
        }
    }
}
