# RESUMEN DE AVANCES Y DOCUMENTACION

## Estado del proyecto (corte: 2026-07-15)

Se ejecuto una implementacion funcional end-to-end del sistema base solicitado en `README.md`:

- Persistencia SQLite operativa con inicializacion automatica.
- Capa MVC completa (`data`, `database`, `services`, `beans`).
- Flujo de autenticacion, sesion, registro pendiente de aprobacion y modulo admin.
- Pantallas funcionales para login, registro, dashboard, CRUD de suscripciones, reportes, gastos fantasmas y administracion.
- Exportacion de reportes a PDF y Excel.
- Build validado con `mvn test`.

## Cambios implementados

### Dependencias y build

- `pom.xml`
  - Se agrego `sqlite-jdbc` para persistencia en SQLite.
  - Se agrego `poi-ooxml` para exportacion Excel.
  - Se agrego `openpdf` para exportacion PDF.

### Modelo de datos (`data/`)

- `src/main/java/hn/uth/monitordesuscripciones/data/Rol.java`
  - Modelo de rol del sistema (`ADMIN`, `USUARIO`).
- `src/main/java/hn/uth/monitordesuscripciones/data/Usuario.java`
  - Modelo de usuario con aprobacion y rol.
- `src/main/java/hn/uth/monitordesuscripciones/data/Categoria.java`
  - Modelo de categoria de suscripciones.
- `src/main/java/hn/uth/monitordesuscripciones/data/Suscripcion.java`
  - Modelo principal de suscripcion (servicio, costo, renovacion, recurrencia).
- `src/main/java/hn/uth/monitordesuscripciones/data/HistorialUso.java`
  - Modelo de uso diario para detectar gastos fantasmas.

### Capa de base de datos (`database/`)

- `src/main/java/hn/uth/monitordesuscripciones/database/SQLiteConnectionManager.java`
  - Singleton de conexion JDBC a SQLite (`monitor_suscripciones.db`).
- `src/main/java/hn/uth/monitordesuscripciones/database/DatabaseInitializer.java`
  - Crea tablas, indices y datos semilla al primer uso.
- `src/main/java/hn/uth/monitordesuscripciones/database/UsuarioDAO.java`
  - CRUD parcial para autenticacion, aprobacion y reseteo de password.
- `src/main/java/hn/uth/monitordesuscripciones/database/CategoriaDAO.java`
  - CRUD completo de categorias.
- `src/main/java/hn/uth/monitordesuscripciones/database/SuscripcionDAO.java`
  - CRUD completo de suscripciones.
- `src/main/java/hn/uth/monitordesuscripciones/database/HistorialUsoDAO.java`
  - Marcado de uso diario y consultas de no uso por periodo.

### Logica de negocio (`services/`)

- `src/main/java/hn/uth/monitordesuscripciones/services/AuthService.java`
  - Login con validacion de aprobacion y registro de usuario pendiente.
- `src/main/java/hn/uth/monitordesuscripciones/services/CategoriaService.java`
  - Reglas de categorias para el modulo admin.
- `src/main/java/hn/uth/monitordesuscripciones/services/SuscripcionService.java`
  - Validaciones de negocio y guardado de suscripciones.
- `src/main/java/hn/uth/monitordesuscripciones/services/DashboardService.java`
  - Calculo de gasto mensual/anual y proximos pagos.
- `src/main/java/hn/uth/monitordesuscripciones/services/GastosFantasmaService.java`
  - Marcado de uso y deteccion de suscripciones no usadas.
- `src/main/java/hn/uth/monitordesuscripciones/services/AdminService.java`
  - Aprobacion de usuarios y reseteo de contrasenas.
- `src/main/java/hn/uth/monitordesuscripciones/services/ReporteService.java`
  - Exportacion de suscripciones en PDF/Excel.

### Managed beans (`beans/`)

- `src/main/java/hn/uth/monitordesuscripciones/beans/SessionBean.java`
  - Mantiene sesion, rol y logout.
- `src/main/java/hn/uth/monitordesuscripciones/beans/LoginBean.java`
  - Controlador de inicio de sesion.
- `src/main/java/hn/uth/monitordesuscripciones/beans/RegistroBean.java`
  - Controlador de registro de nuevos usuarios.
- `src/main/java/hn/uth/monitordesuscripciones/beans/DashboardBean.java`
  - KPIs y listado de suscripciones en dashboard.
- `src/main/java/hn/uth/monitordesuscripciones/beans/SuscripcionBean.java`
  - CRUD de suscripciones.
- `src/main/java/hn/uth/monitordesuscripciones/beans/CategoriaBean.java`
  - CRUD de categorias (admin).
- `src/main/java/hn/uth/monitordesuscripciones/beans/GastosFantasmaBean.java`
  - Pantalla de uso diario y no uso por periodos.
- `src/main/java/hn/uth/monitordesuscripciones/beans/ReportesBean.java`
  - Exportacion de reportes.
- `src/main/java/hn/uth/monitordesuscripciones/beans/AdminBean.java`
  - Aprobacion de usuarios y reseteo de password.

### Vistas JSF (`webapp/`)

- `src/main/webapp/index.xhtml`: Pantalla 1 (login).
- `src/main/webapp/registro.xhtml`: Pantalla 2 (registro).
- `src/main/webapp/dashboard.xhtml`: Pantalla 3 (dashboard).
- `src/main/webapp/suscripcion-agregar.xhtml`: Pantalla 4 (agregar suscripcion).
- `src/main/webapp/suscripcion-editar.xhtml`: Pantalla 5 (editar suscripcion).
- `src/main/webapp/reportes.xhtml`: Pantalla 6 (reportes + exportacion).
- `src/main/webapp/suscripciones.xhtml`: Pantalla 7 (listado + eliminar/editar).
- `src/main/webapp/gastos-fantasmas.xhtml`: Pantalla 8 (uso/no uso 7/14/30/90).
- `src/main/webapp/categorias.xhtml`: Pantalla 9 (admin).
- `src/main/webapp/admin-usuarios.xhtml`: Pantalla 10 (admin).

### Recursos

- `src/main/resources/schema.sql`
  - Script SQL versionado con el esquema base e inserts semilla.

## Validacion ejecutada

- Comando ejecutado:
  - `./mvnw.cmd -q test`
- Resultado:
  - Compilacion y pruebas pasaron sin errores.

## Pendientes reales despues de esta iteracion

- Implementar graficos de pastel y barras en `dashboard.xhtml`/`reportes.xhtml`.
- Endurecer seguridad (hash de password, filtros de autorizacion por URL).
- Agregar pruebas unitarias e integracion formales para servicios y DAOs.

