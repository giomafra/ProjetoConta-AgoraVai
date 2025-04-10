package conta.view;

import conta.controller.CategoriaController;
import conta.controller.ContaController;
import conta.controller.TransacaoController;
import conta.controller.UsuarioController;
import conta.model.CategoriaModel;
import conta.model.ContaModel;
import conta.model.TransacaoModel;
import conta.model.UsuarioModel;
import conta.model.dao.CategoriaDAO;
import conta.model.dao.ContaDAO;
import conta.model.dao.TransacaoDAO;
import conta.model.dao.UsuarioDAO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuView {
    private Scanner scanner = new Scanner(System.in);
    private UsuarioController usuarioController = new UsuarioController();
    private ContaController contaController = new ContaController();
    private CategoriaController categoriaController = new CategoriaController();
    private TransacaoController transacaoController = new TransacaoController();



    public void mostrarMenu() {

        int opcao;
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Excluir");
            System.out.println("4. Atualizar");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    excluir();
                    break;
                case 4:
                    atualizar();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\nEscolha o que deseja cadastrar:");
        System.out.println("1. Usuário");
        System.out.println("2. Conta");
        System.out.println("3. Categoria");
        System.out.println("4. Transação");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                cadastrarUsuario();
                break;
            case 2:
                cadastrarConta();
                break;
            case 3:
                cadastrarCategoria();
                break;
            case 4:
                cadastrarTransacao();
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void cadastrarUsuario() {
        System.out.println("\nCadastro de Usuário");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        usuarioController.cadastrar(nome, email, senha);
    }

    private void cadastrarConta() {
        System.out.println("\nCadastro de Conta");

        System.out.print("Tipo da conta (corrente/poupanca): ");
        String tipoConta = scanner.nextLine();

        System.out.print("Nome do banco: ");
        String nomeDoBanco = scanner.nextLine();

        System.out.print("Número da conta: ");
        int numeroDaConta = Integer.parseInt(scanner.nextLine());

        System.out.print("Saldo inicial: ");
        double saldoInicial = Double.parseDouble(scanner.nextLine());

        System.out.print("Digite o nome do usuário para vincular a conta: ");
        String nomeUsuario = scanner.nextLine();

        System.out.print("Digite a senha do usuário: ");
        String senha = scanner.nextLine();

        contaController.cadastrarConta(tipoConta, nomeDoBanco, numeroDaConta, saldoInicial, nomeUsuario, senha);
    }

    private void cadastrarCategoria() {
        System.out.println("\nCadastro de Categoria");

        System.out.print("Tipo da Categoria: ");
        String tipoCategoria = scanner.nextLine();

        System.out.print("Nome do Usuário: ");
        String nome = scanner.nextLine();

        System.out.print("Senha do Usuário: ");
        String senha = scanner.nextLine();

        categoriaController.cadastrarCategoria(tipoCategoria, nome, senha);
    }

    private void cadastrarTransacao() {
        System.out.println("\nCadastro de Transação");

        System.out.print("Valor da Transação: ");
        double valor = Double.parseDouble(scanner.nextLine());

        System.out.print("Tipo de Transação (receita/despesa): ");
        String tipoTransacao = scanner.nextLine();

        System.out.print("Número da Conta: ");
        int numeroConta = Integer.parseInt(scanner.nextLine());

        System.out.print("Tipo da Categoria: ");
        String tipoCategoria = scanner.nextLine();

        System.out.print("Nome do Usuário: ");
        String nomeUsuario = scanner.nextLine();

        System.out.print("Senha do Usuário: ");
        String senha = scanner.nextLine();

        transacaoController.cadastrarTransacao(
                valor,
                tipoTransacao,
                numeroConta,
                tipoCategoria,
                nomeUsuario,
                senha
        );
    }

    private void listar() {
        System.out.println("\nEscolha o que deseja listar:");
        System.out.println("1. Usuário");
        System.out.println("2. Conta");
        System.out.println("3. Categoria");
        System.out.println("4. Transação");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                listarUsuarios();
                break;
            case 2:
                listarContas();
                break;
            case 3:
                listarCategorias();
                break;
            case 4:
                listarTransacoes();
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void listarUsuarios() {
        System.out.print("Nome de usuário: ");
        String nome = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        UsuarioController usuarioController = new UsuarioController();
        List<UsuarioModel> usuarios = usuarioController.listarUsuariosSeAdmin(nome, senha);

        if (usuarios == null) {
            System.out.println("Acesso negado. Apenas o administrador pode visualizar a lista de usuários.");
        } else {
            System.out.println("\n--- Lista de Usuários ---");
            for (UsuarioModel u : usuarios) {
                System.out.println("Nome: " + u.getNome());
                System.out.println("Email: " + u.getEmail());
                System.out.println("-------------");
            }
        }
    }

    private void listarContas() {
        System.out.print("Nome do usuário: ");
        String nome = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        ContaController contaController = new ContaController();
        List<ContaModel> contas = contaController.listarContasPorUsuario(nome, senha);

        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta encontrada ou credenciais inválidas.");
        } else {
            System.out.println("\n--- Contas do usuário ---");
            for (ContaModel conta : contas) {
                System.out.println("Tipo: " + conta.getTipoConta());
                System.out.println("Banco: " + conta.getBanco());
                System.out.println("Número da Conta: " + conta.getNumeroConta());
                System.out.println("Saldo: " + conta.getSaldo());
                System.out.println("-------------");
            }
        }
    }
    private void listarCategorias() {
        System.out.println("\n=== Listar Categorias ===");
        System.out.print("Nome do Usuário: ");
        String nome = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        List<CategoriaModel> categorias = categoriaController.listarCategoriasPorUsuario(nome, senha);

        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria encontrada ou credenciais inválidas.");
        } else {
            System.out.println("\nCategorias do usuário " + nome + ":");
            for (CategoriaModel categoria : categorias) {
                System.out.println("ID: " + categoria.getId() + " | Tipo: " + categoria.getTipoCategoria());
            }
        }
    }

    private void listarTransacoes() {
        System.out.print("Nome do Usuário: ");
        String nome = scanner.nextLine();
        System.out.print("Senha do Usuário: ");
        String senha = scanner.nextLine();

        List<TransacaoModel> transacoes = transacaoController.listarTransacoesPorUsuario(nome, senha);

        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação encontrada ou credenciais inválidas.");
            return;
        }

        System.out.println("\n=== Transações do Usuário ===");
        for (TransacaoModel t : transacoes) {
            System.out.printf("ID: %d | Valor: %.2f | Tipo: %s | Data: %s | ContaID: %d | CategoriaID: %d\n",
                    t.getId(), t.getValor(), t.getTipoTransacao(), t.getDataHoraTransacao(),
                    t.getContaId(), t.getCategoriaId());
        }
    }

    private void excluir() {
        System.out.println("\nEscolha o que deseja cadastrar:");
        System.out.println("1. Usuário");
        System.out.println("2. Conta");
        System.out.println("3. Categoria");
        System.out.println("4. Transação");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                excluirUsuario();
                break;
            case 2:
                excluirConta();
                break;
            case 3:
                excluirCategoria();
                break;
            case 4:
                excluirTransacao();
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void excluirUsuario() {
        System.out.print("Digite o nome do usuário a ser excluído: ");
        String nomeParaExcluir = scanner.nextLine();

        System.out.print("Digite o nome do usuário para confirmar: ");
        String adminNome = scanner.nextLine();

        System.out.print("Digite a senha: ");
        String adminSenha = scanner.nextLine();

        boolean sucesso = usuarioController.excluirUsuarioComoAdmin(nomeParaExcluir, adminNome, adminSenha);
        if (sucesso) {
            System.out.println("Usuário excluído com sucesso.");
        } else {
            System.out.println("Não foi possível excluir o usuário.");
        }
    }
    private void excluirConta() {
        System.out.print("Digite o nome do usuário: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a senha do usuário: ");
        String senha = scanner.nextLine();

        boolean sucesso = contaController.excluirConta(nome, senha);
        if (sucesso) {
            System.out.println("Conta excluída com sucesso.");
        } else {
            System.out.println("Erro ao excluir a conta.");
        }
    }


    private void excluirCategoria() {
        System.out.print("Digite o nome do usuário: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        boolean sucesso = categoriaController.excluirCategoriasDoUsuario(nome, senha);
        if (sucesso) {
            System.out.println("Categorias excluídas com sucesso.");
        } else {
            System.out.println("Erro ao excluir categorias.");
        }
    }

    private void excluirTransacao() {
        System.out.print("Digite o nome do usuário: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a senha do usuário: ");
        String senha = scanner.nextLine();

        boolean sucesso = transacaoController.excluirTransacoes(nome, senha);
        if (sucesso) {
            System.out.println("Transações excluídas com sucesso.");
        } else {
            System.out.println("Erro ao excluir transações.");
        }
    }
    private void atualizar() {
        System.out.println("\nEscolha o que deseja cadastrar:");
        System.out.println("1. Usuário");
        System.out.println("2. Conta");
        System.out.println("3. Categoria");
        System.out.println("4. Transação");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                atualizarUsuario();
                break;
            case 2:
                atualizarConta();
                break;
            case 3:
                atualizarCategoria();
                break;
            case 4:
                atualizarTransacao();
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void atualizarUsuario() {
        System.out.print("Digite o nome atual do usuário: ");
        String nomeAntigo = scanner.nextLine();

        System.out.print("Digite a senha atual do usuário: ");
        String senhaAntiga = scanner.nextLine();

        System.out.print("Digite o novo nome: ");
        String novoNome = scanner.nextLine();

        System.out.print("Digite o novo email: ");
        String novoEmail = scanner.nextLine();

        System.out.print("Digite a nova senha: ");
        String novaSenha = scanner.nextLine();

        UsuarioModel novoUsuario = new UsuarioModel(novoNome, novoEmail, novaSenha);

        boolean atualizado = usuarioController.atualizarUsuario(nomeAntigo, senhaAntiga, novoUsuario);
        if (atualizado) {
            System.out.println("Usuário atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar usuário. Verifique nome e senha.");
        }
    }

    private void atualizarConta() {
        System.out.print("Digite seu nome de usuário: ");
        String nome = scanner.nextLine();

        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        System.out.print("Novo tipo de conta (corrente ou poupanca): ");
        String tipoConta = scanner.nextLine();

        System.out.print("Novo nome do banco: ");
        String nomeBanco = scanner.nextLine();

        System.out.print("Novo número da conta: ");
        int numeroConta = scanner.nextInt();

        System.out.print("Novo saldo inicial: ");
        double saldoInicial = scanner.nextDouble();
        scanner.nextLine();

        ContaModel novaConta = new ContaModel(tipoConta, nomeBanco, numeroConta, saldoInicial);
        boolean atualizada = contaController.atualizarConta(nome, senha, novaConta);

        if (atualizada) {
            System.out.println("Conta atualizada com sucesso!");
        } else {
            System.out.println("Erro ao atualizar a conta. Verifique os dados.");
        }
    }


    private void atualizarCategoria() {
        System.out.print("Digite seu nome de usuário: ");
        String nome = scanner.nextLine();

        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        System.out.print("Digite o tipo da categoria que deseja atualizar: ");
        String tipoAtual = scanner.nextLine();

        System.out.print("Digite o novo tipo para a categoria: ");
        String novoTipo = scanner.nextLine();

        boolean atualizada = categoriaController.atualizarCategoria(nome, senha, tipoAtual, novoTipo);

        if (atualizada) {
            System.out.println("Categoria atualizada com sucesso!");
        } else {
            System.out.println("Erro ao atualizar categoria. Verifique os dados.");
        }
    }

    private void atualizarTransacao() {
        System.out.print("Digite seu nome de usuário: ");
        String nome = scanner.nextLine();

        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        System.out.print("Digite o ID da transação que deseja atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Novo valor: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Novo tipo (receita ou despesa): ");
        String tipo = scanner.nextLine();

        System.out.print("Nova data e hora (formato: yyyy-MM-dd HH:mm:ss): ");
        String dataHoraStr = scanner.nextLine();
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.print("Tipo da nova categoria: ");
        String tipoCategoria = scanner.nextLine();

        // Remova tipoCategoria do construtor
        TransacaoModel nova = new TransacaoModel(valor, tipo, dataHora);

        // Passe tipoCategoria separadamente para o controller
        boolean atualizado = transacaoController.atualizarTransacao(id, nome, senha, nova, tipoCategoria);

        if (atualizado) {
            System.out.println("Transação atualizada com sucesso!");
        } else {
            System.out.println("Erro ao atualizar a transação.");
        }
    }

}














