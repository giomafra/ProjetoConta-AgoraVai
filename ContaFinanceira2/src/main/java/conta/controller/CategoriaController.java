package conta.controller;

import conta.model.CategoriaModel;
import conta.model.UsuarioModel;
import conta.model.dao.CategoriaDAO;
import conta.model.dao.UsuarioDAO;

public class CategoriaController {

    private CategoriaDAO categoriaDAO = new CategoriaDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void cadastrarCategoria(String tipoCategoria, String nomeUsuario, String senha) {
        UsuarioModel usuario = usuarioDAO.buscarPorNomeESenha(nomeUsuario, senha);

        if (usuario != null) {
            CategoriaModel categoria = new CategoriaModel(tipoCategoria, usuario.getId());
            categoriaDAO.cadastrar(categoria);
            System.out.println("Categoria cadastrada com sucesso!");
        } else {
            System.out.println("Usuário não encontrado. Nome ou senha inválidos.");
        }
    }
}
