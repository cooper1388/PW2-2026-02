<%@ page import="java.util.Map,java.util.List,java.util.ArrayList,java.util.LinkedHashMap" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String rol = (String) session.getAttribute("usuarioRol");
    String usuarioSesion = (String) session.getAttribute("usuarioNombre");

    if (usuarioSesion == null || rol == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    if ("logout".equals(request.getParameter("accion"))) {
        session.invalidate();
        response.sendRedirect("index.jsp");
        return;
    }

    List<Map<String, String>> clientes = (List<Map<String, String>>) application.getAttribute("clientesRegistrados");
    if (clientes == null) {
        clientes = java.util.Collections.synchronizedList(new ArrayList<>());
        application.setAttribute("clientesRegistrados", clientes);
    }

    String mensajeCliente = "";
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String accion = request.getParameter("accion");
        if ("registrarCliente".equals(accion)) {
            String nombre = request.getParameter("nombreCliente");
            String email = request.getParameter("emailCliente");

            if (nombre == null || nombre.trim().isEmpty() || email == null || email.trim().isEmpty()) {
                mensajeCliente = "Debe completar nombre y email del cliente.";
            } else {
                Map<String, String> cliente = new LinkedHashMap<>();
                cliente.put("nombre", nombre.trim());
                cliente.put("email", email.trim());
                cliente.put("registradoPor", usuarioSesion);
                clientes.add(cliente);
                mensajeCliente = "Cliente registrado correctamente.";
            }
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Registro de Clientes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <span class="navbar-brand">Formulario de clientes</span>
        <div class="d-flex align-items-center gap-2">
            <% if ("admin".equals(rol)) { %>
            <a href="admin.jsp" class="btn btn-outline-light btn-sm">Panel admin</a>
            <% } %>
            <a href="clientes.jsp?accion=logout" class="btn btn-light btn-sm">Cerrar sesión</a>
        </div>
    </div>
</nav>

<main class="container py-4">
    <div class="row g-4">
        <div class="col-12 col-lg-5">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h3 class="h5 mb-1">Registrar cliente</h3>
                    <p class="text-muted small">Usuario: <strong><%= usuarioSesion %></strong> | Rol: <strong><%= rol %></strong></p>

                    <% if (!mensajeCliente.isEmpty()) { %>
                    <div class="alert alert-success" role="alert"><%= mensajeCliente %></div>
                    <% } %>

                    <form method="post" action="clientes.jsp">
                        <input type="hidden" name="accion" value="registrarCliente" />

                        <div class="mb-3">
                            <label for="nombreCliente" class="form-label">Nombre</label>
                            <input id="nombreCliente" type="text" name="nombreCliente" class="form-control" required />
                        </div>

                        <div class="mb-3">
                            <label for="emailCliente" class="form-label">Email</label>
                            <input id="emailCliente" type="email" name="emailCliente" class="form-control" required />
                        </div>

                        <button type="submit" class="btn btn-success w-100">Guardar cliente</button>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-12 col-lg-7">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h4 class="h6 mb-3">Clientes registrados</h4>
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
        </div>
    </div>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>

