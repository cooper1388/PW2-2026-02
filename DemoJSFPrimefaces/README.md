# DemoJSFPrimefaces - Dashboard de Quiniela

Esta aplicación JSF + PrimeFaces carga un archivo CSV desde:

`C:\app-quiniela\tabla.csv`

## Qué muestra
- Usuarios analizados
- Registros cargados
- Puntos totales, promedio y mediana
- Ranking completo de usuarios
- Top 5 y últimos 5 jugadores
- Gráficos de puntos y distribución de aciertos

## Formato esperado del CSV
El archivo debe conservar el encabezado:

`fecha_generacion,posicion,usuario,puntos_totales,aciertos_exactos_totales,aciertos_ganador_totales,aciertos_goles_equipo_totales,partidos_evaluados_totales,rival1,rival2,fecha_partido,fecha_pronostico,goles_pronosticados_rival1,goles_pronosticados_rival2,goles_reales_rival1,goles_reales_rival2,tipo_acierto,puntos_otorgados`

## Ejecución
```powershell
cd C:\projects\uth\PW2-2026-02\DemoJSFPrimefaces
mvnw.cmd clean test
mvnw.cmd clean package
```

Luego despliega el WAR generado en `target/` en Tomcat 11 o el servidor Jakarta EE que estés usando.

