package hn.uth.appquinielajsf.beans;

import hn.uth.appquinielajsf.data.Partido;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("ResultadosBean")
@SessionScoped
public class Resultados implements Serializable {
    private Partido partidoSeleccionado;
    private List<Partido> resultados;

    public Resultados() {
        limpiarCampos();
        this.resultados = new ArrayList<>();
    }

    public Partido getPartidoSeleccionado() {
        return partidoSeleccionado;
    }

    public void setPartidoSeleccionado(Partido partidoSeleccionado) {
        this.partidoSeleccionado = partidoSeleccionado;
    }

    public void registrarMarcador() {
        for (int i = 0; i < this.resultados.size(); i++) {
            if (this.resultados.get(i).getRival1().equals(this.partidoSeleccionado.getRival1()) &&
                    this.resultados.get(i).getRival2().equals(this.partidoSeleccionado.getRival2())) {
                this.resultados.set(i, this.partidoSeleccionado);
                limpiarCampos();
                return;
            }
        }
        this.resultados.add(this.partidoSeleccionado);
        limpiarCampos();
    }

    private void limpiarCampos() {
        this.partidoSeleccionado = null;

    }

    public List<Partido> getResultados() {
        return resultados;
    }

    public void setResultados(List<Partido> resultados) {
        this.resultados = resultados;
    }
}
