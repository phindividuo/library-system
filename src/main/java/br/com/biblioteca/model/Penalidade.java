/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.biblioteca.model;

import java.time.LocalDate;

public class Penalidade {
    private int id;
    private Usuario usuario;
    private Emprestimo emprestimo; // O empréstimo que gerou a multa
    private LocalDate dataAplicacao;
    private int diasBloqueio;
    private String motivo;

    public Penalidade() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Emprestimo getEmprestimo() { return emprestimo; }
    public void setEmprestimo(Emprestimo emprestimo) { this.emprestimo = emprestimo; }

    public LocalDate getDataAplicacao() { return dataAplicacao; }
    public void setDataAplicacao(LocalDate dataAplicacao) { this.dataAplicacao = dataAplicacao; }

    public int getDiasBloqueio() { return diasBloqueio; }
    public void setDiasBloqueio(int diasBloqueio) { this.diasBloqueio = diasBloqueio; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
