package hn.uth.appquinielajsf.beans;

import hn.uth.appquinielajsf.data.Partido;
import hn.uth.appquinielajsf.data.PosicionUsuario;
import hn.uth.appquinielajsf.data.Pronostico;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

@Named("TablaPosicionesBean")
@RequestScoped
public class TablaPosiciones implements Serializable {
    private static final ZoneId ZONA_HONDURAS = ZoneId.of("America/Tegucigalpa");
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Inject
    private QuinielaStore quinielaStoreBean;

    public List<PosicionUsuario> getPosiciones() {
        return calcularTabla().posiciones();
    }

    public void exportarTablaCsv() {
        TablaCalculada tablaCalculada = calcularTabla();
        List<String> lineas = new ArrayList<>();
        lineas.add("fecha_generacion,posicion,usuario,puntos_totales,aciertos_exactos_totales,aciertos_ganador_totales,aciertos_goles_equipo_totales,partidos_evaluados_totales,rival1,rival2,fecha_partido,fecha_pronostico,goles_pronosticados_rival1,goles_pronosticados_rival2,goles_reales_rival1,goles_reales_rival2,tipo_acierto,puntos_otorgados");

        for (DetalleEvaluacion detalle : tablaCalculada.detalles()) {
            PosicionUsuario posicionUsuario = tablaCalculada.posicionPorUsuario().get(detalle.usuario());
            lineas.add(String.join(",",
                    escapeCsv(LocalDateTime.now(ZONA_HONDURAS).format(FORMATO_FECHA)),
                    escapeCsv(String.valueOf(posicionUsuario.getPosicion())),
                    escapeCsv(posicionUsuario.getUsuario()),
                    escapeCsv(String.valueOf(posicionUsuario.getPuntos())),
                    escapeCsv(String.valueOf(posicionUsuario.getAciertosExactos())),
                    escapeCsv(String.valueOf(posicionUsuario.getAciertosGanador())),
                    escapeCsv(String.valueOf(posicionUsuario.getAciertosGolesEquipo())),
                    escapeCsv(String.valueOf(posicionUsuario.getPartidosEvaluados())),
                    escapeCsv(detalle.rival1()),
                    escapeCsv(detalle.rival2()),
                    escapeCsv(detalle.fechaPartido()),
                    escapeCsv(detalle.fechaPronostico()),
                    escapeCsv(String.valueOf(detalle.golesPronosticadosRival1())),
                    escapeCsv(String.valueOf(detalle.golesPronosticadosRival2())),
                    escapeCsv(String.valueOf(detalle.golesRealesRival1())),
                    escapeCsv(String.valueOf(detalle.golesRealesRival2())),
                    escapeCsv(detalle.tipoAcierto()),
                    escapeCsv(String.valueOf(detalle.puntosOtorgados()))
            ));
        }

        try {
            Files.createDirectories(CsvDataLoader.DIRECTORIO_DATOS);
            Files.write(CsvDataLoader.ARCHIVO_TABLA, lineas, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("No fue posible escribir tabla.csv", e);
        }
    }

    private TablaCalculada calcularTabla() {
        List<Pronostico> pronosticos = quinielaStoreBean.getPronosticos();
        List<Partido> resultados = quinielaStoreBean.getResultados();

        Map<Partido, Partido> resultadosPorPartido = new HashMap<>();
        for (Partido resultado : resultados) {
            resultadosPorPartido.put(resultado, resultado);
        }

        Map<String, AcumuladorPuntos> acumuladoPorUsuario = new HashMap<>();
        Map<String, List<DetalleEvaluacion>> detallesPorUsuario = new LinkedHashMap<>();

        for (Pronostico pronostico : pronosticos) {
            if (pronostico.getPartido() == null || pronostico.getUsuario() == null || pronostico.getUsuario().isBlank()) {
                continue;
            }

            Partido resultadoReal = resultadosPorPartido.get(pronostico.getPartido());
            if (resultadoReal == null) {
                continue;
            }

            AcumuladorPuntos acumulador = acumuladoPorUsuario.get(pronostico.getUsuario());
            if (acumulador == null) {
                acumulador = new AcumuladorPuntos();
                acumuladoPorUsuario.put(pronostico.getUsuario(), acumulador);
            }

            int predR1 = pronostico.getGolesRival1();
            int predR2 = pronostico.getGolesRival2();
            int realR1 = resultadoReal.getGolesRival1();
            int realR2 = resultadoReal.getGolesRival2();

            boolean exacto = predR1 == realR1 && predR2 == realR2;
            boolean aciertaResultado = mismoResultadoFinal(predR1, predR2, realR1, realR2);
            boolean aciertaGolesEquipo = predR1 == realR1 || predR2 == realR2;
            String tipoAcierto = exacto ? "EXACTO" : aciertaResultado ? "GANADOR" : aciertaGolesEquipo ? "GOL_EQUIPO" : "SIN_ACIERTO";
            int puntosOtorgados = exacto ? 6 : aciertaResultado ? 3 : aciertaGolesEquipo ? 1 : 0;

            if (exacto) {
                acumulador.puntos += 6;
                acumulador.aciertosExactos++;
            } else {
                if (aciertaResultado) {
                    acumulador.puntos += 3;
                    acumulador.aciertosGanador++;
                }
                if (aciertaGolesEquipo) {
                    acumulador.puntos += 1;
                    acumulador.aciertosGolesEquipo++;
                }
            }

            acumulador.partidosEvaluados++;

            detallesPorUsuario.computeIfAbsent(pronostico.getUsuario(), k -> new ArrayList<>())
                    .add(new DetalleEvaluacion(
                            pronostico.getUsuario(),
                            pronostico.getPartido().getRival1(),
                            pronostico.getPartido().getRival2(),
                            formatearFecha(pronostico.getPartido().getFechaHora()),
                            formatearFecha(pronostico.getFechaHoraPronostico()),
                            predR1,
                            predR2,
                            realR1,
                            realR2,
                            tipoAcierto,
                            puntosOtorgados
                    ));
        }

        List<PosicionUsuario> posiciones = new ArrayList<>();
        Map<String, PosicionUsuario> posicionPorUsuario = new HashMap<>();
        for (Map.Entry<String, AcumuladorPuntos> entry : acumuladoPorUsuario.entrySet()) {
            AcumuladorPuntos a = entry.getValue();
            PosicionUsuario posicionUsuario = new PosicionUsuario(
                    entry.getKey(),
                    a.puntos,
                    a.aciertosExactos,
                    a.aciertosGanador,
                    a.aciertosGolesEquipo,
                    a.partidosEvaluados
            );
            posiciones.add(posicionUsuario);
            posicionPorUsuario.put(entry.getKey(), posicionUsuario);
        }

        posiciones.sort((a, b) -> {
            if (b.getPuntos() != a.getPuntos()) return b.getPuntos() - a.getPuntos();
            if (b.getAciertosExactos() != a.getAciertosExactos()) return b.getAciertosExactos() - a.getAciertosExactos();
            if (b.getAciertosGanador() != a.getAciertosGanador()) return b.getAciertosGanador() - a.getAciertosGanador();
            if (b.getAciertosGolesEquipo() != a.getAciertosGolesEquipo()) return b.getAciertosGolesEquipo() - a.getAciertosGolesEquipo();
            return a.getUsuario().compareTo(b.getUsuario());
        });

        int posicionActual = 0;
        PosicionUsuario anterior = null;
        for (int i = 0; i < posiciones.size(); i++) {
            PosicionUsuario actual = posiciones.get(i);
            if (anterior == null ||
                    actual.getPuntos() != anterior.getPuntos() ||
                    actual.getAciertosExactos() != anterior.getAciertosExactos() ||
                    actual.getAciertosGanador() != anterior.getAciertosGanador() ||
                    actual.getAciertosGolesEquipo() != anterior.getAciertosGolesEquipo()) {
                posicionActual = i + 1;
            }
            actual.setPosicion(posicionActual);
            anterior = actual;
        }

        return new TablaCalculada(posiciones, posicionPorUsuario, detallesPlanos(detallesPorUsuario));
    }

    private List<DetalleEvaluacion> detallesPlanos(Map<String, List<DetalleEvaluacion>> detallesPorUsuario) {
        List<DetalleEvaluacion> detalles = new ArrayList<>();
        for (List<DetalleEvaluacion> detalleEvaluacions : detallesPorUsuario.values()) {
            detalles.addAll(detalleEvaluacions);
        }
        return detalles;
    }

    private String formatearFecha(java.util.Date fecha) {
        if (fecha == null) {
            return "";
        }
        return LocalDateTime.ofInstant(fecha.toInstant(), ZONA_HONDURAS).format(FORMATO_FECHA);
    }

    private String escapeCsv(String valor) {
        if (valor == null) {
            return "";
        }
        String limpio = valor.replace("\"", "\"\"");
        if (limpio.contains(",") || limpio.contains("\n") || limpio.contains("\r") || limpio.contains("\"")) {
            return '"' + limpio + '"';
        }
        return limpio;
    }

    private boolean mismoResultadoFinal(int predR1, int predR2, int realR1, int realR2) {
        return Integer.compare(predR1, predR2) == Integer.compare(realR1, realR2);
    }

    private record TablaCalculada(List<PosicionUsuario> posiciones,
                                  Map<String, PosicionUsuario> posicionPorUsuario,
                                  List<DetalleEvaluacion> detalles) {
    }

    private record DetalleEvaluacion(String usuario,
                                     String rival1,
                                     String rival2,
                                     String fechaPartido,
                                     String fechaPronostico,
                                     int golesPronosticadosRival1,
                                     int golesPronosticadosRival2,
                                     int golesRealesRival1,
                                     int golesRealesRival2,
                                     String tipoAcierto,
                                     int puntosOtorgados) {
    }

    private static class AcumuladorPuntos {
        private int puntos;
        private int aciertosExactos;
        private int aciertosGanador;
        private int aciertosGolesEquipo;
        private int partidosEvaluados;
    }
}

