package hn.uth.monitordesuscripciones.services;

import hn.uth.monitordesuscripciones.data.Suscripcion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class DashboardService {

    public BigDecimal gastoMensual(List<Suscripcion> suscripciones) {
        BigDecimal total = BigDecimal.ZERO;
        for (Suscripcion suscripcion : suscripciones) {
            if ("ANUAL".equalsIgnoreCase(suscripcion.getRecurrencia())) {
                total = total.add(suscripcion.getCosto().divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP));
            } else if ("SEMANAL".equalsIgnoreCase(suscripcion.getRecurrencia())) {
                total = total.add(suscripcion.getCosto().multiply(BigDecimal.valueOf(4.33)));
            } else {
                total = total.add(suscripcion.getCosto());
            }
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal gastoAnual(List<Suscripcion> suscripciones) {
        return gastoMensual(suscripciones).multiply(BigDecimal.valueOf(12)).setScale(2, RoundingMode.HALF_UP);
    }

    public long proximosPagos(List<Suscripcion> suscripciones, int dias) {
        LocalDate limite = LocalDate.now().plusDays(dias);
        return suscripciones.stream().filter(s -> !s.getFechaRenovacion().isAfter(limite)).count();
    }
}

