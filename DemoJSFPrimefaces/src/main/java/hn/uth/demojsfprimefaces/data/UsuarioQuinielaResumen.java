package hn.uth.demojsfprimefaces.data;

import java.io.Serializable;

public class UsuarioQuinielaResumen implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int posicion;
    private final String usuario;
    private final int puntosTotales;
    private final int aciertosExactosTotales;
    private final int aciertosGanadorTotales;
    private final int aciertosGolesEquipoTotales;
    private final int partidosEvaluadosTotales;
    private final double puntosPorPartido;
    private final double porcentajeRespectoLider;

    public UsuarioQuinielaResumen(int posicion,
                                  String usuario,
                                  int puntosTotales,
                                  int aciertosExactosTotales,
                                  int aciertosGanadorTotales,
                                  int aciertosGolesEquipoTotales,
                                  int partidosEvaluadosTotales,
                                  double puntosPorPartido,
                                  double porcentajeRespectoLider) {
        this.posicion = posicion;
        this.usuario = usuario;
        this.puntosTotales = puntosTotales;
        this.aciertosExactosTotales = aciertosExactosTotales;
        this.aciertosGanadorTotales = aciertosGanadorTotales;
        this.aciertosGolesEquipoTotales = aciertosGolesEquipoTotales;
        this.partidosEvaluadosTotales = partidosEvaluadosTotales;
        this.puntosPorPartido = puntosPorPartido;
        this.porcentajeRespectoLider = porcentajeRespectoLider;
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

    public double getPuntosPorPartido() {
        return puntosPorPartido;
    }

    public double getPorcentajeRespectoLider() {
        return porcentajeRespectoLider;
    }
}

