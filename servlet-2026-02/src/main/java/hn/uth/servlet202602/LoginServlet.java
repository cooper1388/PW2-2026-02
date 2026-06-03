package hn.uth.servlet202602;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("loggedIn", Boolean.FALSE);
        response.sendRedirect(request.getContextPath() + "/");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String email = safeTrim(request.getParameter("email"));
        String password = safeTrim(request.getParameter("clave"));
        String contextPath = request.getContextPath();
        String safeEmail = escapeHtml(email);

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='es'><head><meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Resultado de inicio de sesion</title>");
        out.println("<style>");
        out.println("body{margin:0;font-family:Segoe UI,Tahoma,Verdana,sans-serif;background:#f4f7fb;display:grid;place-items:center;min-height:100vh;padding:24px;}");
        out.println(".card{max-width:560px;width:100%;background:#fff;border-radius:14px;padding:24px;box-shadow:0 12px 32px rgba(47,91,234,.16);}");
        out.println("h1,h3{margin:0 0 12px;color:#111827;}");
        out.println("a{display:inline-block;margin-top:8px;color:#2f5bea;text-decoration:none;font-weight:600;}");
        out.println("a:hover{text-decoration:underline;}");
        out.println("</style></head><body><main class='card'>");

        if (email.isEmpty() || password.isEmpty()) {
            out.println("<h3>El correo o contrasena no pueden estar vacios.</h3>");
            out.println("<a href='" + contextPath + "/'>Reintentar</a>");
            out.println("</main></body></html>");
            return;
        }

        if ("kenny.cooper@uth.hn".equalsIgnoreCase(email) && "998877".equals(password)) {
            request.getSession(true).setAttribute("loggedIn", Boolean.TRUE);
            response.sendRedirect(contextPath + "/alumnos");
            return;
        } else {
            out.println("<h3>El correo " + safeEmail + " o contrasena es incorrecto.</h3>");
            out.println("<a href='" + contextPath + "/'>Reintentar</a>");
        }

        out.println("</main></body></html>");
    }

    private String safeTrim(String value) {
        return value == null ? "" : value.trim();
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