package conta.model.dao;

import conta.model.UsuarioModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public boolean autenticarAdmin(String nome, String senha) {
        String sql = "SELECT * FROM usuario WHERE nome = ? AND senha = ?";
        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && nome.equalsIgnoreCase("admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<UsuarioModel> listarTodos() {
        List<UsuarioModel> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection conn = ConexaoBD.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                UsuarioModel usuario = new UsuarioModel(
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public boolean excluirUsuario(String nomeUsuarioParaExcluir, String adminNome, String adminSenha) {
        if (!autenticarAdmin(adminNome, adminSenha)) {
            System.out.println("Apenas o usuário admin pode excluir outros usuários.");
            return false;
        }

        String buscarUsuarioId = "SELECT id FROM usuario WHERE nome = ?";
        String excluirTransacoes = "DELETE FROM transacao WHERE usuario_id = ?";
        String excluirCategorias = "DELETE FROM categoria WHERE usuario_id = ?";
        String excluirUsuario = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmtBuscar = conn.prepareStatement(buscarUsuarioId)) {

            stmtBuscar.setString(1, nomeUsuarioParaExcluir);
            ResultSet rs = stmtBuscar.executeQuery();

            if (rs.next()) {
                int usuarioId = rs.getInt("id");

                try (PreparedStatement stmtTransacoes = conn.prepareStatement(excluirTransacoes);
                     PreparedStatement stmtCategorias = conn.prepareStatement(excluirCategorias);
                     PreparedStatement stmtUsuario = conn.prepareStatement(excluirUsuario)) {

                    stmtTransacoes.setInt(1, usuarioId);
                    stmtTransacoes.executeUpdate();

                    stmtCategorias.setInt(1, usuarioId);
                    stmtCategorias.executeUpdate();

                    stmtUsuario.setInt(1, usuarioId);
                    stmtUsuario.executeUpdate();

                    System.out.println("Usuário e dados relacionados excluídos com sucesso.");
                    return true;
                }

            } else {
                System.out.println("Usuário não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean atualizarUsuario(String nomeAntigo, String senhaAntiga, UsuarioModel novoUsuario) {
        String buscarUsuario = "SELECT id FROM usuario WHERE nome = ? AND senha = ?";
        String atualizarUsuario = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getInstance().getConnection();
             PreparedStatement stmtBusca = conn.prepareStatement(buscarUsuario)) {

            stmtBusca.setString(1, nomeAntigo);
            stmtBusca.setString(2, senhaAntiga);
            ResultSet rs = stmtBusca.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");

                try (PreparedStatement stmtAtualizar = conn.prepareStatement(atualizarUsuario)) {
                    stmtAtualizar.setString(1, novoUsuario.getNome());
                    stmtAtualizar.setString(2, novoUsuario.getEmail());
                    stmtAtualizar.setString(3, novoUsuario.getSenha());
                    stmtAtualizar.setInt(4, id);

                    int linhas = stmtAtualizar.executeUpdate();
                    return linhas > 0;
                }

            } else {
                System.out.println("Usuário não encontrado ou senha incorreta.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



}
