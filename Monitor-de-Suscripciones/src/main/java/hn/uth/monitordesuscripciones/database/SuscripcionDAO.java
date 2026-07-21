package hn.uth.monitordesuscripciones.database;

import hn.uth.monitordesuscripciones.data.Suscripcion;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SuscripcionDAO {

    public SuscripcionDAO() {
        DatabaseInitializer.initialize();
    }

    public List<Suscripcion> listarPorUsuario(int usuarioId) {
        List<Suscripcion> lista = new ArrayList<>();
        String sql = "SELECT s.id, s.usuario_id, s.categoria_id, s.servicio, s.costo, s.fecha_renovacion, s.recurrencia, c.nombre categoria_nombre " +
                "FROM suscripciones s JOIN categorias c ON c.id = s.categoria_id WHERE s.usuario_id = ? ORDER BY s.servicio";
        try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Error al listar suscripciones", ex);
        }
        return lista;
    }

    public Suscripcion findById(int id) {
        String sql = "SELECT s.id, s.usuario_id, s.categoria_id, s.servicio, s.costo, s.fecha_renovacion, s.recurrencia, c.nombre categoria_nombre " +
                "FROM suscripciones s JOIN categorias c ON c.id = s.categoria_id WHERE s.id = ?";
        try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Error al buscar suscripcion", ex);
        }
        return null;
    }

    public void crear(Suscripcion suscripcion) {
        String sql = "INSERT INTO suscripciones(usuario_id, categoria_id, servicio, costo, fecha_renovacion, recurrencia) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, suscripcion.getUsuarioId());
            ps.setInt(2, suscripcion.getCategoriaId());
            ps.setString(3, suscripcion.getServicio());
            ps.setBigDecimal(4, suscripcion.getCosto());
            ps.setString(5, suscripcion.getFechaRenovacion().toString());
            ps.setString(6, suscripcion.getRecurrencia());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new IllegalStateException("Error al crear suscripcion", ex);
        }
    }

    public void actualizar(Suscripcion suscripcion) {
        String sql = "UPDATE suscripciones SET categoria_id = ?, servicio = ?, costo = ?, fecha_renovacion = ?, recurrencia = ? WHERE id = ?";
        try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, suscripcion.getCategoriaId());
            ps.setString(2, suscripcion.getServicio());
            ps.setBigDecimal(3, suscripcion.getCosto());
            ps.setString(4, suscripcion.getFechaRenovacion().toString());
            ps.setString(5, suscripcion.getRecurrencia());
            ps.setInt(6, suscripcion.getId());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new IllegalStateException("Error al actualizar suscripcion", ex);
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM suscripciones WHERE id = ?";
        try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new IllegalStateException("Error al eliminar suscripcion", ex);
        }
    }

    private Suscripcion map(ResultSet rs) throws Exception {
        Suscripcion s = new Suscripcion();
        s.setId(rs.getInt("id"));
        s.setUsuarioId(rs.getInt("usuario_id"));
        s.setCategoriaId(rs.getInt("categoria_id"));
        s.setServicio(rs.getString("servicio"));
        s.setCosto(BigDecimal.valueOf(rs.getDouble("costo")));
        s.setFechaRenovacion(LocalDate.parse(rs.getString("fecha_renovacion")));
        s.setRecurrencia(rs.getString("recurrencia"));
        s.setCategoriaNombre(rs.getString("categoria_nombre"));
        return s;
    }
}

