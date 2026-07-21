package hn.uth.monitordesuscripciones.data;

import java.time.LocalDate;

public class HistorialUso {
    private int id;
    private int suscripcionId;
    private LocalDate fechaUso;
    private boolean usada;

    public HistorialUso() {
    }

    public HistorialUso(int id, int suscripcionId, LocalDate fechaUso, boolean usada) {
        this.id = id;
        this.suscripcionId = suscripcionId;
        this.fechaUso = fechaUso;
        this.usada = usada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(int suscripcionId) {
        this.suscripcionId = suscripcionId;
    }

    public LocalDate getFechaUso() {
        return fechaUso;
    }

    public void setFechaUso(LocalDate fechaUso) {
        this.fechaUso = fechaUso;
    }

    public boolean isUsada() {
        return usada;
    }

    public void setUsada(boolean usada) {
        this.usada = usada;
    }
}

