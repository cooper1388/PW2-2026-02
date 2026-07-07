package hn.uth.demojsfprimefaces.data;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class QuinielaDashboardServiceTest {

    @Test
    void debeLeerAgruparYResumirElCsv() throws Exception {
        Path tempFile = Files.createTempFile("tabla-quiniela", ".csv");
        Files.writeString(tempFile, String.join(System.lineSeparator(),
                "fecha_generacion,posicion,usuario,puntos_totales,aciertos_exactos_totales,aciertos_ganador_totales,aciertos_goles_equipo_totales,partidos_evaluados_totales,rival1,rival2,fecha_partido,fecha_pronostico,goles_pronosticados_rival1,goles_pronosticados_rival2,goles_reales_rival1,goles_reales_rival2,tipo_acierto,puntos_otorgados",
                "2026-07-06 18:53:34,1,ana@demo.com,241,21,25,40,92,Mexico,South Africa,2026-06-11 13:00:00,2026-06-11 13:00:00,0,4,4,2,SIN_ACIERTO,0",
                "2026-07-06 18:53:34,1,ana@demo.com,241,21,25,40,92,South Korea,Czechia,2026-06-11 20:00:00,2026-06-11 20:00:00,2,1,2,3,GOL_EQUIPO,1",
                "2026-07-06 18:53:34,1,ana@demo.com,241,21,25,40,92,Canada,Bosnia-Herzegovina,2026-06-12 13:00:00,2026-06-12 13:00:00,1,2,0,1,GANADOR,3",
                "2026-07-06 18:53:34,2,luis@demo.com,120,10,12,18,92,Brazil,Serbia,2026-06-11 16:00:00,2026-06-11 16:00:00,2,0,2,0,EXACTO,6",
                "2026-07-06 18:53:34,2,luis@demo.com,120,10,12,18,92,France,Denmark,2026-06-11 19:00:00,2026-06-11 19:00:00,1,1,2,1,GANADOR,3",
                "2026-07-06 18:53:34,2,luis@demo.com,120,10,12,18,92,Japan,Costa Rica,2026-06-12 16:00:00,2026-06-12 16:00:00,0,1,0,1,SIN_ACIERTO,0"
        ), StandardCharsets.UTF_8);

        QuinielaDashboardService service = new QuinielaDashboardService();
        QuinielaDashboardData data = service.load(tempFile);

        assertNotNull(data);
        assertEquals(6, data.getRegistrosCargados());
        assertEquals(2, data.getUsuariosAnalizados());
        assertEquals(361, data.getPuntosTotales());
        assertEquals(180.5d, data.getMedianaPuntos(), 0.0001d);
        assertEquals("ana@demo.com", data.getMejorUsuario().getUsuario());
        assertEquals("luis@demo.com", data.getPeorUsuario().getUsuario());
        assertEquals(1L, data.getAciertosPorTipo().get("EXACTO"));
        assertEquals(2L, data.getAciertosPorTipo().get("GANADOR"));
        assertEquals(1L, data.getAciertosPorTipo().get("GOL_EQUIPO"));
        assertEquals(2L, data.getAciertosPorTipo().get("SIN_ACIERTO"));

        Files.deleteIfExists(tempFile);
    }
}

