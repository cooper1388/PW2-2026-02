package hn.uth.monitordesuscripciones.beans;

import hn.uth.monitordesuscripciones.data.Categoria;
import hn.uth.monitordesuscripciones.services.CategoriaService;
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
public class CategoriaBean implements Serializable {
    private final CategoriaService categoriaService = new CategoriaService();

    private List<Categoria> categorias = new ArrayList<>();
    private Categoria categoriaActual = new Categoria();

    @PostConstruct
    public void init() {
        recargar();
    }

    public void recargar() {
        categorias = categoriaService.listar();
    }

    public void nueva() {
        categoriaActual = new Categoria();
    }

    public void editar(Categoria categoria) {
        categoriaActual = categoria;
    }

    public void guardar() {
        categoriaService.guardar(categoriaActual);
        recargar();
        nueva();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Categoria guardada", null));
    }

    public void eliminar(Categoria categoria) {
        categoriaService.eliminar(categoria.getId());
        recargar();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Categoria eliminada", null));
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public Categoria getCategoriaActual() {
        return categoriaActual;
    }

    public void setCategoriaActual(Categoria categoriaActual) {
        this.categoriaActual = categoriaActual;
    }
}

