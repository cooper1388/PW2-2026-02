package hn.uth.appquinielajsf.beans;

import hn.uth.appquinielajsf.data.Partido;
import hn.uth.appquinielajsf.data.PosicionUsuario;
import hn.uth.appquinielajsf.data.Pronostico;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named("TablaPosicionesBean")
@RequestScoped
public class TablaPosiciones implements Serializable {
    @Inject
    private QuinielaStore quinielaStoreBean;

    public List<PosicionUsuario> getPosiciones() {
        List<Pronostico> pronosticos = quinielaStoreBean.getPronosticos();
        List<Partido> resultados = quinielaStoreBean.getResultados();

        Map<Partido, Partido> resultadosPorPartido = new HashMap<>();
        for (Partido resultado : resultados) {
            resultadosPorPartido.put(resultado, resultado);
        }

        Map<String, AcumuladorPuntos> acumuladoPorUsuario = new HashMap<>();

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
        }

        List<PosicionUsuario> posiciones = new ArrayList<>();
        for (Map.Entry<String, AcumuladorPuntos> entry : acumuladoPorUsuario.entrySet()) {
            AcumuladorPuntos a = entry.getValue();
            posiciones.add(new PosicionUsuario(
                    entry.getKey(),
                    a.puntos,
                    a.aciertosExactos,
                    a.aciertosGanador,
                    a.aciertosGolesEquipo,
                    a.partidosEvaluados
            ));
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

        return posiciones;
    }

    private boolean mismoResultadoFinal(int predR1, int predR2, int realR1, int realR2) {
        return Integer.compare(predR1, predR2) == Integer.compare(realR1, realR2);
    }

    private static class AcumuladorPuntos {
        private int puntos;
        private int aciertosExactos;
        private int aciertosGanador;
        private int aciertosGolesEquipo;
        private int partidosEvaluados;
    }
}

