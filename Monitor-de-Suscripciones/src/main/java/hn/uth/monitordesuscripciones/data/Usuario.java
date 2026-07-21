package hn.uth.monitordesuscripciones.data;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String password;
    private int rolId;
    private boolean aprobado;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String correo, String password, int rolId, boolean aprobado) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.rolId = rolId;
        this.aprobado = aprobado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public boolean isAdmin() {
        return rolId == 1;
    }
}

