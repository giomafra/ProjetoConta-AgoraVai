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

}
