package conta.model.dao;

import conta.model.UsuarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public void cadastrar(UsuarioModel usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UsuarioModel buscarPorNomeESenha(String nome, String senha) {
        String sql = "SELECT * FROM usuario WHERE nome = ? AND senha = ?";
        UsuarioModel usuario = null;

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");
                usuario = new UsuarioModel(nome, email, senha);
                usuario.setId(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    public void vincularConta(String nome, String senha, int contaId) {
        String sql = "UPDATE usuario SET conta_id = ? WHERE nome = ? AND senha = ?";

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, contaId);
            stmt.setString(2, nome);
            stmt.setString(3, senha);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
