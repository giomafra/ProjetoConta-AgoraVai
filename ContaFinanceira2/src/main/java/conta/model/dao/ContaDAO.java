package conta.model.dao;

import conta.model.ContaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

public class ContaDAO {

    public int cadastrar(ContaModel conta) {
        String sql = "INSERT INTO conta (tipoConta, nomeDoBanco, numeroConta, saldoInicial) VALUES (?, ?, ?, ?)";
        int contaId = -1;

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, conta.getTipoConta());
            stmt.setString(2, conta.getBanco());
            stmt.setInt(3, conta.getNumeroConta());
            stmt.setDouble(4, conta.getSaldo());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                contaId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contaId;
    }

    public ContaModel buscarPorNumero(int numeroConta) {
        String sql = "SELECT * FROM conta WHERE numeroConta = ?";
        ContaModel conta = null;

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numeroConta);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String tipoConta = rs.getString("tipoConta");
                String banco = rs.getString("nomeDoBanco");
                double saldo = rs.getDouble("saldoInicial");

                conta = new ContaModel(tipoConta, banco, numeroConta, saldo);
                conta.setId(rs.getInt("id")); // se você tiver o atributo id na ContaModel
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conta;
    }

    public boolean atualizarSaldo(int contaId, double novoSaldo) {
        String sql = "UPDATE conta SET saldoInicial = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, novoSaldo);
            stmt.setInt(2, contaId);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ContaModel> listarContasPorUsuario(String nome, String senha) {
        List<ContaModel> contas = new ArrayList<>();

        String sql = """
                SELECT c.id, c.tipoConta, c.nomeDoBanco, c.numeroConta, c.saldoInicial
                FROM conta c
                JOIN usuario u ON u.conta_id = c.id
                WHERE u.nome = ? AND u.senha = ?
                """;

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ContaModel conta = new ContaModel(
                        rs.getString("tipoConta"),
                        rs.getString("nomeDoBanco"),
                        rs.getInt("numeroConta"),
                        rs.getDouble("saldoInicial")
                );
                contas.add(conta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contas;
    }

    public boolean excluirContaPorUsuario(String nomeUsuario, String senha) {
        String buscarContaId = "SELECT c.id FROM conta c JOIN usuario u ON u.conta_id = c.id WHERE u.nome = ? AND u.senha = ?";
        String atualizarUsuario = "UPDATE usuario SET conta_id = NULL WHERE nome = ? AND senha = ?";
        String excluirConta = "DELETE FROM conta WHERE id = ?";

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmtBusca = conn.prepareStatement(buscarContaId)) {

            stmtBusca.setString(1, nomeUsuario);
            stmtBusca.setString(2, senha);
            ResultSet rs = stmtBusca.executeQuery();

            if (rs.next()) {
                int contaId = rs.getInt("id");

                // Primeiro, desvincula a conta do usuário
                try (PreparedStatement stmtUpdate = conn.prepareStatement(atualizarUsuario)) {
                    stmtUpdate.setString(1, nomeUsuario);
                    stmtUpdate.setString(2, senha);
                    stmtUpdate.executeUpdate();
                }

                // Depois, exclui a conta
                try (PreparedStatement stmtExcluir = conn.prepareStatement(excluirConta)) {
                    stmtExcluir.setInt(1, contaId);
                    int linhasAfetadas = stmtExcluir.executeUpdate();

                    if (linhasAfetadas > 0) {
                        System.out.println("Conta excluída com sucesso.");
                        return true;
                    }
                }
            } else {
                System.out.println("Conta não encontrada para o usuário informado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean atualizarConta(String nomeUsuario, String senha, ContaModel novaConta) {
        String sqlBuscar = """
        SELECT c.id FROM conta c
        JOIN usuario u ON u.conta_id = c.id
        WHERE u.nome = ? AND u.senha = ?
    """;

        String sqlAtualizar = """
        UPDATE conta
        SET tipoConta = ?, nomeDoBanco = ?, numeroConta = ?, saldoInicial = ?
        WHERE id = ?
    """;

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmtBusca = conn.prepareStatement(sqlBuscar)) {

            stmtBusca.setString(1, nomeUsuario);
            stmtBusca.setString(2, senha);
            ResultSet rs = stmtBusca.executeQuery();

            if (rs.next()) {
                int contaId = rs.getInt("id");

                try (PreparedStatement stmtAtualizar = conn.prepareStatement(sqlAtualizar)) {
                    stmtAtualizar.setString(1, novaConta.getTipoConta());
                    stmtAtualizar.setString(2, novaConta.getBanco());
                    stmtAtualizar.setInt(3, novaConta.getNumeroConta());
                    stmtAtualizar.setDouble(4, novaConta.getSaldo());
                    stmtAtualizar.setInt(5, contaId);

                    int linhasAfetadas = stmtAtualizar.executeUpdate();
                    return linhasAfetadas > 0;
                }
            } else {
                System.out.println("Conta não encontrada ou dados incorretos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }




}
