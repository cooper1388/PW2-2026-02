package hn.uth.monitordesuscripciones.beans;

import hn.uth.monitordesuscripciones.data.Suscripcion;
import hn.uth.monitordesuscripciones.services.DashboardService;
import hn.uth.monitordesuscripciones.services.SuscripcionService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class DashboardBean implements Serializable {
    private final SuscripcionService suscripcionService = new SuscripcionService();
    private final DashboardService dashboardService = new DashboardService();

    @Inject
    private SessionBean sessionBean;

    private List<Suscripcion> suscripciones = new ArrayList<>();
    private BigDecimal gastoMensual = BigDecimal.ZERO;
    private BigDecimal gastoAnual = BigDecimal.ZERO;
    private long proximosPagos;

    @PostConstruct
    public void init() {
        if (sessionBean.isLogueado()) {
            suscripciones = suscripcionService.listarPorUsuario(sessionBean.getUsuario().getId());
            gastoMensual = dashboardService.gastoMensual(suscripciones);
            gastoAnual = dashboardService.gastoAnual(suscripciones);
            proximosPagos = dashboardService.proximosPagos(suscripciones, 7);
        }
    }

    public List<Suscripcion> getSuscripciones() {
        return suscripciones;
    }

    public BigDecimal getGastoMensual() {
        return gastoMensual;
    }

    public BigDecimal getGastoAnual() {
        return gastoAnual;
    }

    public long getProximosPagos() {
        return proximosPagos;
    }
}

