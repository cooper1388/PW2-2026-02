package hn.uth.servlet202602;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "alumnos", value = "/alumnos")
public class AlumnoServlet extends HttpServlet {

    private static final String TEMPLATE_PATH = "/alumnos.html";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isUnauthorized(request.getSession(false))) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        renderPage(request, response, "", "", "", "", "", "", "", "");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isUnauthorized(request.getSession(false))) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        String nombre = safeTrim(request.getParameter("nombre"));
        String cuenta = safeTrim(request.getParameter("cuenta"));
        String correo = safeTrim(request.getParameter("correo"));
        String carrera = safeTrim(request.getParameter("carrera"));
        String clase = safeTrim(request.getParameter("clase"));
        String seccion = safeTrim(request.getParameter("seccion"));

        if (nombre.isEmpty() || cuenta.isEmpty() || correo.isEmpty() || carrera.isEmpty() || clase.isEmpty() || seccion.isEmpty()) {
            renderPage(request, response,
                    "error",
                    "Debes completar todos los campos para registrar el alumno.",
                    nombre, cuenta, correo, carrera, clase, seccion);
            return;
        }

        if (!isValidEmail(correo)) {
            renderPage(request, response,
                    "error",
                    "Ingresa un correo valido para el alumno.",
                    nombre, cuenta, correo, carrera, clase, seccion);
            return;
        }

        renderPage(request, response,
                "success",
                "Alumno registrado correctamente: " + nombre + " (" + cuenta + ").",
                "", "", "", "", "", "");
    }

    private void renderPage(
            HttpServletRequest request,
            HttpServletResponse response,
            String messageType,
            String message,
            String nombre,
            String cuenta,
            String correo,
            String carrera,
            String clase,
            String seccion
    ) throws IOException {
        String template = loadTemplate();
        String cssPath = request.getContextPath() + "/alumnos.css";
        String actionPath = request.getContextPath() + "/alumnos";
        String loginPath = request.getContextPath() + "/";

        String finalHtml = template
                .replace("{{CSS_PATH}}", cssPath)
                .replace("{{ACTION_PATH}}", actionPath)
                .replace("{{LOGIN_PATH}}", loginPath)
                .replace("{{MESSAGE_CLASS}}", messageType.isEmpty() ? "feedback hidden" : "feedback " + messageType)
                .replace("{{MESSAGE_TEXT}}", escapeHtml(message))
                .replace("{{NOMBRE}}", escapeHtml(nombre))
                .replace("{{CUENTA}}", escapeHtml(cuenta))
                .replace("{{CORREO}}", escapeHtml(correo))
                .replace("{{CARRERA}}", escapeHtml(carrera))
                .replace("{{CLASE}}", escapeHtml(clase))
                .replace("{{SECCION}}", escapeHtml(seccion));

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(finalHtml);
    }

    private String loadTemplate() throws IOException {
        try (InputStream inputStream = getServletContext().getResourceAsStream(TEMPLATE_PATH)) {
            if (inputStream == null) {
                throw new IOException("No se encontro la plantilla " + TEMPLATE_PATH);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private boolean isUnauthorized(HttpSession session) {
        return session == null || !Boolean.TRUE.equals(session.getAttribute("loggedIn"));
    }

    private String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }

    private boolean isValidEmail(String email) {
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false;
        }
        if(!(parts[0].length() > 1 && parts[1].length() > 1)){
            return false;
        }
        return email.contains("@") && email.length() > 5 ;
    }

    private String escapeHtml(String value) {
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }
}

