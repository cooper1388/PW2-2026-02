package hn.uth.demojsfprimefaces.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class QuinielaDashboardService implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);

    public QuinielaDashboardData load(Path csvPath) throws IOException {
        Objects.requireNonNull(csvPath, "csvPath");

        if (!Files.exists(csvPath)) {
            throw new NoSuchFileException(csvPath.toAbsolutePath().toString());
        }

        Map<String, UsuarioAccumulator> usuarios = new LinkedHashMap<>();
        Map<String, Long> aciertosPorTipo = new LinkedHashMap<>();
        List<Integer> puntosPorUsuario = new ArrayList<>();
        LocalDateTime fechaGeneracionMaxima = null;
        int registrosCargados = 0;
        int lineasOmitidas = 0;

        try (BufferedReader reader = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8)) {
            String header = reader.readLine();
            if (header == null) {
                throw new IllegalStateException("El archivo CSV está vacío: " + csvPath.toAbsolutePath());
            }

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                registrosCargados++;
                try {
                    QuinielaRegistro registro = parseRegistro(line);
                    fechaGeneracionMaxima = max(fechaGeneracionMaxima, registro.getFechaGeneracion());
                    aciertosPorTipo.merge(registro.getTipoAcierto(), 1L, Long::sum);
                    usuarios.computeIfAbsent(registro.getUsuario(), UsuarioAccumulator::new).accept(registro);
                } catch (RuntimeException ex) {
                    lineasOmitidas++;
                }
            }
        }

        if (usuarios.isEmpty()) {
            throw new IllegalStateException("No se encontraron registros válidos en el archivo CSV: " + csvPath.toAbsolutePath());
        }

        List<UsuarioQuinielaResumen> ranking = new ArrayList<>(usuarios.values().stream()
                .map(UsuarioAccumulator::toResumen)
                .toList());
        ranking.sort(Comparator.comparingInt(UsuarioQuinielaResumen::getPosicion)
                .thenComparing(Comparator.comparingInt(UsuarioQuinielaResumen::getPuntosTotales).reversed())
                .thenComparing(UsuarioQuinielaResumen::getUsuario, String.CASE_INSENSITIVE_ORDER));

        int maxPoints = ranking.stream().mapToInt(UsuarioQuinielaResumen::getPuntosTotales).max().orElse(0);
        ranking = ranking.stream()
                .map(usuario -> new UsuarioQuinielaResumen(
                        usuario.getPosicion(),
                        usuario.getUsuario(),
                        usuario.getPuntosTotales(),
                        usuario.getAciertosExactosTotales(),
                        usuario.getAciertosGanadorTotales(),
                        usuario.getAciertosGolesEquipoTotales(),
                        usuario.getPartidosEvaluadosTotales(),
                        usuario.getPuntosPorPartido(),
                        maxPoints == 0 ? 0.0d : (usuario.getPuntosTotales() * 100.0d) / maxPoints))
                .toList();

        for (UsuarioQuinielaResumen usuario : ranking) {
            puntosPorUsuario.add(usuario.getPuntosTotales());
        }

        int puntosTotales = ranking.stream().mapToInt(UsuarioQuinielaResumen::getPuntosTotales).sum();
        double promedioPuntos = ranking.stream().mapToInt(UsuarioQuinielaResumen::getPuntosTotales).average().orElse(0.0d);
        double medianaPuntos = median(puntosPorUsuario);

        UsuarioQuinielaResumen mejorUsuario = ranking.stream()
                .max(Comparator.comparingInt(UsuarioQuinielaResumen::getPuntosTotales)
                        .thenComparingInt(UsuarioQuinielaResumen::getAciertosExactosTotales))
                .orElse(null);

        UsuarioQuinielaResumen peorUsuario = ranking.stream()
                .min(Comparator.comparingInt(UsuarioQuinielaResumen::getPuntosTotales)
                        .thenComparingInt(UsuarioQuinielaResumen::getAciertosExactosTotales))
                .orElse(null);

        return new QuinielaDashboardData(
                csvPath.toAbsolutePath().toString(),
                fechaGeneracionMaxima,
                registrosCargados,
                usuarios.size(),
                puntosTotales,
                promedioPuntos,
                medianaPuntos,
                lineasOmitidas,
                ranking,
                aciertosPorTipo,
                mejorUsuario,
                peorUsuario
        );
    }

    private QuinielaRegistro parseRegistro(String line) {
        String[] values = line.split(",", -1);
        if (values.length < 18) {
            throw new IllegalArgumentException("La línea no contiene las 18 columnas requeridas");
        }

        return new QuinielaRegistro(
                parseDate(values[0]),
                parseInt(values[1]),
                values[2].trim(),
                parseInt(values[3]),
                parseInt(values[4]),
                parseInt(values[5]),
                parseInt(values[6]),
                parseInt(values[7]),
                values[8].trim(),
                values[9].trim(),
                parseDate(values[10]),
                parseDate(values[11]),
                parseInt(values[12]),
                parseInt(values[13]),
                parseInt(values[14]),
                parseInt(values[15]),
                values[16].trim(),
                parseInt(values[17])
        );
    }

    private static LocalDateTime parseDate(String value) {
        try {
            return LocalDateTime.parse(value.trim(), DATE_TIME_FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Formato de fecha inválido: " + value, ex);
        }
    }

    private static int parseInt(String value) {
        return Integer.parseInt(value.trim());
    }

    private static LocalDateTime max(LocalDateTime left, LocalDateTime right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        return left.isAfter(right) ? left : right;
    }

    private static double median(List<Integer> values) {
        if (values.isEmpty()) {
            return 0.0d;
        }
        List<Integer> sorted = new ArrayList<>(values);
        Collections.sort(sorted);
        int middle = sorted.size() / 2;
        if (sorted.size() % 2 == 0) {
            return (sorted.get(middle - 1) + sorted.get(middle)) / 2.0d;
        }
        return sorted.get(middle);
    }

    private static final class UsuarioAccumulator {
        private final String usuario;
        private int posicion = Integer.MAX_VALUE;
        private int puntosTotales = Integer.MIN_VALUE;
        private int aciertosExactosTotales;
        private int aciertosGanadorTotales;
        private int aciertosGolesEquipoTotales;
        private int partidosEvaluadosTotales;

        private UsuarioAccumulator(String usuario) {
            this.usuario = usuario;
        }

        private void accept(QuinielaRegistro registro) {
            posicion = Math.min(posicion, registro.getPosicion());
            puntosTotales = Math.max(puntosTotales, registro.getPuntosTotales());
            aciertosExactosTotales = Math.max(aciertosExactosTotales, registro.getAciertosExactosTotales());
            aciertosGanadorTotales = Math.max(aciertosGanadorTotales, registro.getAciertosGanadorTotales());
            aciertosGolesEquipoTotales = Math.max(aciertosGolesEquipoTotales, registro.getAciertosGolesEquipoTotales());
            partidosEvaluadosTotales = Math.max(partidosEvaluadosTotales, registro.getPartidosEvaluadosTotales());
        }

        private UsuarioQuinielaResumen toResumen() {
            int posicionFinal = posicion == Integer.MAX_VALUE ? 0 : posicion;
            int puntosFinales = puntosTotales == Integer.MIN_VALUE ? 0 : puntosTotales;
            double puntosPorPartido = partidosEvaluadosTotales == 0 ? 0.0d : puntosFinales / (double) partidosEvaluadosTotales;
            return new UsuarioQuinielaResumen(
                    posicionFinal,
                    usuario,
                    puntosFinales,
                    aciertosExactosTotales,
                    aciertosGanadorTotales,
                    aciertosGolesEquipoTotales,
                    partidosEvaluadosTotales,
                    puntosPorPartido,
                    0.0d
            );
        }
    }
}


