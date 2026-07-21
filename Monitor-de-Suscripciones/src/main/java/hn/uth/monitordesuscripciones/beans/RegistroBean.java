package hn.uth.monitordesuscripciones.beans;

import hn.uth.monitordesuscripciones.services.AuthService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class RegistroBean {
    private String nombre;
    private String correo;
    private String password;

    private final AuthService authService = new AuthService();

    public String registrar() {
        boolean creado = authService.registrar(nombre, correo, password);
        if (!creado) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "El correo ya existe", null));
            return null;
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro exitoso. Un administrador debe aprobar tu cuenta.", null));
        return "index.xhtml?faces-redirect=true";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

