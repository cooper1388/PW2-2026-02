<%--
  Created by IntelliJ IDEA.
  User: KJ_Al
  Date: 8/6/2026
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dahboard</title>
</head>
<body>
<%
    String nombre = request.getParameter("nombre");
    if (nombre != null && !nombre.isEmpty()) {
%>
        <p> Hola <%= nombre %>! </p>
<% }else {
        %>
    <h3>Tiene que ingresar si nombre!</h3>
<%
} %>
</body>
</html>
