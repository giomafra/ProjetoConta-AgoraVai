package conta.model.dao;

import conta.model.TransacaoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<TransacaoModel> listarTransacoesPorUsuario(String nome, String senha) {
        List<TransacaoModel> transacoes = new ArrayList<>();

        String sql = """
        SELECT t.id, t.valor, t.tipoTransacao, t.dataHoraTransacao, t.usuario_id, 
               t.conta_id, t.categoria_id
        FROM transacao t
        JOIN usuario u ON u.id = t.usuario_id
        WHERE u.nome = ? AND u.senha = ?
        """;

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TransacaoModel transacao = new TransacaoModel(
                        rs.getDouble("valor"),
                        rs.getString("tipoTransacao"),
                        rs.getTimestamp("dataHoraTransacao").toLocalDateTime(),
                        rs.getInt("usuario_id"),
                        rs.getInt("conta_id"),
                        rs.getInt("categoria_id")
                );
                transacao.setId(rs.getInt("id"));
                transacoes.add(transacao);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transacoes;
    }
}
