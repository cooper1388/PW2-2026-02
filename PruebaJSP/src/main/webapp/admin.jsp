<%@ page import="java.util.Map,java.util.List,java.util.ArrayList,java.util.LinkedHashMap" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String rol = (String) session.getAttribute("usuarioRol");
    String usuarioSesion = (String) session.getAttribute("usuarioNombre");

    if (usuarioSesion == null || rol == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    if (!"admin".equals(rol)) {
        response.sendRedirect("clientes.jsp");
        return;
    }

    if ("logout".equals(request.getParameter("accion"))) {
        session.invalidate();
        response.sendRedirect("index.jsp");
        return;
    }

    Map<String, String> credenciales = (Map<String, String>) application.getAttribute("credenciales");
    if (credenciales == null) {
        response.getWriter().print("<h3 class='text-danger'>Error: No se encontraron las credenciales de usuario. Contacte al administrador del sistema.</h3>");
         return;
    }

    List<Map<String, String>> clientes = (List<Map<String, String>>) application.getAttribute("clientesRegistrados");
    if (clientes == null) {
        clientes = java.util.Collections.synchronizedList(new ArrayList<>());
        application.setAttribute("clientesRegistrados", clientes);
    }

    String mensajePassword = "";

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String accion = request.getParameter("accion");

        if ("cambiarPassword".equals(accion)) {
            String usuarioObjetivo = request.getParameter("usuarioObjetivo");
            String nuevaPassword = request.getParameter("nuevaPassword");

            if (usuarioObjetivo == null || nuevaPassword == null || nuevaPassword.trim().isEmpty()) {
                mensajePassword = "Debe seleccionar usuario y escribir la nueva contrasena.";
            } else if (!credenciales.containsKey(usuarioObjetivo)) {
                mensajePassword = "El usuario seleccionado no existe.";
            } else {
                credenciales.put(usuarioObjetivo, nuevaPassword);
                mensajePassword = "Contrasena actualizada para " + usuarioObjetivo + ".";
            }
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Panel Administrador</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <span class="navbar-brand">Panel administrativo</span>
        <div class="d-flex align-items-center gap-3 text-white">
            <span class="small">Usuario: <strong><%= usuarioSesion %></strong> | Rol: <strong><%= rol %></strong></span>
            <a href="admin.jsp?accion=logout" class="btn btn-outline-light btn-sm">Cerrar sesión</a>
        </div>
    </div>
</nav>

<main class="container py-4">
    <div class="row g-4">
        <div class="col-12 col-lg-6">
            <div class="card shadow-sm h-100">
                <div class="card-body">
                    <h3 class="h5 card-title mb-3">Cambiar contraseñas</h3>
                    <% if (!mensajePassword.isEmpty()) { %>
                    <div class="alert alert-info" role="alert"><%= mensajePassword %></div>
                    <% } %>
                    <form method="post" action="admin.jsp">
                        <input type="hidden" name="accion" value="cambiarPassword" />

                        <div class="mb-3">
                            <label for="usuarioObjetivo" class="form-label">Usuario</label>
                            <select id="usuarioObjetivo" name="usuarioObjetivo" class="form-select" required>
                                <% for (String usuario : credenciales.keySet()) { %>
                                <option value="<%= usuario %>"><%= usuario %></option>
                                <% } %>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="nuevaPassword" class="form-label">Nueva contraseña</label>
                            <input id="nuevaPassword" type="password" name="nuevaPassword" class="form-control" required />
                        </div>

                        <button type="submit" class="btn btn-primary">Actualizar contraseña</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="card shadow-sm mt-4">
        <div class="card-body">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="h6 m-0">Clientes registrados</h4>
                <a href="clientes.jsp" class="btn btn-outline-primary btn-sm">Ir al formulario de clientes</a>
            </div>
            <div class="table-responsive">
                <table class="table table-striped table-hover align-middle mb-0">
                    <thead class="table-dark">
                    <tr>
                        <th>Nombre</th>
                        <th>Email</th>
                        <th>Registrado por</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Map<String, String> cliente : clientes) { %>
                    <tr>
                        <td><%= cliente.get("nombre") %></td>
                        <td><%= cliente.get("email") %></td>
                        <td><%= cliente.get("registradoPor") %></td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>

