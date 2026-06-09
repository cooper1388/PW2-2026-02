<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Prueba JSP</title>
</head>
  <body>
    <h2>Su dirección IP es: <%= request.getRemoteAddr() %></h2>
    <br/>
    <h3>Su Host es: <%= request.getRemoteHost() %></h3>

    <form action="dashboard.jsp" method="post">
      <label for="nombre">Nombre: </label>
      <input id="nombre" name="nombre" type="text" placeholder="Nombre" />
      <input type="submit" value="Ingresar" />
    </form>
  </body>
</html>