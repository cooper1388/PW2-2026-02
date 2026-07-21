package hn.uth.monitordesuscripciones.services;

import hn.uth.monitordesuscripciones.data.Suscripcion;
import hn.uth.monitordesuscripciones.database.HistorialUsoDAO;

import java.time.LocalDate;
import java.util.List;

public class GastosFantasmaService {
    private final HistorialUsoDAO historialUsoDAO = new HistorialUsoDAO();

    public void marcarUsoHoy(int suscripcionId, boolean usada) {
        historialUsoDAO.marcarUso(suscripcionId, LocalDate.now(), usada);
    }

    public List<Suscripcion> noUsadas(int usuarioId, int dias) {
        return historialUsoDAO.listarNoUsadasEnPeriodo(usuarioId, dias);
    }
}

