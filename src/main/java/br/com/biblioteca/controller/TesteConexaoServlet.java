/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phindividuo
 */

package br.com.biblioteca.controller;

import br.com.biblioteca.util.ConnectionFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/teste-conexao")
public class TesteConexaoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><body>");
            out.println("<h1>Teste de Conexão</h1>");
            
            try {
                Connection conn = ConnectionFactory.getConnection();
                out.println("<h2 style='color:green'>SUCESSO! Conectado ao Banco Derby.</h2>");
                conn.close();
            } catch (Exception e) {
                out.println("<h2 style='color:red'>ERRO: " + e.getMessage() + "</h2>");
                e.printStackTrace(out); // Mostra o erro na tela
            }
            
            out.println("</body></html>");
        }
    }
}