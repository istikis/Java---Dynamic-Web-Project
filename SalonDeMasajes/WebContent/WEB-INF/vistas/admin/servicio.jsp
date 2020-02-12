<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/vistas/layout/head.jsp" %>

<section  class="container col col-sm-6 mt-4 justify-content-center" id="servicios">
<h3 class="mb-4">Tabla Servicios</h3>
	<%-- <table
		class="table table-striped table-bordered table-hover table-sm table-responsive">
		<thead>
			<tr>
				<th>Id</th>
				<th>Nombre</th>
				<th>Precio</th>
				<th>Opciones</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${servicios}" var="servicio">
				<tr>
					<th>${servicio.id}</th>
					<td>${servicio.nombre}</td>
					<td>${servicio.precio}</td>
					<td><a class="btn btn-warning" href="admin/servicios?id=${servicio.id}&op=modificar">Modificar</a> <a
						class="btn btn-danger" href="admin/borrar?id=${servicio.id}">Borrar</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table> --%>

	<a class="btn btn-primary" href="admin/servicios?op=agregar">Añadir</a>

</section>

<%@ include file="/WEB-INF/vistas/layout/footer.jsp" %>