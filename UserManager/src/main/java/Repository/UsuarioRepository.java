/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Exception.RepositoryException;
import com.mycompany.usermanager.Model.Usuario;
import ConnectionDb.SQLiteConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
     private static final DateTimeFormatter DATETIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public boolean existeAlgumUsuario() {
        String sql = "SELECT COUNT(*) as total FROM Users";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            return rs.next() && rs.getInt("total") > 0;

        } catch (SQLException e) {
            throw new RepositoryException("Erro ao verificar existência de usuários", e);
        }
    }
    
      public Usuario salvar(Usuario usuario) {
        String sql = """
            INSERT INTO Users (fullName, username, password, profile, isFirstAdmin, registrationDate, isAuthorized)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, usuario.getNomeCompleto());
            pstmt.setString(2, usuario.getNomeUsuario());
            pstmt.setString(3, usuario.getSenha());
            pstmt.setString(4, usuario.isAdministrador() ? "ADMINISTRATOR" : "USER");
            pstmt.setInt(5, usuario.isPrimeiroAdmin() ? 1 : 0);
            pstmt.setString(6, usuario.getDataCadastro().format(DATETIME_FORMATTER));
            pstmt.setInt(7, usuario.isAutorizado() ? 1 : 0);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuario.setId(generatedKeys.getInt(1));
                        return usuario;
                    }
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RepositoryException("Erro ao salvar usuário", e);
        }
    }
      
     public Usuario buscarPorNomeUsuario(String nomeUsuario) {
        String sql = "SELECT * FROM Users WHERE username = ?";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomeUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RepositoryException("Erro ao buscar usuário por nome de usuário", e);
        }
    }

    public Usuario buscarPorId(Integer id) {
        String sql = "SELECT * FROM Users WHERE id = ?";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RepositoryException("Erro ao buscar usuário por ID", e);
        }
    }

    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY registrationDate";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }

            return usuarios;

        } catch (SQLException e) {
            throw new RepositoryException("Erro ao listar usuários", e);
        }
    }

    public List<Usuario> listarPendentes() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE isAuthorized = 0 ORDER BY registrationDate";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }

            return usuarios;

        } catch (SQLException e) {
            throw new RepositoryException("Erro ao listar usuários pendentes", e);
        }
  }
    
    
    public boolean autorizarUsuario(Integer id) {
      String sql = "UPDATE Users SET isAuthorized = 1 WHERE id = ?";

      try (Connection conn = SQLiteConnection.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql)) {

          pstmt.setInt(1, id);
          return pstmt.executeUpdate() > 0;

      } catch (SQLException e) {
          throw new RepositoryException("Erro ao autorizar usuário", e);
      }
  }

  public boolean alterarPerfil(Integer id, boolean isAdministrador) {
      String sql = "UPDATE Users SET profile = ? WHERE id = ?";

      try (Connection conn = SQLiteConnection.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql)) {

          pstmt.setString(1, isAdministrador ? "ADMINISTRATOR" : "USER");
          pstmt.setInt(2, id);

          return pstmt.executeUpdate() > 0;

      } catch (SQLException e) {
          throw new RepositoryException("Erro ao alterar perfil do usuário", e);
      }
  }

      public List<Usuario> listarAutorizadosExceto(Integer idExcluir) {
          List<Usuario> usuarios = new ArrayList<>();
          String sql = "SELECT * FROM Users WHERE isAuthorized = 1 AND id != ? ORDER BY fullName";

          try (Connection conn = SQLiteConnection.getConnection();
               PreparedStatement pstmt = conn.prepareStatement(sql)) {

              pstmt.setInt(1, idExcluir);
              try (ResultSet rs = pstmt.executeQuery()) {
                  while (rs.next()) {
                      usuarios.add(mapearUsuario(rs));
                  }
              }

              return usuarios;

          } catch (SQLException e) {
              throw new RepositoryException("Erro ao listar usuários autorizados", e);
          }
      }

    public boolean atualizar(Usuario usuario) {
        String sql = """
            UPDATE Users
            SET fullName = ?, username = ?, password = ?, profile = ?,
                isFirstAdmin = ?, isAuthorized = ?
            WHERE id = ?
        """;

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNomeCompleto());
            pstmt.setString(2, usuario.getNomeUsuario());
            pstmt.setString(3, usuario.getSenha());
            pstmt.setString(4, usuario.isAdministrador() ? "ADMINISTRATOR" : "USER");
            pstmt.setInt(5, usuario.isPrimeiroAdmin() ? 1 : 0);
            pstmt.setInt(6, usuario.isAutorizado() ? 1 : 0);
            pstmt.setInt(7, usuario.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RepositoryException("Erro ao atualizar usuário", e);
        }
    }

    public boolean excluir(Integer id) {
        String sql = "DELETE FROM Users WHERE id = ?";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RepositoryException("Erro ao excluir usuário", e);
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id"),
                rs.getString("fullName"),
                rs.getString("username"),
                rs.getString("password"),
                "ADMINISTRATOR".equals(rs.getString("profile")),
                rs.getInt("isFirstAdmin") == 1,
                LocalDateTime.parse(rs.getString("registrationDate"), DATETIME_FORMATTER),
                rs.getInt("isAuthorized") == 1
        );
    }
 }
    

