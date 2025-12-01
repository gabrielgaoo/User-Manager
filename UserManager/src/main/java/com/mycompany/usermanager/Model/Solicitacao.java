/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.usermanager.Model;

/**
 *
 * @author Lenovo
 */
public class Solicitacao {
            private Usuario usuario;
            private IAprovadorSolicitacao solicita;
            
            public Solicitacao(Usuario usuario) {
                this.usuario = usuario;
            }
            public void  enviarSolicitacao(Usuario usuario){
                  if(usuario.getStatus().PENDENTE.equals(Status.PENDENTE)){  
                  solicita.aprovarSolicitacao();
                  }
            
            }                
            
}
