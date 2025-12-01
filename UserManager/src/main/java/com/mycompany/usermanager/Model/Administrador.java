/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.usermanager.Model;

/**
 *
 * @author Lenovo
 */
public class Administrador implements IAutenticador, IAprovadorSolicitacao, INotificador {
            private String nome;
            private String senha;
                        
            public Administrador(String nome, String senha) {
                this.nome = nome;
                this.senha = senha;
            }

            @Override
            public void aprovarSolicitacao() {

            }

            @Override
            public void rejeitarSolicitacao() {

            }

            @Override
            public void enviaNotificacao() {

            }

}
