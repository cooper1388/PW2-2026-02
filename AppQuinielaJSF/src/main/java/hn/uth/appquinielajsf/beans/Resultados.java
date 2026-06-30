package hn.uth.appquinielajsf.beans;

import hn.uth.appquinielajsf.data.Partido;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("ResultadosBean")
@SessionScoped
public class Resultados implements Serializable {
    @Inject
    private QuinielaStore quinielaStoreBean;

    private Partido partidoSeleccionado;

    public Resultados() {
        limpiarCampos();
    }

    public Partido getPartidoSeleccionado() {
        return partidoSeleccionado;
    }

    public void setPartidoSeleccionado(Partido partidoSeleccionado) {
        this.partidoSeleccionado = partidoSeleccionado;
    }

    public void registrarMarcador() {
        if (this.partidoSeleccionado == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione un partido para registrar el resultado.", null));
            return;
        }

        quinielaStoreBean.registrarResultado(this.partidoSeleccionado);
        limpiarCampos();
    }

    private void limpiarCampos() {
        this.partidoSeleccionado = null;

    }

    public List<Partido> getResultados() {
        return quinielaStoreBean.getResultados();
    }

    public void setResultados(List<Partido> resultados) {
        quinielaStoreBean.reemplazarResultados(resultados);
    }
}
