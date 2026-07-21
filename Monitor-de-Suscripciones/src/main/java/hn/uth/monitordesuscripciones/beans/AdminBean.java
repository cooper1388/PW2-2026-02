package hn.uth.monitordesuscripciones.beans;

import hn.uth.monitordesuscripciones.data.Usuario;
import hn.uth.monitordesuscripciones.services.AdminService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class AdminBean implements Serializable {
    private final AdminService adminService = new AdminService();

    private List<Usuario> pendientes = new ArrayList<>();
    private String nuevaPassword = "temporal123";

    @PostConstruct
    public void init() {
        recargar();
    }

    public void recargar() {
        pendientes = adminService.listarPendientes();
    }

    public void aprobar(Usuario usuario) {
        adminService.aprobarUsuario(usuario.getId());
        recargar();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario aprobado", null));
    }

    public void resetPassword(Usuario usuario) {
        adminService.resetPassword(usuario.getId(), nuevaPassword);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Password reseteado", null));
    }

    public List<Usuario> getPendientes() {
        return pendientes;
    }

    public String getNuevaPassword() {
        return nuevaPassword;
    }

    public void setNuevaPassword(String nuevaPassword) {
        this.nuevaPassword = nuevaPassword;
    }
}

