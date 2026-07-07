package hn.uth.demojsfprimefaces.data;

import java.io.Serializable;
import java.time.LocalDateTime;

public class QuinielaRegistro implements Serializable {

    private static final long serialVersionUID = 1L;

    private final LocalDateTime fechaGeneracion;
    private final int posicion;
    private final String usuario;
    private final int puntosTotales;
    private final int aciertosExactosTotales;
    private final int aciertosGanadorTotales;
    private final int aciertosGolesEquipoTotales;
    private final int partidosEvaluadosTotales;
    private final String rival1;
    private final String rival2;
    private final LocalDateTime fechaPartido;
    private final LocalDateTime fechaPronostico;
    private final int golesPronosticadosRival1;
    private final int golesPronosticadosRival2;
    private final int golesRealesRival1;
    private final int golesRealesRival2;
    private final String tipoAcierto;
    private final int puntosOtorgados;

    public QuinielaRegistro(LocalDateTime fechaGeneracion,
                            int posicion,
                            String usuario,
                            int puntosTotales,
                            int aciertosExactosTotales,
                            int aciertosGanadorTotales,
                            int aciertosGolesEquipoTotales,
                            int partidosEvaluadosTotales,
                            String rival1,
                            String rival2,
                            LocalDateTime fechaPartido,
                            LocalDateTime fechaPronostico,
                            int golesPronosticadosRival1,
                            int golesPronosticadosRival2,
                            int golesRealesRival1,
                            int golesRealesRival2,
                            String tipoAcierto,
                            int puntosOtorgados) {
        this.fechaGeneracion = fechaGeneracion;
        this.posicion = posicion;
        this.usuario = usuario;
        this.puntosTotales = puntosTotales;
        this.aciertosExactosTotales = aciertosExactosTotales;
        this.aciertosGanadorTotales = aciertosGanadorTotales;
        this.aciertosGolesEquipoTotales = aciertosGolesEquipoTotales;
        this.partidosEvaluadosTotales = partidosEvaluadosTotales;
        this.rival1 = rival1;
        this.rival2 = rival2;
        this.fechaPartido = fechaPartido;
        this.fechaPronostico = fechaPronostico;
        this.golesPronosticadosRival1 = golesPronosticadosRival1;
        this.golesPronosticadosRival2 = golesPronosticadosRival2;
        this.golesRealesRival1 = golesRealesRival1;
        this.golesRealesRival2 = golesRealesRival2;
        this.tipoAcierto = tipoAcierto;
        this.puntosOtorgados = puntosOtorgados;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public int getPosicion() {
        return posicion;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public int getAciertosExactosTotales() {
        return aciertosExactosTotales;
    }

    public int getAciertosGanadorTotales() {
        return aciertosGanadorTotales;
    }

    public int getAciertosGolesEquipoTotales() {
        return aciertosGolesEquipoTotales;
    }

    public int getPartidosEvaluadosTotales() {
        return partidosEvaluadosTotales;
    }

    public String getRival1() {
        return rival1;
    }

    public String getRival2() {
        return rival2;
    }

    public LocalDateTime getFechaPartido() {
        return fechaPartido;
    }

    public LocalDateTime getFechaPronostico() {
        return fechaPronostico;
    }

    public int getGolesPronosticadosRival1() {
        return golesPronosticadosRival1;
    }

    public int getGolesPronosticadosRival2() {
        return golesPronosticadosRival2;
    }

    public int getGolesRealesRival1() {
        return golesRealesRival1;
    }

    public int getGolesRealesRival2() {
        return golesRealesRival2;
    }

    public String getTipoAcierto() {
        return tipoAcierto;
    }

    public int getPuntosOtorgados() {
        return puntosOtorgados;
    }
}

