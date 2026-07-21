package hn.uth.monitordesuscripciones.services;

import hn.uth.monitordesuscripciones.data.Categoria;
import hn.uth.monitordesuscripciones.database.CategoriaDAO;

import java.util.List;

public class CategoriaService {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    public List<Categoria> listar() {
        return categoriaDAO.listar();
    }

    public void guardar(Categoria categoria) {
        if (categoria.getId() == 0) {
            categoriaDAO.crear(categoria);
        } else {
            categoriaDAO.actualizar(categoria);
        }
    }

    public void eliminar(int idCategoria) {
        categoriaDAO.eliminar(idCategoria);
    }
}

