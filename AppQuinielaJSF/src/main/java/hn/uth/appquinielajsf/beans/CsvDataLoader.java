package hn.uth.appquinielajsf.beans;

import hn.uth.appquinielajsf.data.Partido;
import hn.uth.appquinielajsf.data.Pronostico;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class CsvDataLoader implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(CsvDataLoader.class.getName());
    public static final Path DIRECTORIO_DATOS = Path.of("C:\\app-quiniela");
    public static final Path ARCHIVO_RESULTADOS = DIRECTORIO_DATOS.resolve("resultados.csv");
    public static final Path ARCHIVO_PREDICCIONES = DIRECTORIO_DATOS.resolve("predicciones.csv");
    public static final Path ARCHIVO_TABLA = DIRECTORIO_DATOS.resolve("tabla.csv");
    private static final ZoneId ZONA_HONDURAS = ZoneId.of("America/Tegucigalpa");
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String RECURSO_RESULTADOS = "resultados.csv";
    private static final String RECURSO_PREDICCIONES = "predicciones.csv";

    @Inject
    private QuinielaStore quinielaStoreBean;

    private boolean inicializado;

    public synchronized void inicializarSiNecesario() {
        if (inicializado) {
            return;
        }

        try {
            cargarDatosIniciales();
            inicializado = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "No fue posible inicializar datos CSV. La aplicacion continuara sin precarga.", e);
        }
    }

    private void cargarDatosIniciales() {
        asegurarDirectorioYBaseCsv();

        List<Partido> resultados = cargarResultadosDesdeCsv();
        quinielaStoreBean.reemplazarResultados(resultados);

        List<Pronostico> pronosticos = cargarPronosticosDesdeCsv();
        quinielaStoreBean.reemplazarPronosticos(pronosticos);

        LOGGER.log(Level.INFO, "Datos iniciales cargados desde CSV: {0} resultados y {1} pronosticos.",
                new Object[]{resultados.size(), pronosticos.size()});
    }

    private List<Partido> cargarResultadosDesdeCsv() {
        List<Partido> resultados = new ArrayList<>();
        for (String[] columnas : leerArchivoCsv(ARCHIVO_RESULTADOS, 5)) {
            resultados.add(crearPartido(columnas[0], columnas[1], columnas[2], columnas[3], columnas[4]));
        }
        return resultados;
    }

    private List<Pronostico> cargarPronosticosDesdeCsv() {
        List<Pronostico> pronosticos = new ArrayList<>();
        for (String[] columnas : leerArchivoCsv(ARCHIVO_PREDICCIONES, 6)) {
            pronosticos.add(crearPronostico(columnas[0], columnas[1], columnas[2], columnas[3], columnas[4], columnas[5]));
        }
        return pronosticos;
    }

    private void asegurarDirectorioYBaseCsv() {
        try {
            Files.createDirectories(DIRECTORIO_DATOS);
            copiarSiNoExiste(RECURSO_RESULTADOS, ARCHIVO_RESULTADOS);
            copiarSiNoExiste(RECURSO_PREDICCIONES, ARCHIVO_PREDICCIONES);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "No fue posible preparar el directorio {0}. Se intentara cargar desde classpath.", DIRECTORIO_DATOS);
        }
    }

    private void copiarSiNoExiste(String recursoClasspath, Path destino) throws IOException {
        if (Files.exists(destino)) {
            return;
        }

        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(recursoClasspath)) {
            if (inputStream == null) {
                Files.createFile(destino);
                return;
            }
            Files.copy(inputStream, destino, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private List<String[]> leerArchivoCsv(Path archivo, int columnasEsperadas) {
        if (Files.exists(archivo)) {
            return leerDesdeReader(obtenerReaderArchivo(archivo), columnasEsperadas, archivo.toString());
        }

        String recursoClasspath = archivo.getFileName().toString();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(recursoClasspath)) {
            if (inputStream == null) {
                LOGGER.log(Level.WARNING, "No se encontro el archivo CSV {0} ni en disco ni en classpath.", archivo);
                return new ArrayList<>();
            }
            return leerDesdeReader(new BufferedReader(new java.io.InputStreamReader(inputStream, StandardCharsets.UTF_8)), columnasEsperadas, recursoClasspath);
        } catch (IOException e) {
            throw new IllegalStateException("No fue posible leer el archivo CSV de respaldo " + recursoClasspath, e);
        }
    }

    private BufferedReader obtenerReaderArchivo(Path archivo) {
        try {
            return Files.newBufferedReader(archivo, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("No fue posible abrir el archivo CSV " + archivo, e);
        }
    }

    private List<String[]> leerDesdeReader(BufferedReader reader, int columnasEsperadas, String nombreFuente) {
        List<String[]> filas = new ArrayList<>();
        try (BufferedReader bufferedReader = reader) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = bufferedReader.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }

                if (primeraLinea && esCabecera(linea)) {
                    primeraLinea = false;
                    continue;
                }

                primeraLinea = false;
                String[] columnas = linea.split(",", -1);
                if (columnas.length != columnasEsperadas) {
                    throw new IllegalStateException("La fila CSV '" + linea + "' no tiene " + columnasEsperadas + " columnas.");
                }
                for (int i = 0; i < columnas.length; i++) {
                    columnas[i] = columnas[i].trim();
                }
                filas.add(columnas);
            }
        } catch (IOException e) {
            throw new IllegalStateException("No fue posible leer el archivo CSV " + nombreFuente, e);
        }
        return filas;
    }

    public synchronized void escribirArchivo(String nombreArchivo, List<String> lineas) {
        try {
            Files.createDirectories(DIRECTORIO_DATOS);
            Path destino = DIRECTORIO_DATOS.resolve(nombreArchivo);
            Files.write(destino, lineas, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new IllegalStateException("No fue posible escribir el archivo CSV " + nombreArchivo, e);
        }
    }

    private boolean esCabecera(String linea) {
        String normalizada = linea.toLowerCase(Locale.ROOT);
        return normalizada.startsWith("rival1,")
                || normalizada.startsWith("usuario,")
                || normalizada.startsWith("equipo local,");
    }

    private Partido crearPartido(String rival1, String rival2, String fecha, String golesRival1, String golesRival2) {
        return new Partido(rival1, rival2, parseFecha(fecha), parseEntero(golesRival1), parseEntero(golesRival2));
    }

    private Pronostico crearPronostico(String usuario, String rival1, String rival2, String fecha,
                                       String golesRival1, String golesRival2) {
        Pronostico pronostico = new Pronostico();
        pronostico.setUsuario(usuario);
        pronostico.setFechaHoraPronostico(parseFecha(fecha));
        pronostico.setGolesRival1(parseEntero(golesRival1));
        pronostico.setGolesRival2(parseEntero(golesRival2));
        pronostico.setPartido(new Partido(rival1, rival2, parseFecha(fecha), 0, 0));
        return pronostico;
    }

    private java.util.Date parseFecha(String valor) {
        LocalDateTime localDateTime = LocalDateTime.parse(valor, FORMATO_FECHA);
        return java.util.Date.from(localDateTime.atZone(ZONA_HONDURAS).toInstant());
    }

    private int parseEntero(String valor) {
        return Integer.parseInt(valor);
    }
}
