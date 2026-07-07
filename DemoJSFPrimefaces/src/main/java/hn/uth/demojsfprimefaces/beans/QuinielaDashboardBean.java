package hn.uth.demojsfprimefaces.beans;

import hn.uth.demojsfprimefaces.data.QuinielaDashboardData;
import hn.uth.demojsfprimefaces.data.QuinielaDashboardService;
import hn.uth.demojsfprimefaces.data.UsuarioQuinielaResumen;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Named
@SessionScoped
public class QuinielaDashboardBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Path CSV_PATH = Path.of("C:/app-quiniela/tabla.csv");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
    private static final List<String> BAR_COLORS = List.of(
            "#3B82F6", "#8B5CF6", "#10B981", "#F59E0B", "#EF4444",
            "#0EA5E9", "#14B8A6", "#6366F1", "#84CC16", "#EC4899"
    );

    private final QuinielaDashboardService service = new QuinielaDashboardService();

    private QuinielaDashboardData dashboardData;
    private String rankingChartJson;
    private String aciertosChartJson;
    private String estadoCarga;
    private boolean dataDisponible;

    @PostConstruct
    public void init() {
        reload();
    }

    public String reload() {
        try {
            dashboardData = service.load(CSV_PATH);
            rankingChartJson = buildRankingChartJson(getTopUsuarios());
            aciertosChartJson = buildAciertosChartJson(dashboardData.getAciertosPorTipo());
            dataDisponible = true;
            estadoCarga = String.format(Locale.ROOT,
                    "CSV cargado correctamente desde %s (%d usuarios, %d registros, %d líneas omitidas).",
                    dashboardData.getSourceFile(),
                    dashboardData.getUsuariosAnalizados(),
                    dashboardData.getRegistrosCargados(),
                    dashboardData.getLineasOmitidas());
            addMessage(FacesMessage.SEVERITY_INFO, "Dashboard actualizado", estadoCarga);
        } catch (IOException ex) {
            dashboardData = null;
            rankingChartJson = emptyChartJson();
            aciertosChartJson = emptyChartJson();
            dataDisponible = false;
            estadoCarga = "No se pudo leer el archivo CSV: " + ex.getMessage();
            addMessage(FacesMessage.SEVERITY_ERROR, "Archivo no encontrado", estadoCarga);
        } catch (RuntimeException ex) {
            dashboardData = null;
            rankingChartJson = emptyChartJson();
            aciertosChartJson = emptyChartJson();
            dataDisponible = false;
            estadoCarga = "No se pudo procesar el CSV: " + ex.getMessage();
            addMessage(FacesMessage.SEVERITY_ERROR, "Error de procesamiento", estadoCarga);
        }
        return null;
    }

    public List<UsuarioQuinielaResumen> getRankingUsuarios() {
        return dashboardData == null ? Collections.emptyList() : dashboardData.getRankingUsuarios();
    }

    public List<UsuarioQuinielaResumen> getTopUsuarios() {
        return getRankingUsuarios().stream().limit(5).toList();
    }

    public List<UsuarioQuinielaResumen> getPeoresUsuarios() {
        List<UsuarioQuinielaResumen> ranking = getRankingUsuarios();
        if (ranking.isEmpty()) {
            return Collections.emptyList();
        }
        int fromIndex = Math.max(0, ranking.size() - 5);
        return ranking.subList(fromIndex, ranking.size());
    }

    public UsuarioQuinielaResumen getMejorUsuario() {
        return dashboardData == null ? null : dashboardData.getMejorUsuario();
    }

    public UsuarioQuinielaResumen getPeorUsuario() {
        return dashboardData == null ? null : dashboardData.getPeorUsuario();
    }

    public String getRankingChartJson() {
        return rankingChartJson;
    }

    public String getAciertosChartJson() {
        return aciertosChartJson;
    }

    public String getEstadoCarga() {
        return estadoCarga;
    }

    public boolean isDataDisponible() {
        return dataDisponible;
    }

    public int getTotalUsuarios() {
        return dashboardData == null ? 0 : dashboardData.getUsuariosAnalizados();
    }

    public int getTotalRegistros() {
        return dashboardData == null ? 0 : dashboardData.getRegistrosCargados();
    }

    public int getTotalPuntos() {
        return dashboardData == null ? 0 : dashboardData.getPuntosTotales();
    }

    public double getPromedioPuntos() {
        return dashboardData == null ? 0.0d : dashboardData.getPromedioPuntos();
    }

    public double getMedianaPuntos() {
        return dashboardData == null ? 0.0d : dashboardData.getMedianaPuntos();
    }

    public int getLineasOmitidas() {
        return dashboardData == null ? 0 : dashboardData.getLineasOmitidas();
    }

    public String getFuenteDatos() {
        return dashboardData == null ? CSV_PATH.toAbsolutePath().toString() : dashboardData.getSourceFile();
    }

    public String getFechaGeneracion() {
        return dashboardData == null || dashboardData.getFechaGeneracion() == null
                ? "-"
                : dashboardData.getFechaGeneracion().format(DATE_TIME_FORMATTER);
    }

    public String getTopUsuarioLabel() {
        UsuarioQuinielaResumen mejor = getMejorUsuario();
        return mejor == null ? "Sin datos" : mejor.getUsuario();
    }

    public String getWorstUsuarioLabel() {
        UsuarioQuinielaResumen peor = getPeorUsuario();
        return peor == null ? "Sin datos" : peor.getUsuario();
    }

    public String getTopUsuarioDetalle() {
        UsuarioQuinielaResumen mejor = getMejorUsuario();
        return mejor == null ? "" : String.format(Locale.ROOT, "%d puntos · %d exactos · %d partidos", mejor.getPuntosTotales(), mejor.getAciertosExactosTotales(), mejor.getPartidosEvaluadosTotales());
    }

    public String getWorstUsuarioDetalle() {
        UsuarioQuinielaResumen peor = getPeorUsuario();
        return peor == null ? "" : String.format(Locale.ROOT, "%d puntos · %d exactos · %d partidos", peor.getPuntosTotales(), peor.getAciertosExactosTotales(), peor.getPartidosEvaluadosTotales());
    }

    private String buildRankingChartJson(List<UsuarioQuinielaResumen> ranking) {
        JsonArrayBuilder labels = Json.createArrayBuilder();
        JsonArrayBuilder values = Json.createArrayBuilder();
        JsonArrayBuilder colors = Json.createArrayBuilder();

        List<UsuarioQuinielaResumen> top = ranking.stream().limit(10).toList();
        for (int i = 0; i < top.size(); i++) {
            UsuarioQuinielaResumen usuario = top.get(i);
            labels.add(usuario.getUsuario());
            values.add(usuario.getPuntosTotales());
            colors.add(BAR_COLORS.get(i % BAR_COLORS.size()));
        }

        JsonObject options = Json.createObjectBuilder()
                .add("responsive", true)
                .add("maintainAspectRatio", false)
                .add("plugins", Json.createObjectBuilder()
                        .add("legend", Json.createObjectBuilder().add("display", false)))
                .add("scales", Json.createObjectBuilder()
                        .add("y", Json.createObjectBuilder().add("beginAtZero", true)))
                .build();

        JsonObject data = Json.createObjectBuilder()
                .add("labels", labels)
                .add("datasets", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("label", "Puntos totales")
                                .add("data", values)
                                .add("backgroundColor", colors)
                                .add("borderColor", "#1D4ED8")
                                .add("borderWidth", 1)))
                .build();

        return Json.createObjectBuilder()
                .add("type", "bar")
                .add("data", data)
                .add("options", options)
                .build()
                .toString();
    }

    private String buildAciertosChartJson(Map<String, Long> aciertosPorTipo) {
        Map<String, String> palette = new LinkedHashMap<>();
        palette.put("EXACTO", "#22C55E");
        palette.put("GANADOR", "#3B82F6");
        palette.put("GOL_EQUIPO", "#F59E0B");
        palette.put("SIN_ACIERTO", "#EF4444");

        JsonArrayBuilder labels = Json.createArrayBuilder();
        JsonArrayBuilder values = Json.createArrayBuilder();
        JsonArrayBuilder colors = Json.createArrayBuilder();

        List<String> orderedKeys = new ArrayList<>(palette.keySet());
        for (String key : aciertosPorTipo.keySet()) {
            if (!orderedKeys.contains(key)) {
                orderedKeys.add(key);
            }
        }

        for (String key : orderedKeys) {
            Long value = aciertosPorTipo.get(key);
            if (value == null) {
                continue;
            }
            labels.add(key);
            values.add(value);
            colors.add(palette.getOrDefault(key, "#6366F1"));
        }

        JsonObject options = Json.createObjectBuilder()
                .add("responsive", true)
                .add("maintainAspectRatio", false)
                .add("plugins", Json.createObjectBuilder()
                        .add("legend", Json.createObjectBuilder().add("position", "bottom")))
                .build();

        JsonObject data = Json.createObjectBuilder()
                .add("labels", labels)
                .add("datasets", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("data", values)
                                .add("backgroundColor", colors)
                                .add("hoverOffset", 12)))
                .build();

        return Json.createObjectBuilder()
                .add("type", "doughnut")
                .add("data", data)
                .add("options", options)
                .build()
                .toString();
    }

    private String emptyChartJson() {
        return Json.createObjectBuilder()
                .add("type", "bar")
                .add("data", Json.createObjectBuilder()
                        .add("labels", Json.createArrayBuilder())
                        .add("datasets", Json.createArrayBuilder()
                                .add(Json.createObjectBuilder()
                                        .add("label", "Sin datos")
                                        .add("data", Json.createArrayBuilder()))))
                .add("options", Json.createObjectBuilder().add("responsive", true))
                .build()
                .toString();
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            context.addMessage(null, new FacesMessage(severity, summary, detail));
        }
    }
}

