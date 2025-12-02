/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.usermanager.Model;

import java.time.LocalDateTime;

/**
 *
 * @author Lenovo
 */
public class Usuario {
    private Integer id;
    private String nomeCompleto;
    private String nomeUsuario;
    private String senha;
    private boolean isAdministrador;
    private boolean isPrimeiroAdmin;
    private LocalDateTime dataCadastro;
    private boolean autorizado;
    
    public Usuario(String nomeCompleto, String nomeUsuario, String senha, boolean isAdministrador, boolean isPrimeiroAdmin) {
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.isAdministrador = isAdministrador;
        this.isPrimeiroAdmin = isPrimeiroAdmin;
        this.dataCadastro = LocalDateTime.now();
        this.autorizado = isPrimeiroAdmin;
    }
    
    public Usuario(Integer id, String nomeCompleto, String nomeUsuario, String senha, 
                   boolean isAdministrador, boolean isPrimeiroAdmin, 
                   LocalDateTime dataCadastro, boolean autorizado) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.isAdministrador = isAdministrador;
        this.isPrimeiroAdmin = isPrimeiroAdmin;
        this.dataCadastro = dataCadastro;
        this.autorizado = autorizado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAdministrador() {
        return isAdministrador;
    }

    public void setAdministrador(boolean administrador) {
        isAdministrador = administrador;
    }

    public boolean isPrimeiroAdmin() {
        return isPrimeiroAdmin;
    }

    public void setPrimeiroAdmin(boolean primeiroAdmin) {
        isPrimeiroAdmin = primeiroAdmin;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isAutorizado() {
        return autorizado;
    }

    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }
    
     public boolean validaUsuarioAtivo() {
        return this.autorizado;
    }
     
    public boolean validaUsuarioPendente() {
        return !this.autorizado && !this.isPrimeiroAdmin;
    }

            
}
