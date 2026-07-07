package hn.uth.appquinielajsf.beans;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvImportExportTest {

    @AfterEach
    void limpiarTablaGenerada() throws Exception {
        Files.deleteIfExists(CsvDataLoader.ARCHIVO_TABLA);
    }

    @Test
    void cargaDatosExternosYEexpotaTablaCsv() throws Exception {
        QuinielaStore store = new QuinielaStore();

        CsvDataLoader loader = new CsvDataLoader();
        setField(loader, "quinielaStoreBean", store);
        loader.inicializarSiNecesario();

        TablaPosiciones tablaPosiciones = new TablaPosiciones();
        setField(tablaPosiciones, "quinielaStoreBean", store);

        tablaPosiciones.exportarTablaCsv();

        Path archivoTabla = CsvDataLoader.ARCHIVO_TABLA;
        assertTrue(Files.exists(archivoTabla), "Debe generarse tabla.csv en C:\\app-quiniela");

        List<String> lineas = Files.readAllLines(archivoTabla, StandardCharsets.UTF_8);
        assertFalse(lineas.isEmpty(), "tabla.csv debe incluir al menos la cabecera");
        assertTrue(lineas.get(0).startsWith("fecha_generacion,posicion,usuario,"), "La cabecera debe incluir metadatos y resumen");
        assertTrue(lineas.size() > 1, "tabla.csv debe tener filas de detalle");

        String[] primeraFila = lineas.get(1).split(",", -1);
        assertTrue(primeraFila.length >= 18, "Cada fila debe exponer el detalle completo para dashboard");
        assertNotNull(primeraFila[2], "El usuario no debe ser nulo");
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}

