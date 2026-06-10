<%@ page import="java.util.Map,java.util.HashMap" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Map<String, String> credenciales = (Map<String, String>) application.getAttribute("credenciales");
    if (credenciales == null) {
        credenciales = new HashMap<>();
        credenciales.put("admin", "admin123");
        credenciales.put("usuario", "user123");
        credenciales.put("alberto", "alberto123");
        application.setAttribute("credenciales", credenciales);
    }

    String rolActual = (String) session.getAttribute("usuarioRol");
    if ("admin".equals(rolActual)) {
        response.sendRedirect("admin.jsp");
        return;
    } else if ("usuario".equals(rolActual)) {
        response.sendRedirect("clientes.jsp");
        return;
    }

    String mensaje = "";
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");

        if (usuario != null && password != null && password.equals(credenciales.get(usuario))) {
            String rol = "admin".equals(usuario) ? "admin" : "usuario";
            session.setAttribute("usuarioNombre", usuario);
            session.setAttribute("usuarioRol", rol);
            if ("admin".equals(rol)) {
                response.sendRedirect("admin.jsp");
            } else {
                response.sendRedirect("clientes.jsp");
            }
            return;
        } else {
            mensaje = "Usuario o contrasena invalida.";
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-light">
<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-12 col-md-8 col-lg-5">
            <div class="card shadow-sm border-0">
                <div class="card-body p-4">
                    <h2 class="h4 mb-3">Inicio de sesión</h2>

                    <% if (!mensaje.isEmpty()) { %>
                    <div class="alert alert-danger" role="alert"><%= mensaje %></div>
                    <% } %>

                    <form method="post" action="index.jsp">
                        <div class="mb-3">
                            <label for="usuario" class="form-label">Usuario</label>
                            <input id="usuario" name="usuario" type="text" class="form-control" required />
                        </div>

                        <div class="mb-4">
                            <label for="password" class="form-label">Contraseña</label>
                            <input id="password" name="password" type="password" class="form-control" required />
                        </div>

                        <button type="submit" class="btn btn-primary w-100">Ingresar</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>