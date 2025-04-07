package conta.model.dao;

import conta.model.CategoriaModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
