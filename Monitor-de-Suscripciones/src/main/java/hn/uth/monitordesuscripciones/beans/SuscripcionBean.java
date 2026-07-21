package hn.uth.monitordesuscripciones.beans;

import hn.uth.monitordesuscripciones.data.Categoria;
import hn.uth.monitordesuscripciones.data.Suscripcion;
import hn.uth.monitordesuscripciones.services.CategoriaService;
import hn.uth.monitordesuscripciones.services.SuscripcionService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class SuscripcionBean implements Serializable {
    private final SuscripcionService suscripcionService = new SuscripcionService();
    private final CategoriaService categoriaService = new CategoriaService();

    @Inject
    private SessionBean sessionBean;

    private List<Suscripcion> suscripciones = new ArrayList<>();
    private List<Categoria> categorias = new ArrayList<>();
    private Suscripcion suscripcionActual = new Suscripcion();

    @PostConstruct
    public void init() {
        if (!sessionBean.isLogueado()) {
            return;
        }
        categorias = categoriaService.listar();
        recargar();
        if (suscripcionActual.getRecurrencia() == null) {
            suscripcionActual.setRecurrencia("MENSUAL");
            suscripcionActual.setFechaRenovacion(LocalDate.now());
            suscripcionActual.setCosto(BigDecimal.ONE);
        }
    }

    public void recargar() {
        suscripciones = suscripcionService.listarPorUsuario(sessionBean.getUsuario().getId());
    }

    public void nueva() {
        suscripcionActual = new Suscripcion();
        suscripcionActual.setRecurrencia("MENSUAL");
        suscripcionActual.setFechaRenovacion(LocalDate.now());
        suscripcionActual.setCosto(BigDecimal.ONE);
    }

    public void editar(Suscripcion suscripcion) {
        suscripcionActual = suscripcionService.buscar(suscripcion.getId());
    }

    public void guardar() {
        try {
            suscripcionActual.setUsuarioId(sessionBean.getUsuario().getId());
            suscripcionService.guardar(suscripcionActual);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Suscripcion guardada", null));
            nueva();
            recargar();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void eliminar(Suscripcion suscripcion) {
        suscripcionService.eliminar(suscripcion.getId());
        recargar();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Suscripcion eliminada", null));
    }

    public List<Suscripcion> getSuscripciones() {
        return suscripciones;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public Suscripcion getSuscripcionActual() {
        return suscripcionActual;
    }

    public void setSuscripcionActual(Suscripcion suscripcionActual) {
        this.suscripcionActual = suscripcionActual;
    }
}

