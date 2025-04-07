package conta.model.dao;

import conta.model.TransacaoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransacaoDAO {

    public boolean cadastrar(TransacaoModel transacao) {
        String sql = "INSERT INTO transacao (valor, tipoTransacao, dataHoraTransacao, usuario_id, conta_id, categoria_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, transacao.getValor());
            stmt.setString(2, transacao.getTipoTransacao());
            stmt.setTimestamp(3, Timestamp.valueOf(transacao.getDataHoraTransacao()));
            stmt.setInt(4, transacao.getUsuarioId());
            stmt.setInt(5, transacao.getContaId());
            stmt.setInt(6, transacao.getCategoriaId());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
