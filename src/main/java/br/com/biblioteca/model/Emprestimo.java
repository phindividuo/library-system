/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phindividuo
 */

package br.com.biblioteca.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Emprestimo {
    private int id;
    private Usuario usuario; // Guardamos o objeto inteiro, não só o ID
    private Livro livro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    private String status; // ATIVO ou FINALIZADO

    public Emprestimo() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }

    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public LocalDate getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) { this.dataDevolucaoPrevista = dataDevolucaoPrevista; }

    public LocalDate getDataDevolucaoReal() { return dataDevolucaoReal; }
    public void setDataDevolucaoReal(LocalDate dataDevolucaoReal) { this.dataDevolucaoReal = dataDevolucaoReal; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    // Métodos auxiliares para o JSP usar e formatar datas
    public String getDataEmprestimoFormatada() {
        if (this.dataEmprestimo != null) {
            return this.dataEmprestimo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return "-";
    }

    public String getDataDevolucaoPrevistaFormatada() {
        if (this.dataDevolucaoPrevista != null) {
            return this.dataDevolucaoPrevista.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return "-";
    }
}