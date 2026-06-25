
# AppQuinielaJSF

Aplicación web académica desarrollada con **Jakarta Faces (JSF)** para gestionar una quiniela del Mundial 2026. Permite a estudiantes practicar conceptos de Programación Web 2 como autenticación en sesión, formularios JSF, convertidores personalizados, CDI y manejo de estado.

## Enfoque académico

Este proyecto está orientado a aprendizaje y evaluación académica.

- Simula un flujo real de pronósticos deportivos con un alcance didáctico.
- Refuerza patrones MVC en aplicaciones Java web.
- Implementa validación de entrada, navegación entre vistas y uso de beans de sesión.
- Prioriza claridad de estructura y comprensión del ciclo de vida JSF sobre complejidad empresarial.

## Que hace la aplicacion

La aplicación cubre tres flujos principales:

1. **Inicio de sesión**
   - El usuario ingresa con correo electrónico válido.
   - Se gestiona autenticación básica en sesión.

2. **Registro de predicciones**
   - Se selecciona un partido no jugado.
   - Se ingresan goles pronosticados para cada equipo.
   - Se almacenan y muestran los pronósticos registrados.

3. **Registro de resultados**
   - Se selecciona un partido ya jugado.
   - Se ingresan goles reales finales.
   - Se almacenan y muestran los resultados registrados.

## Tecnologias utilizadas

- **Java 25**
- **Maven** (empaquetado `war`)
- **Jakarta Faces 4.x (JSF)**
- **CDI / Weld Servlet**
- **Hibernate Validator + Jakarta Validation**
- **Jakarta JSON**
- **JUnit 5** (base para pruebas)
- **XHTML + CSS** para la interfaz

Dependencias y versiones definidas en `pom.xml`.

## Arquitectura y estructura

Estructura principal del codigo fuente en `src/main/java/hn/uth/appquinielajsf/`:

- `beans/`
  - `Login.java`: autenticación y estado de sesión.
  - `Partidos.java`: catálogo y filtrado de partidos por fecha.
  - `Predicciones.java`: gestión de pronósticos de usuario.
  - `Resultados.java`: registro de marcadores finales.
- `data/`
  - `Partido.java`: modelo de partido.
  - `Pronostico.java`: modelo de predicción.
- `converters/`
  - `PartidosConverter.java`: conversión de objetos para componentes JSF.

Vistas principales en `src/main/webapp/`:

- `login.xhtml`
- `index.xhtml`
- `resultados.xhtml`

## Requisitos

- JDK 25 (o la versión configurada en `pom.xml`)
- Maven 3.9+ (o uso de Maven Wrapper incluido)
- Servidor compatible con aplicaciones web Java para desplegar el archivo WAR

## Ejecucion local (compilar y empaquetar)

```powershell
.\mvnw.cmd clean package
```

Esto genera el artefacto WAR en `target/`.

## Objetivos de aprendizaje cubiertos

- Desarrollo web con JSF y ciclo de vida de componentes.
- Gestión de estado de usuario con beans de sesión.
- Validación de datos de entrada.
- Conversión de tipos en formularios JSF.
- Separación de responsabilidades por capas (`beans`, `data`, `converters`).

## Alcance y limitaciones (contexto academico)

- Persistencia en memoria (sin base de datos).
- Seguridad básica orientada a práctica educativa.
- Reglas de negocio simplificadas para facilitar evaluación y mantenimiento.

## Mejoras sugeridas para evolucion del proyecto

- Integración con base de datos relacional.
- Autenticación robusta con roles.
- Cálculo automático de puntajes de quiniela.
- Pruebas unitarias e integración más amplias.
- Internacionalización y mejoras de accesibilidad UI.

## Autoría

Proyecto académico de la asignatura **Programación Web 2**.
