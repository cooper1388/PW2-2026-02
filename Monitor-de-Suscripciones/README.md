# Objetivo

Sistema de monitoreo de Suscripciones Digitales (Netflix, Spotify, Disney+, etc.)
que permite a los usuarios llevar un control de sus gastos mensuales y anuales en servicios de streaming.
La aplicación proporcionará una interfaz intuitiva para agregar, eliminar y actualizar suscripciones,
así como generar reportes visuales sobre el gasto total y por categoría, fechas de renovación, costos por categoría, entre otros.

# Pantallas

1. Pantalla de Login (inicio de sesión) que pide usuario y correo electrónico (pública y pantalla inicial del sistema).
2. Pantalla de Registro de Usuario (para nuevos usuarios) que solicita nombre, correo electrónico y contraseña, esta puede ser publica pero el usuario solo se crea al ser aprobado por un administrador (deben de exisir roles de administrador y usuario).
3. Pantalla Principal (Dashboard) que muestra un resumen de las suscripciones activas, gastos totales y próximos pagos, gráfico de pastel de costos por categoría, gráfico de barras con costos por mes y un botón de acceso rápido para agregar una suscripción más.
4. Pantalla de Agregar Suscripción que permite al usuario ingresar el nombre del servicio, costo mensual, fecha de renovación y categoría (entretenimiento, música, educación, etc.).
5. Pantalla de Editar Suscripción que permite al usuario modificar los detalles de una suscripción existente, incluyendo nombre del servicio, costo mensual, fecha de renovación y categoría.
6. Pantalla de Reportes que muestra gráficos y estadísticas sobre los gastos en suscripciones, incluyendo costos por categoría, costos por mes, y un resumen anual de gastos. También permitirá exportar los reportes en formatos PDF o Excel.
7. Pantalla de listado de suscripciones para ver un listado de todas las suscripciones, su recurrencia (mensual, semanal, anual) y la opción de eliminar o editar cada suscripción.
8. Pantalla de gastos fantasmas, donde se puntee cada suscripción de forma rápida para decir cual use cada día, esta misma pantalla mostrará todas las suscripciones fantasma (no usadas en períodos de los últimos 7 días, 14 días, 30 días, 90 días, etc.) Debe de contar con una funcionalidad rápida para indicar si he usado hoy esa suscripción o no.
9. Pantalla de gestión de categorías de suscripción (solo accesible por el administrador).
10. Pantalla de reseteo de contraseña y aprobación de usuarios (Solo accesible por el administrador).

# Base de datos

La base de datos será en la tecnología SQLite y estará estructurada para almacenar información sobre los usuarios, suscripciones y categorías. A continuación se presenta un esquema básico de la base de datos:

## Tablas

1. Categorías:
2. Suscripciones
3. Usuarios
4. Historial de uso de suscripciones (para la funcionalidad de gastos fantasmas)
5. Roles de usuario (para diferenciar entre administradores y usuarios regulares)

# Librerías y Tecnologías

- JSF con Primefaces 15.0.16 para frontend.
- No utilizar ningun otro framework de frontend, unicamente controles de primefaces.
- Java 25 para backend con estructura MVC (utilizando managed beans) y Singleton para conexión a base de datos (jdbc).

# Estructura de carpetas

- src/main/java: Contendrá el código fuente de la aplicación, incluyendo los managed beans, controladores y modelos.
  - carpeta beans para los managed beans
  - carpeta data para los objetos java
  - carpeta database para la conexión a la base de datos y las operaciones CRUD.
  - carpeta services para la lógica de negocio y servicios de la aplicación.
- src/main/resources: Contendrá los archivos de configuración, como el archivo de propiedades para la conexión a la base de datos y otros recursos necesarios.
- src/main/webapp: Contendrá los archivos JSP, XHTML y otros recursos web, como imágenes y hojas de estilo CSS.
