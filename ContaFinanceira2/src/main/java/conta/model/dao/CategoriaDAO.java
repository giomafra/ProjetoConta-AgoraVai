package conta.model.dao;

import conta.model.CategoriaModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public void cadastrar(CategoriaModel categoria) {
        String sql = "INSERT INTO categoria (tipoCategoria, usuario_id) VALUES (?, ?)";

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getTipoCategoria());
            stmt.setInt(2, categoria.getUsuarioId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int buscarIdPorTipo(String tipoCategoria) {
        String sql = "SELECT id FROM categoria WHERE tipoCategoria = ?";
        int id = -1;

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipoCategoria);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public List<CategoriaModel> listarCategoriasPorUsuario(String nome, String senha) {
        List<CategoriaModel> categorias = new ArrayList<>();

        String sql = """
                SELECT c.id, c.tipoCategoria
                FROM categoria c
                JOIN usuario u ON c.usuario_id = u.id
                WHERE u.nome = ? AND u.senha = ?
                """;

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CategoriaModel categoria = new CategoriaModel();
                categoria.setId(rs.getInt("id"));
                categoria.setTipoCategoria(rs.getString("tipoCategoria"));
                categorias.add(categoria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }
    public boolean excluirCategoriasPorUsuario(String nomeUsuario, String senha) {
        String buscarUsuarioId = "SELECT id FROM usuario WHERE nome = ? AND senha = ?";
        String excluirCategorias = "DELETE FROM categoria WHERE usuario_id = ?";

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmtBuscar = conn.prepareStatement(buscarUsuarioId)) {

            stmtBuscar.setString(1, nomeUsuario);
            stmtBuscar.setString(2, senha);
            ResultSet rs = stmtBuscar.executeQuery();

            if (rs.next()) {
                int usuarioId = rs.getInt("id");

                try (PreparedStatement stmtExcluir = conn.prepareStatement(excluirCategorias)) {
                    stmtExcluir.setInt(1, usuarioId);
                    int linhasAfetadas = stmtExcluir.executeUpdate();

                    if (linhasAfetadas > 0) {
                        System.out.println("Categorias excluídas com sucesso.");
                        return true;
                    } else {
                        System.out.println("Nenhuma categoria encontrada para esse usuário.");
                    }
                }
            } else {
                System.out.println("Usuário não encontrado ou senha incorreta.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean atualizarCategoria(String nomeUsuario, String senha, String tipoCategoriaAtual, String novoTipoCategoria) {
        String sqlBuscar = "SELECT c.id FROM categoria c " +
                "JOIN usuario u ON c.usuario_id = u.id " +
                "WHERE u.nome = ? AND u.senha = ? AND c.tipoCategoria = ?";

        String sqlAtualizar = "UPDATE categoria SET tipoCategoria = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmtBusca = conn.prepareStatement(sqlBuscar)) {

            stmtBusca.setString(1, nomeUsuario);
            stmtBusca.setString(2, senha);
            stmtBusca.setString(3, tipoCategoriaAtual);
            ResultSet rs = stmtBusca.executeQuery();

            if (rs.next()) {
                int idCategoria = rs.getInt("id");

                try (PreparedStatement stmtAtualiza = conn.prepareStatement(sqlAtualizar)) {
                    stmtAtualiza.setString(1, novoTipoCategoria);
                    stmtAtualiza.setInt(2, idCategoria);

                    int linhasAfetadas = stmtAtualiza.executeUpdate();
                    return linhasAfetadas > 0;
                }
            } else {
                System.out.println("Categoria não encontrada ou dados incorretos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



}
