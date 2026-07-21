package hn.uth.monitordesuscripciones.beans;

import hn.uth.monitordesuscripciones.data.Usuario;
import hn.uth.monitordesuscripciones.services.AuthService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class LoginBean {
    private String correo;
    private String password;

    private final AuthService authService = new AuthService();

    @Inject
    private SessionBean sessionBean;

    public String login() {
        Usuario usuario = authService.login(correo, password);
        if (usuario == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Credenciales invalidas o cuenta pendiente de aprobacion", null));
            return null;
        }
        sessionBean.setUsuario(usuario);
        return "dashboard.xhtml?faces-redirect=true";
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

