package hn.uth.appquinielajsf.data;

import java.util.Date;
import java.util.Objects;

public class Partido  implements java.io.Serializable {
    private String rival1;
    private String rival2;
    private Date fechaHora;
    private int golesRival1;
    private int golesRival2;

    public Partido(String rival1, String rival2, Date fechaHora, int golesRival1, int golesRival2) {
        this.rival1 = rival1;
        this.rival2 = rival2;
        this.fechaHora = fechaHora;
        this.golesRival1 = golesRival1;
        this.golesRival2 = golesRival2;
    }

    public Partido() {
        this.rival1 = "";
        this.rival2 = "";
        this.fechaHora = new Date();
        this.golesRival1 = 0;
        this.golesRival2 = 0;
    }

    public String getRival1() {
        return rival1;
    }

    public void setRival1(String rival1) {
        this.rival1 = rival1;
    }

    public String getRival2() {
        return rival2;
    }

    public void setRival2(String rival2) {
        this.rival2 = rival2;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partido partido)) {
            return false;
        }
        return Objects.equals(rival1, partido.rival1)
                && Objects.equals(rival2, partido.rival2)
                && Objects.equals(fechaHora, partido.fechaHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rival1, rival2, fechaHora);
    }
}
