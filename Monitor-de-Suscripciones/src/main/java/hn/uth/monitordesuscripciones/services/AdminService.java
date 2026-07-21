package hn.uth.monitordesuscripciones.services;

import hn.uth.monitordesuscripciones.data.Usuario;
import hn.uth.monitordesuscripciones.database.UsuarioDAO;

import java.util.List;

public class AdminService {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public List<Usuario> listarPendientes() {
        return usuarioDAO.listarPendientesAprobacion();
    }

    public void aprobarUsuario(int idUsuario) {
        usuarioDAO.aprobar(idUsuario);
    }

    public void resetPassword(int idUsuario, String nuevaPassword) {
        usuarioDAO.resetPassword(idUsuario, nuevaPassword);
    }
}

