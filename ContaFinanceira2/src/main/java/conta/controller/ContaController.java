package conta.controller;

import conta.model.ContaModel;
import conta.model.Rendimento;
import conta.model.UsuarioModel;
import conta.model.dao.ContaDAO;
import conta.model.dao.UsuarioDAO;

import java.util.List;

public class ContaController {
    private ContaDAO contaDAO = new ContaDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void cadastrarConta(String tipoConta, String banco, int numeroConta, double saldo, String nomeUsuario, String senha) {
        UsuarioModel usuario = usuarioDAO.buscarPorNomeESenha(nomeUsuario, senha);

        if (usuario != null) {
            ContaModel conta;

            if (tipoConta.equalsIgnoreCase("poupanca")) {
                conta = new ContaModel(tipoConta, banco, numeroConta, saldo);
                conta.calcularRendimento();
            } else {
                conta = new ContaModel(tipoConta, banco, numeroConta, saldo);
            }

            int contaId = contaDAO.cadastrar(conta);

            if (contaId != -1) {
                usuarioDAO.vincularConta(nomeUsuario, senha, contaId);
                System.out.println("Conta cadastrada e vinculada com sucesso!");
            } else {
                System.out.println("Erro ao cadastrar a conta.");
            }

        } else {
            System.out.println("Usuário não encontrado. Nome ou senha inválidos.");
        }
    }
    public List<ContaModel> listarContasPorUsuario(String nome, String senha) {
        return contaDAO.listarContasPorUsuario(nome, senha);
    }

    public boolean excluirConta(String nomeUsuario, String senha) {
        ContaDAO dao = new ContaDAO();
        return dao.excluirContaPorUsuario(nomeUsuario, senha);
    }

    public boolean atualizarConta(String nomeUsuario, String senha, ContaModel novaConta) {
        ContaDAO dao = new ContaDAO();
        return dao.atualizarConta(nomeUsuario, senha, novaConta);
    }


}
