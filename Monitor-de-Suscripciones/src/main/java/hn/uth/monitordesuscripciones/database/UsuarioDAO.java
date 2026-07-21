package hn.uth.monitordesuscripciones.database;

import hn.uth.monitordesuscripciones.data.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public UsuarioDAO() {
        DatabaseInitializer.initialize();
    }

    public Usuario findByCorreo(String correo) {
        String sql = "SELECT id, nombre, correo, password, rol_id, aprobado FROM usuarios WHERE correo = ?";
        try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Error al buscar usuario", ex);
        }
        return null;
    }

    public void crear(Usuario usuario) {
        String sql = "INSERT INTO usuarios(nombre, correo, password, rol_id, aprobado) VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getPassword());
            ps.setInt(4, usuario.getRolId());
            ps.setInt(5, usuario.isAprobado() ? 1 : 0);
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new IllegalStateException("Error al crear usuario", ex);
        }
    }

    public List<Usuario> listarPendientesAprobacion() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, nombre, correo, password, rol_id, aprobado FROM usuarios WHERE aprobado = 0 ORDER BY id DESC";
        try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                usuarios.add(map(rs));
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Error al listar usuarios pendientes", ex);
        }
        return usuarios;
    }

    public void aprobar(int idUsuario) {
        updateSimple("UPDATE usuarios SET aprobado = 1 WHERE id = ?", idUsuario);
    }

    public void resetPassword(int idUsuario, String nuevaPassword) {
        String sql = "UPDATE usuarios SET password = ? WHERE id = ?";
        try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nuevaPassword);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new IllegalStateException("Error al resetear password", ex);
        }
    }

    private void updateSimple(String sql, int idUsuario) {
        try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new IllegalStateException("Error al actualizar usuario", ex);
        }
    }

    private Usuario map(ResultSet rs) throws Exception {
        return new Usuario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getString("password"),
                rs.getInt("rol_id"),
                rs.getInt("aprobado") == 1
        );
    }
}

