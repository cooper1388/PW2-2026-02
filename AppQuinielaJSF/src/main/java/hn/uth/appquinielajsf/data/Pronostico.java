package hn.uth.appquinielajsf.data;

import java.util.Date;
import java.io.Serializable;

public class Pronostico implements Serializable {
    private int golesRival1;
    private int golesRival2;
    private Date fechaHoraPronostico;
    private String usuario;
    private Partido partido;

    public Pronostico() {
        this.golesRival1 = 0;
        this.golesRival2 = 0;
        this.fechaHoraPronostico = new Date();
        this.usuario = "";
        this.partido = null;
    }

    public int getGolesRival1() {
        return golesRival1;
    }

    public void setGolesRival1(int golesRival1) {
        this.golesRival1 = golesRival1;
    }

    public int getGolesRival2() {
        return golesRival2;
    }

    public void setGolesRival2(int golesRival2) {
        this.golesRival2 = golesRival2;
    }

    public Date getFechaHoraPronostico() {
        return fechaHoraPronostico;
    }

    public void setFechaHoraPronostico(Date fechaHoraPronostico) {
        this.fechaHoraPronostico = fechaHoraPronostico;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }
}
