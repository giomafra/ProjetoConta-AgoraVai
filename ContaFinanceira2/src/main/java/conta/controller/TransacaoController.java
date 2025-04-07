package conta.controller;

import conta.model.TransacaoModel;
import conta.model.UsuarioModel;
import conta.model.ContaModel;
import conta.model.dao.TransacaoDAO;
import conta.model.dao.UsuarioDAO;
import conta.model.dao.ContaDAO;
import conta.model.dao.CategoriaDAO;

import java.time.LocalDateTime;
import java.util.List;

public class TransacaoController {

    private TransacaoDAO transacaoDAO = new TransacaoDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ContaDAO contaDAO = new ContaDAO();
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    public void cadastrarTransacao(double valor, String tipoTransacao, int numeroConta, String tipoCategoria, String nomeUsuario, String senha) {
        // implementação aqui


        UsuarioModel usuario = usuarioDAO.buscarPorNomeESenha(nomeUsuario, senha);
        if (usuario == null) {
            System.out.println("Usuário não encontrado. Nome ou senha incorretos.");
            return;
        }

        ContaModel conta = contaDAO.buscarPorNumero(numeroConta);
        if (conta == null) {
            System.out.println("Conta não encontrada.");
            return;
        }

        int categoriaId = categoriaDAO.buscarIdPorTipo(tipoCategoria);
        if (categoriaId == -1) {
            System.out.println("Categoria não encontrada.");
            return;
        }

        LocalDateTime agora = LocalDateTime.now();
        TransacaoModel transacao = new TransacaoModel(valor, tipoTransacao, agora, usuario.getId(), conta.getId(), categoriaId);

        // Atualizar o saldo da conta
        double saldoAtualizado = conta.getSaldo();

        if (tipoTransacao.equalsIgnoreCase("receita")) {
            saldoAtualizado += valor;
        } else if (tipoTransacao.equalsIgnoreCase("despesa")) {
            saldoAtualizado -= valor;
        } else {
            System.out.println("Tipo de transação inválido.");
            return;
        }

        // Atualiza saldo no banco
        boolean saldoAtualizadoComSucesso = contaDAO.atualizarSaldo(conta.getId(), saldoAtualizado);

        if (!saldoAtualizadoComSucesso) {
            System.out.println("Erro ao atualizar o saldo da conta.");
            return;
        }

        // Cadastra transação
        boolean sucesso = transacaoDAO.cadastrar(transacao);

        if (sucesso) {
            System.out.println("Transação cadastrada com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar transação.");
        }
    }

    public List<TransacaoModel> listarTransacoesPorUsuario(String nome, String senha) {
        return transacaoDAO.listarTransacoesPorUsuario(nome, senha);
    }

    public boolean excluirTransacoes(String nomeUsuario, String senha) {
        TransacaoDAO dao = new TransacaoDAO();
        return dao.excluirTransacoesPorUsuario(nomeUsuario, senha);
    }

    public boolean atualizarTransacao(int id, String nomeUsuario, String senha, TransacaoModel novaTransacao, String tipoCategoria) {
        TransacaoDAO dao = new TransacaoDAO();
        return dao.atualizarTransacao(id, nomeUsuario, senha, novaTransacao, tipoCategoria);
    }




}
