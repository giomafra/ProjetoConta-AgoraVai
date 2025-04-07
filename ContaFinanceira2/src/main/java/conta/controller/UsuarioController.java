package conta.controller;

import conta.model.UsuarioModel;
import conta.model.dao.UsuarioDAO;

import java.util.List;

public class UsuarioController {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void cadastrar(String nome, String email, String senha) {
        UsuarioModel usuario = new UsuarioModel(nome, email, senha);
        usuarioDAO.cadastrar(usuario);
    }

    public List<UsuarioModel> listarUsuariosSeAdmin(String nome, String senha) {
        boolean adminValido = usuarioDAO.autenticarAdmin(nome, senha);
        if (adminValido) {
            return usuarioDAO.listarTodos();
        } else {
            return null;
        }
    }

    public boolean excluirUsuarioComoAdmin(String nomeUsuarioParaExcluir, String adminNome, String adminSenha) {
        UsuarioDAO dao = new UsuarioDAO();
        return dao.excluirUsuario(nomeUsuarioParaExcluir, adminNome, adminSenha);
    }

    public boolean atualizarUsuario(String nomeAntigo, String senhaAntiga, UsuarioModel novoUsuario) {
        UsuarioDAO dao = new UsuarioDAO();
        return dao.atualizarUsuario(nomeAntigo, senhaAntiga, novoUsuario);
    }



}