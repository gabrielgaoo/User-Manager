/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.usermanager.Model;

/**
 *
 * @author Lenovo
 */
public class Usuario {
            private String nome;
            private String senha;
            private Status status;
            
            public Usuario(String nome, String senha,Status status){
                        this.nome = nome;
                        this.senha = senha;            
                        this.status = status.PENDENTE;
            }

            public String getNome() {
                return nome;
            }

            public String getSenha() {
                return senha;
            }     

            public Status getStatus() {
                return status;
            }
            
}
