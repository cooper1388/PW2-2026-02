package hn.uth.monitordesuscripciones.beans;

import hn.uth.monitordesuscripciones.data.Suscripcion;
import hn.uth.monitordesuscripciones.services.GastosFantasmaService;
import hn.uth.monitordesuscripciones.services.SuscripcionService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class GastosFantasmaBean implements Serializable {
    private final SuscripcionService suscripcionService = new SuscripcionService();
    private final GastosFantasmaService gastosFantasmaService = new GastosFantasmaService();

    @Inject
    private SessionBean sessionBean;

    private int dias = 7;
    private List<Suscripcion> suscripciones = new ArrayList<>();
    private List<Suscripcion> noUsadas = new ArrayList<>();

    @PostConstruct
    public void init() {
        if (sessionBean.isLogueado()) {
            suscripciones = suscripcionService.listarPorUsuario(sessionBean.getUsuario().getId());
            recargarNoUsadas();
        }
    }

    public void marcarUso(Suscripcion suscripcion, boolean usada) {
        gastosFantasmaService.marcarUsoHoy(suscripcion.getId(), usada);
        recargarNoUsadas();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Uso actualizado para hoy", null));
    }

    public void recargarNoUsadas() {
        noUsadas = gastosFantasmaService.noUsadas(sessionBean.getUsuario().getId(), dias);
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public List<Suscripcion> getSuscripciones() {
        return suscripciones;
    }

    public List<Suscripcion> getNoUsadas() {
        return noUsadas;
    }
}

