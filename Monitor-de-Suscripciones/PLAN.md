# PLAN DE IMPLEMENTACION - Monitor de Suscripciones

Este documento sirve como tablero de seguimiento para implementar todo lo solicitado en `README.md`.

## Estado general del proyecto

- Estado: En ejecucion (iteracion 1 y 2 avanzadas)
- Fecha de inicio del plan: 2026-07-15
- Responsable: Equipo PW2

## Resumen de avance

- [x] Estructura base del proyecto Maven WAR creada
- [x] Configuracion base de JSF/PrimeFaces disponible
- [x] Vista inicial `index.xhtml` creada (login basico)
- [x] Documento de plan (`PLAN.md`) creado
- [x] Documento de resumen (`RESUMEN.md`) creado
- [x] Modelo de datos SQLite implementado
- [x] Logica de negocio y autenticacion completa
- [x] 10 pantallas funcionales implementadas
- [x] Reportes PDF/Excel implementados
- [ ] Pruebas funcionales y tecnicas completadas

## Checklist maestro por requerimiento del README

### 1) Arquitectura y base tecnica

- [ ] Ajustar `pom.xml` a version final de Java objetivo del equipo
- [x] Agregar driver JDBC de SQLite
- [x] Crear clase Singleton de conexion en `database/`
- [x] Definir convenciones MVC para `beans/`, `data/`, `services/`
- [x] Configurar navegacion JSF y mensajes globales de UI

### 2) Base de datos SQLite

- [x] Crear script SQL inicial con tablas:
  - [x] `roles`
  - [x] `usuarios`
  - [x] `categorias`
  - [x] `suscripciones`
  - [x] `historial_uso_suscripciones`
- [x] Definir llaves primarias/foraneas e indices
- [x] Incluir datos semilla (roles admin/usuario y categorias base)
- [x] Crear capa CRUD para cada entidad en `database/`
- [x] Manejar creacion automatica de BD al primer arranque

### 3) Seguridad, roles y acceso

- [x] Implementar autenticacion por correo + credenciales
- [x] Gestionar sesion de usuario activo
- [x] Implementar autorizacion por rol (ADMIN/USUARIO)
- [x] Bloquear pantallas administrativas para usuarios no admin
- [x] Flujo de aprobacion de nuevos usuarios por ADMIN
- [x] Flujo de reseteo de contrasena administrado por ADMIN

### 4) Pantallas funcionales (10 requeridas)

- [x] Pantalla 1: Login (publica, inicial)
- [x] Pantalla 2: Registro de usuario (publica, pendiente de aprobacion)
- [x] Pantalla 3: Dashboard (resumen, proximos pagos, graficos)
- [x] Pantalla 4: Agregar suscripcion
- [x] Pantalla 5: Editar suscripcion
- [x] Pantalla 6: Reportes (categoria, mes, anual, exportacion)
- [x] Pantalla 7: Listado de suscripciones (incluye recurrencia, editar/eliminar)
- [x] Pantalla 8: Gastos fantasmas (uso diario y deteccion por periodos)
- [x] Pantalla 9: Gestion de categorias (solo ADMIN)
- [x] Pantalla 10: Reseteo de contrasena y aprobacion de usuarios (solo ADMIN)

### 5) Funcionalidad de suscripciones

- [x] CRUD completo de suscripciones
- [x] Campos obligatorios: servicio, costo, fecha renovacion, categoria, recurrencia
- [x] Validaciones de negocio y de formulario
- [x] Calculo de gasto mensual y anual
- [x] Alertas de proximos pagos

### 6) Reporteria y visualizacion

- [ ] Grafico pastel de costos por categoria
- [ ] Grafico barras de costos por mes
- [x] Resumen anual consolidado
- [x] Exportar reportes a PDF
- [x] Exportar reportes a Excel

### 7) Gastos fantasmas (uso/no uso)

- [x] Registro rapido de uso diario por suscripcion
- [x] Consulta de suscripciones no usadas en ultimos 7 dias
- [x] Consulta de suscripciones no usadas en ultimos 14 dias
- [x] Consulta de suscripciones no usadas en ultimos 30 dias
- [x] Consulta de suscripciones no usadas en ultimos 90 dias

### 8) Pruebas y calidad

- [ ] Pruebas unitarias para servicios clave
- [ ] Pruebas de integracion para capa de datos SQLite
- [ ] Pruebas de flujo UI (login, registro, CRUD, reportes)
- [ ] Validacion de permisos por rol
- [ ] Verificacion de exportaciones PDF/Excel

### 9) Documentacion final

- [ ] Actualizar `RESUMEN.md` por cada iteracion
- [ ] Registrar decisiones tecnicas importantes
- [ ] Documentar despliegue y ejecucion local
- [ ] Checklist de entrega final del proyecto

## Plan por iteraciones sugerido

### Iteracion 1 - Fundaciones

- Base de datos SQLite operativa
- Singleton de conexion JDBC
- Entidades base (`Rol`, `Usuario`, `Categoria`, `Suscripcion`, `HistorialUso`)
- Login y registro iniciales

### Iteracion 2 - Core funcional

- CRUD de suscripciones completo
- Dashboard con metricas base
- Listado de suscripciones con filtros
- Gestion de categorias (admin)

### Iteracion 3 - Analitica y admin

- Reportes con graficos (categoria/mes/anual)
- Exportacion PDF/Excel
- Gastos fantasmas con periodos 7/14/30/90
- Aprobacion de usuarios y reseteo de contrasena

### Iteracion 4 - Cierre

- Endurecimiento de validaciones y seguridad
- Pruebas finales
- Documentacion y ajustes de entrega

## Tablero de seguimiento detallado

| ID | Tarea | Estado | Prioridad | Dependencias | Evidencia |
|---|---|---|---|---|---|
| T-01 | Crear `PLAN.md` y `RESUMEN.md` | Completado | Alta | Ninguna | Documentos creados |
| T-02 | Definir esquema SQLite e inicializacion | Completado | Alta | T-01 | `DatabaseInitializer` + tablas creadas |
| T-03 | Implementar conexion Singleton JDBC | Completado | Alta | T-02 | `SQLiteConnectionManager` |
| T-04 | Implementar autenticacion y sesion | Completado | Alta | T-03 | `LoginBean` + `SessionBean` |
| T-05 | Implementar registro con aprobacion admin | Completado | Alta | T-04 | `RegistroBean` + `AdminBean` |
| T-06 | Implementar CRUD de categorias (admin) | Completado | Media | T-03 | `CategoriaBean/Service/DAO` |
| T-07 | Implementar CRUD de suscripciones | Completado | Alta | T-03, T-06 | `SuscripcionBean/Service/DAO` |
| T-08 | Implementar dashboard con KPIs y graficos | En progreso | Alta | T-07 | KPIs listos, graficos pendientes |
| T-09 | Implementar reportes y exportacion PDF/Excel | Completado | Media | T-08 | `ReportesBean` + `ReporteService` |
| T-10 | Implementar gastos fantasmas | Completado | Media | T-07 | `GastosFantasmaBean/Service/DAO` |
| T-11 | Implementar modulo admin (pantalla 10) | Completado | Alta | T-05 | Aprobacion + reseteo operativos |
| T-12 | Pruebas finales y cierre documental | En progreso | Alta | T-02..T-11 | Build `mvn test` ejecutado |

## Criterio para actualizar estados

- `Pendiente`: no iniciado.
- `En progreso`: iniciado pero sin criterios de aceptacion completos.
- `Bloqueado`: depende de otra tarea o decision.
- `Completado`: implementado, probado y documentado en `RESUMEN.md`.

