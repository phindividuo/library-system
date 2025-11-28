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

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String perfil; // 'ADMIN' ou 'LEITOR'
    private LocalDate dataDesbloqueio; // Para penalidades

    // Construtor vazio (obrigatório para o JavaBeans)
    public Usuario() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
    public LocalDate getDataDesbloqueio() { return dataDesbloqueio; }
    public void setDataDesbloqueio(LocalDate dataDesbloqueio) { this.dataDesbloqueio = dataDesbloqueio; }
}
