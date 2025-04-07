package conta.model.dao;

import conta.model.CategoriaModel;
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
    public boolean excluirTransacoesPorUsuario(String nomeUsuario, String senha) {
        String buscarUsuarioId = "SELECT id FROM usuario WHERE nome = ? AND senha = ?";
        String excluirTransacoes = "DELETE FROM transacao WHERE usuario_id = ?";

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmtBusca = conn.prepareStatement(buscarUsuarioId)) {

            stmtBusca.setString(1, nomeUsuario);
            stmtBusca.setString(2, senha);
            ResultSet rs = stmtBusca.executeQuery();

            if (rs.next()) {
                int usuarioId = rs.getInt("id");

                try (PreparedStatement stmtExcluir = conn.prepareStatement(excluirTransacoes)) {
                    stmtExcluir.setInt(1, usuarioId);
                    int linhasAfetadas = stmtExcluir.executeUpdate();

                    if (linhasAfetadas > 0) {
                        System.out.println("Transações excluídas com sucesso.");
                    } else {
                        System.out.println("Nenhuma transação encontrada para o usuário.");
                    }

                    return true;
                }
            } else {
                System.out.println("Usuário não encontrado ou senha incorreta.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    public boolean atualizarTransacao(int transacaoId, String nomeUsuario, String senha, TransacaoModel novaTransacao, String tipoCategoria) {
        String sqlUsuario = "SELECT id FROM usuario WHERE nome = ? AND senha = ?";
        String sqlCategoria = "SELECT id FROM categoria WHERE tipoCategoria = ? AND usuario_id = ?";
        String sqlUpdate = """
        UPDATE transacao
        SET valor = ?, tipoTransacao = ?, dataHoraTransacao = ?, categoria_id = ?
        WHERE id = ? AND usuario_id = ?
    """;

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario)) {

            stmtUsuario.setString(1, nomeUsuario);
            stmtUsuario.setString(2, senha);
            ResultSet rsUser = stmtUsuario.executeQuery();

            if (rsUser.next()) {
                int usuarioId = rsUser.getInt("id");

                try (PreparedStatement stmtCategoria = conn.prepareStatement(sqlCategoria)) {

                    stmtCategoria.setString(1, tipoCategoria); // aqui corrigido ✅
                    stmtCategoria.setInt(2, usuarioId);
                    ResultSet rsCat = stmtCategoria.executeQuery();

                    if (rsCat.next()) {
                        int categoriaId = rsCat.getInt("id");

                        try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                            stmtUpdate.setDouble(1, novaTransacao.getValor());
                            stmtUpdate.setString(2, novaTransacao.getTipoTransacao());
                            stmtUpdate.setTimestamp(3, Timestamp.valueOf(novaTransacao.getDataHoraTransacao()));
                            stmtUpdate.setInt(4, categoriaId);
                            stmtUpdate.setInt(5, transacaoId);
                            stmtUpdate.setInt(6, usuarioId);

                            int linhasAfetadas = stmtUpdate.executeUpdate();
                            return linhasAfetadas > 0;
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



}
