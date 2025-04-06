package conta.controller;

import conta.model.UsuarioModel;
import conta.model.dao.UsuarioDAO;

public class UsuarioController {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void cadastrar(String nome, String email, String senha) {
        UsuarioModel usuario = new UsuarioModel(nome, email, senha);
        usuarioDAO.cadastrarUsuario(usuario);
    }
}