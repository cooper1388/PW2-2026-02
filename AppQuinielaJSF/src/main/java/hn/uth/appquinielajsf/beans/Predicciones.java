package hn.uth.appquinielajsf.beans;

import hn.uth.appquinielajsf.data.Partido;
import hn.uth.appquinielajsf.data.Pronostico;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("PrediccionesBean")
@SessionScoped
public class Predicciones implements Serializable {
    @Inject
    private Login loginBean;

    private Partido partidoSeleccionado;
    private List<Pronostico> pronosticos;
    private Pronostico pronosticoActual;

    public Predicciones() {
        this.pronosticos = new ArrayList<>();
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

        pronosticoActual.setUsuario(loginBean.getCorreo());
        pronosticoActual.setPartido(this.partidoSeleccionado);
        pronosticoActual.setFechaHoraPronostico(new java.util.Date());
        this.pronosticos.add(pronosticoActual);
        limpiarCampos();
    }

    private void limpiarCampos() {
        this.partidoSeleccionado = null;
        this.pronosticoActual = new Pronostico();
    }

    public List<Pronostico> getPronosticos() {
        return pronosticos;
    }

    public void setPronosticos(List<Pronostico> pronosticos) {
        this.pronosticos = pronosticos;
    }

    public Pronostico getPronosticoActual() {
        return pronosticoActual;
    }

    public void setPronosticoActual(Pronostico pronosticoActual) {
        this.pronosticoActual = pronosticoActual;
    }
}
