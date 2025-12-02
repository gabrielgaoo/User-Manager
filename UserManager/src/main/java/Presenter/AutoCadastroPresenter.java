/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presenter;

import Repository.UsuarioRepository;
import com.mycompany.usermanager.Model.Usuario;
import com.mycompany.usermanager.View.AutoCadastroView;


public class AutoCadastroPresenter {
      private final AutoCadastroView view;
    private final UsuarioRepository usuarioRepository;

    public AutoCadastroPresenter() {
        this.view = new AutoCadastroView();
        this.usuarioRepository = new UsuarioRepository();
        inicializar();
    }

    private void inicializar() {
        view.adicionarListenerBotaoCadastrar(e -> realizarAutocadastro());
        view.adicionarListenerBotaoCancelar(e -> cancelar());
        view.setVisible(true);
    }

    private void realizarAutocadastro() {
        String nomeCompleto = view.getTxtNomeCompleto().getText().trim();
        String nomeUsuario = view.getTxtNomeUsuario().getText().trim();        
        String senha = view.getTxtSenha().getText().trim();
        String confirmacaoSenha = view.getTxtConfirmaSenha().getText().trim();

        if (nomeCompleto.isEmpty()) {
            view.exibirMensagemErro("Informe o nome completo");
            return;
        }

        if (nomeUsuario.isEmpty()) {
            view.exibirMensagemErro("Informe o nome de usuário");
            return;
        }

        try {
            if (usuarioRepository.buscarPorNomeUsuario(nomeUsuario) != null) {
                view.exibirMensagemErro("Nome de usuário já existente");
                return;
            }
        } catch (Exception ex) {
            view.exibirMensagemErro("Erro ao verificar usuário existente: " + ex.getMessage());
            return;
        }

        if (senha == null || senha.length() < 4) {
            view.exibirMensagemErro("A senha deve conter ao menos 4 caracteres");
            return;
        }

        if (!senha.equals(confirmacaoSenha)) {
            view.exibirMensagemErro("As senhas não coincidem");
            return;
        }

        try {
            Usuario novoUsuario = new Usuario(
                nomeCompleto,
                nomeUsuario,
                senha,
                false,
                false
            );

            novoUsuario.setAutorizado(false);

            Usuario usuarioSalvo = usuarioRepository.salvar(novoUsuario);

            if (usuarioSalvo != null) {
                String mensagemSucesso =
                    "Cadastro solicitado com sucesso!\n\n" +
                    "Seu cadastro foi registrado e está aguardando autorização.\n" +
                    "Você será notificado quando um administrador aprovar seu acesso.\n\n" +
                    "Nome: " + nomeCompleto + "\n" +
                    "Usuário: " + nomeUsuario;

                view.exibirMensagemSucesso(mensagemSucesso);
                view.dispose();
            } else {
                view.exibirMensagemErro("Erro ao salvar cadastro no banco de dados");
            }

        } catch (Exception e) {
            view.exibirMensagemErro("Erro ao realizar cadastro: " + e.getMessage());
        }
    }

    private void cancelar() {
        view.dispose();
    }

    public AutoCadastroView getView() {
        return view;
    }
}   

