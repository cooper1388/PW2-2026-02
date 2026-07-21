package hn.uth.monitordesuscripciones.beans;

import hn.uth.monitordesuscripciones.data.Usuario;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class SessionBean implements Serializable {
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isLogueado() {
        return usuario != null;
    }

    public boolean isAdmin() {
        return isLogueado() && usuario.isAdmin();
    }

    public String logout() {
        usuario = null;
        return "index.xhtml?faces-redirect=true";
    }
}

