package hn.uth.monitordesuscripciones.database;

import hn.uth.monitordesuscripciones.data.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public CategoriaDAO() {
        DatabaseInitializer.initialize();
    }

    public List<Categoria> listar() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion FROM categorias ORDER BY nombre";
        try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                categorias.add(new Categoria(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion")));
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Error al listar categorias", ex);
        }
        return categorias;
    }

    public void crear(Categoria categoria) {
        String sql = "INSERT INTO categorias(nombre, descripcion) VALUES(?, ?)";
        try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new IllegalStateException("Error al crear categoria", ex);
        }
    }

    public void actualizar(Categoria categoria) {
        String sql = "UPDATE categorias SET nombre = ?, descripcion = ? WHERE id = ?";
        try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setInt(3, categoria.getId());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new IllegalStateException("Error al actualizar categoria", ex);
        }
    }

    public void eliminar(int idCategoria) {
        String sql = "DELETE FROM categorias WHERE id = ?";
        try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new IllegalStateException("Error al eliminar categoria", ex);
        }
    }
}

