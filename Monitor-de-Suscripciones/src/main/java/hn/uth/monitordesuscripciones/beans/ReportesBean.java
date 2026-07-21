package hn.uth.monitordesuscripciones.beans;

import hn.uth.monitordesuscripciones.data.Suscripcion;
import hn.uth.monitordesuscripciones.services.ReporteService;
import hn.uth.monitordesuscripciones.services.SuscripcionService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ReportesBean {
    private final SuscripcionService suscripcionService = new SuscripcionService();
    private final ReporteService reporteService = new ReporteService();

    @Inject
    private SessionBean sessionBean;

    private List<Suscripcion> suscripciones = new ArrayList<>();

    @PostConstruct
    public void init() {
        if (sessionBean.isLogueado()) {
            suscripciones = suscripcionService.listarPorUsuario(sessionBean.getUsuario().getId());
        }
    }

    public void exportarPdf() {
        exportar(reporteService.generarPdf(suscripciones), "reporte_suscripciones.pdf", "application/pdf");
    }

    public void exportarExcel() {
        exportar(reporteService.generarExcel(suscripciones), "reporte_suscripciones.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    private void exportar(byte[] data, String fileName, String contentType) {
        try {
            HttpServletResponse response = (HttpServletResponse) jakarta.faces.context.FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
            response.reset();
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentLength(data.length);
            try (OutputStream output = response.getOutputStream()) {
                output.write(data);
                output.flush();
            }
            jakarta.faces.context.FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception ex) {
            throw new IllegalStateException("No se pudo exportar el archivo", ex);
        }
    }

    public List<Suscripcion> getSuscripciones() {
        return suscripciones;
    }
}

