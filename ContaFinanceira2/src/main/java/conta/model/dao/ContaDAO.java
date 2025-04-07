package conta.model.dao;

import conta.model.ContaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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


}
