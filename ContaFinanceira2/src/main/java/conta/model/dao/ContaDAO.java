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
            stmt.setString(2, conta.getNomeDoBanco());
            stmt.setInt(3, conta.getNumeroDaConta());
            stmt.setDouble(4, conta.getSaldoInicial());

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
}
