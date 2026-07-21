package hn.uth.monitordesuscripciones.database;

import hn.uth.monitordesuscripciones.data.Suscripcion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistorialUsoDAO {

    public HistorialUsoDAO() {
        DatabaseInitializer.initialize();
    }

    public void marcarUso(int suscripcionId, LocalDate fecha, boolean usada) {
        String sql = "INSERT INTO historial_uso_suscripciones(suscripcion_id, fecha_uso, usada) VALUES(?, ?, ?) " +
                "ON CONFLICT(suscripcion_id, fecha_uso) DO UPDATE SET usada = excluded.usada";
        try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, suscripcionId);
            ps.setString(2, fecha.toString());
            ps.setInt(3, usada ? 1 : 0);
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new IllegalStateException("Error al guardar uso", ex);
        }
    }

    public List<Suscripcion> listarNoUsadasEnPeriodo(int usuarioId, int dias) {
        List<Suscripcion> lista = new ArrayList<>();
        String sql = "SELECT s.id, s.usuario_id, s.categoria_id, s.servicio, s.costo, s.fecha_renovacion, s.recurrencia, c.nombre categoria_nombre " +
                "FROM suscripciones s " +
                "JOIN categorias c ON c.id = s.categoria_id " +
                "LEFT JOIN historial_uso_suscripciones h ON h.suscripcion_id = s.id AND h.fecha_uso >= ? AND h.usada = 1 " +
                "WHERE s.usuario_id = ? AND h.id IS NULL ORDER BY s.servicio";

        LocalDate inicio = LocalDate.now().minusDays(dias);
        try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, inicio.toString());
            ps.setInt(2, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Suscripcion s = new Suscripcion();
                    s.setId(rs.getInt("id"));
                    s.setUsuarioId(rs.getInt("usuario_id"));
                    s.setCategoriaId(rs.getInt("categoria_id"));
                    s.setServicio(rs.getString("servicio"));
                    s.setCosto(rs.getBigDecimal("costo"));
                    s.setFechaRenovacion(LocalDate.parse(rs.getString("fecha_renovacion")));
                    s.setRecurrencia(rs.getString("recurrencia"));
                    s.setCategoriaNombre(rs.getString("categoria_nombre"));
                    lista.add(s);
                }
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Error al consultar gastos fantasmas", ex);
        }
        return lista;
    }
}

