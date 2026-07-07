package hn.uth.demojsfprimefaces.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QuinielaDashboardData implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String sourceFile;
    private final LocalDateTime fechaGeneracion;
    private final int registrosCargados;
    private final int usuariosAnalizados;
    private final int puntosTotales;
    private final double promedioPuntos;
    private final double medianaPuntos;
    private final int lineasOmitidas;
    private final List<UsuarioQuinielaResumen> rankingUsuarios;
    private final Map<String, Long> aciertosPorTipo;
    private final UsuarioQuinielaResumen mejorUsuario;
    private final UsuarioQuinielaResumen peorUsuario;

    public QuinielaDashboardData(String sourceFile,
                                 LocalDateTime fechaGeneracion,
                                 int registrosCargados,
                                 int usuariosAnalizados,
                                 int puntosTotales,
                                 double promedioPuntos,
                                 double medianaPuntos,
                                 int lineasOmitidas,
                                 List<UsuarioQuinielaResumen> rankingUsuarios,
                                 Map<String, Long> aciertosPorTipo,
                                 UsuarioQuinielaResumen mejorUsuario,
                                 UsuarioQuinielaResumen peorUsuario) {
        this.sourceFile = sourceFile;
        this.fechaGeneracion = fechaGeneracion;
        this.registrosCargados = registrosCargados;
        this.usuariosAnalizados = usuariosAnalizados;
        this.puntosTotales = puntosTotales;
        this.promedioPuntos = promedioPuntos;
        this.medianaPuntos = medianaPuntos;
        this.lineasOmitidas = lineasOmitidas;
        this.rankingUsuarios = List.copyOf(rankingUsuarios);
        this.aciertosPorTipo = Collections.unmodifiableMap(new LinkedHashMap<>(aciertosPorTipo));
        this.mejorUsuario = mejorUsuario;
        this.peorUsuario = peorUsuario;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public int getRegistrosCargados() {
        return registrosCargados;
    }

    public int getUsuariosAnalizados() {
        return usuariosAnalizados;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public double getPromedioPuntos() {
        return promedioPuntos;
    }

    public double getMedianaPuntos() {
        return medianaPuntos;
    }

    public int getLineasOmitidas() {
        return lineasOmitidas;
    }

    public List<UsuarioQuinielaResumen> getRankingUsuarios() {
        return Collections.unmodifiableList(rankingUsuarios);
    }

    public Map<String, Long> getAciertosPorTipo() {
        return Collections.unmodifiableMap(aciertosPorTipo);
    }

    public UsuarioQuinielaResumen getMejorUsuario() {
        return mejorUsuario;
    }

    public UsuarioQuinielaResumen getPeorUsuario() {
        return peorUsuario;
    }
}


