package hn.uth.appquinielajsf.data;

import java.io.Serializable;

public class PosicionUsuario implements Serializable {
    private int posicion;
    private String usuario;
    private int puntos;
    private int aciertosExactos;
    private int aciertosGanador;
    private int aciertosGolesEquipo;
    private int partidosEvaluados;

    public PosicionUsuario() {
        this.usuario = "";
    }

    public PosicionUsuario(String usuario, int puntos, int aciertosExactos, int aciertosGanador,
                           int aciertosGolesEquipo, int partidosEvaluados) {
        this.usuario = usuario;
        this.puntos = puntos;
        this.aciertosExactos = aciertosExactos;
        this.aciertosGanador = aciertosGanador;
        this.aciertosGolesEquipo = aciertosGolesEquipo;
        this.partidosEvaluados = partidosEvaluados;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getAciertosExactos() {
        return aciertosExactos;
    }

    public void setAciertosExactos(int aciertosExactos) {
        this.aciertosExactos = aciertosExactos;
    }

    public int getAciertosGanador() {
        return aciertosGanador;
    }

    public void setAciertosGanador(int aciertosGanador) {
        this.aciertosGanador = aciertosGanador;
    }

    public int getAciertosGolesEquipo() {
        return aciertosGolesEquipo;
    }

    public void setAciertosGolesEquipo(int aciertosGolesEquipo) {
        this.aciertosGolesEquipo = aciertosGolesEquipo;
    }

    public int getPartidosEvaluados() {
        return partidosEvaluados;
    }

    public void setPartidosEvaluados(int partidosEvaluados) {
        this.partidosEvaluados = partidosEvaluados;
    }
}

