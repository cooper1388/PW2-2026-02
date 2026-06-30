package hn.uth.appquinielajsf.beans;

import hn.uth.appquinielajsf.data.Partido;
import hn.uth.appquinielajsf.data.Pronostico;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("PrediccionesBean")
@SessionScoped
public class Predicciones implements Serializable {
    @Inject
    private Login loginBean;

    @Inject
    private QuinielaStore quinielaStoreBean;

    private Partido partidoSeleccionado;
    private Pronostico pronosticoActual;

    public Predicciones() {
        limpiarCampos();
    }

    public Partido getPartidoSeleccionado() {
        return partidoSeleccionado;
    }

    public void setPartidoSeleccionado(Partido partidoSeleccionado) {
        this.partidoSeleccionado = partidoSeleccionado;
    }

    public void registrarPrediccion() {
        if (loginBean == null || !loginBean.isAutenticado()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe iniciar sesion con un correo valido.", null));
            return;
        }

        if (this.partidoSeleccionado == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione un partido para registrar el pronostico.", null));
            return;
        }

        pronosticoActual.setUsuario(loginBean.getCorreo());
        pronosticoActual.setPartido(this.partidoSeleccionado);
        pronosticoActual.setFechaHoraPronostico(new java.util.Date());
        quinielaStoreBean.registrarPronostico(pronosticoActual);
        limpiarCampos();
    }

    private void limpiarCampos() {
        this.partidoSeleccionado = null;
        this.pronosticoActual = new Pronostico();
    }

    public List<Pronostico> getPronosticos() {
        return quinielaStoreBean.getPronosticos();
    }

    public void setPronosticos(List<Pronostico> pronosticos) {
        quinielaStoreBean.reemplazarPronosticos(pronosticos);
    }

    public Pronostico getPronosticoActual() {
        return pronosticoActual;
    }

    public void setPronosticoActual(Pronostico pronosticoActual) {
        this.pronosticoActual = pronosticoActual;
    }
}
