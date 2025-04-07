package conta.model.dao;

import conta.model.CategoriaModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
