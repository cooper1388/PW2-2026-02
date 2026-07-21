package hn.uth.monitordesuscripciones.services;

import hn.uth.monitordesuscripciones.data.Usuario;
import hn.uth.monitordesuscripciones.database.UsuarioDAO;

public class AuthService {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario login(String correo, String password) {
        Usuario usuario = usuarioDAO.findByCorreo(correo);
        if (usuario == null) {
            return null;
        }
        if (!usuario.isAprobado()) {
            return null;
        }
        if (!usuario.getPassword().equals(password)) {
            return null;
        }
        return usuario;
    }

    public boolean registrar(String nombre, String correo, String password) {
        Usuario existente = usuarioDAO.findByCorreo(correo);
        if (existente != null) {
            return false;
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setPassword(password);
        usuario.setRolId(2);
        usuario.setAprobado(false);
        usuarioDAO.crear(usuario);
        return true;
    }
}

