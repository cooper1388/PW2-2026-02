package hn.uth.appquinielajsf.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.util.regex.Pattern;

@Named("LoginBean")
@SessionScoped
public class Login implements Serializable {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private String correo;

    @Inject
    private CsvDataLoader csvDataLoader;

    public Login() {
        this.correo = "";
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isAutenticado() {
        return correo != null && !correo.isBlank();
    }

    public String iniciarSesion() {
        String correoNormalizado = correo == null ? "" : correo.trim().toLowerCase();
        if (!EMAIL_PATTERN.matcher(correoNormalizado).matches()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese un correo electronico valido.", null));
            return null;
        }

        this.correo = correoNormalizado;
        return "/index.xhtml?faces-redirect=true";
    }

    public String cerrarSesion() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

    public void validarSesion() {
        csvDataLoader.inicializarSiNecesario();
        if (!isAutenticado()) {
            redirigir("/login.xhtml");
        }
    }

    public void redirigirSiAutenticado() {
        csvDataLoader.inicializarSiNecesario();
        if (isAutenticado()) {
            redirigir("/index.xhtml");
        }
    }

    private void redirigir(String pagina) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        try {
            externalContext.redirect(externalContext.getRequestContextPath() + pagina);
            facesContext.responseComplete();
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo redirigir a " + pagina, e);
        }
    }
}

