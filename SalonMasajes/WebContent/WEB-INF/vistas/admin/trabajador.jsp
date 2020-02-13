<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/vistas/layout/head.jsp" %>

<section  class="container col col-sm-6 mt-4 justify-content-center" id="trabajadores">
<h3 class="mb-4">Tabla Trabajadores</h3>
	<table
		class="table table-striped table-bordered table-hover table-sm table-responsive">
		<thead>
			<tr>
				<th>Id</th>
				<th>Nombre</th>
				<th>Apellidos</th>
				<th>DNI</th>
				<th>Opciones</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${trabajadores}" var="trabajador">
				<tr>
					<th>${trabajador.id}</th>
					<td>${trabajador.nombre}</td>
					<td>${trabajador.apellidos}</td>
					<td>${trabajador.dni}</td>
					<td><a class="btn btn-warning" href="admin/add_trabajador?id=${trabajador.id}&op=modificar">Modificar</a> <a
						class="btn btn-danger" href="admin/borrartrabajador?id=${trabajador.id}">Borrar</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<a class="btn btn-primary" href="admin/add_trabajador?op=agregar">Añadir</a>

</section>

<%@ include file="/WEB-INF/vistas/layout/footer.jsp" %>