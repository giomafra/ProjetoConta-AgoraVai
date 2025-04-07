package conta.view;

import conta.controller.CategoriaController;
import conta.controller.ContaController;
import conta.controller.UsuarioController;

import java.util.Scanner;

public class MenuView {
    private Scanner scanner = new Scanner(System.in);
    private UsuarioController usuarioController = new UsuarioController();
    private ContaController contaController = new ContaController();
    private CategoriaController categoriaController = new CategoriaController();




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
//                    listar();
                    break;
                case 3:
//                    excluir();
                    break;
                case 4:
//                    atualizar();
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
//                cadastrarTransacao();
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


}


