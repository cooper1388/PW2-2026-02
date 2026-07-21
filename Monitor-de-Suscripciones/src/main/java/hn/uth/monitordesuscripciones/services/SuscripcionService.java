package hn.uth.monitordesuscripciones.services;

import hn.uth.monitordesuscripciones.data.Suscripcion;
import hn.uth.monitordesuscripciones.database.SuscripcionDAO;

import java.math.BigDecimal;
import java.util.List;

public class SuscripcionService {
    private final SuscripcionDAO suscripcionDAO = new SuscripcionDAO();

    public List<Suscripcion> listarPorUsuario(int usuarioId) {
        return suscripcionDAO.listarPorUsuario(usuarioId);
    }

    public Suscripcion buscar(int id) {
        return suscripcionDAO.findById(id);
    }

    public void guardar(Suscripcion suscripcion) {
        validar(suscripcion);
        if (suscripcion.getId() == 0) {
            suscripcionDAO.crear(suscripcion);
        } else {
            suscripcionDAO.actualizar(suscripcion);
        }
    }

    public void eliminar(int id) {
        suscripcionDAO.eliminar(id);
    }

    private void validar(Suscripcion suscripcion) {
        if (suscripcion.getServicio() == null || suscripcion.getServicio().trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar el nombre del servicio");
        }
        if (suscripcion.getCosto() == null || suscripcion.getCosto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El costo debe ser mayor que 0");
        }
        if (suscripcion.getFechaRenovacion() == null) {
            throw new IllegalArgumentException("Debe seleccionar fecha de renovacion");
        }
        if (suscripcion.getCategoriaId() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar categoria");
        }
    }
}

