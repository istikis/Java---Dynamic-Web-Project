<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/vistas/layout/head.jsp" %>

<section  class="container col col-sm-10 mt-4 justify-content-center" id="sesiones">
<h3 class="mb-4">Registro de Sesiones</h3>
	<table
		class="table table-striped table-bordered table-hover table-sm table-responsive">
		<thead>
			<tr>
				<th>Id</th>
				<th>Nombre</th>
				<th>Apellidos</th>
				<th>DNI</th>
				<th>Servicio</th>
				<th>Precio</th>
				<th><span>Fecha de Servicio</span></th>
				<th>Especialista</th>
				<th>DNI</th>
				<th>Reseña</th>
				<th>Calificación</th>
				<th>Opciones</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${sesiones}" var="sesion">
				<tr>
					<th>${sesion.idCliente}</th>
					<td>${cliente.nombre}</td>
					<td>${cliente.apellidos}</td>
					<td>saczcsac</td>
					<td>cacadcd</td>
					<td>${cliente.idCliente}</td>
					<td>${cliente.nombre}</td>
					<td>${cliente.apellidos}</td>
					<td>saczcsac</td>
					<td>Reseña cliente</td>
					<td>saczcsac</td>
					<td><a class="btn btn-warning" href="admin/video?id=${cliente.idCliente}&op=modificar">Modificar</a> <a
						class="btn btn-danger" href="admin/borrar?id=${cliente.idCliente}">Borrar</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- <a class="btn btn-primary" href="admin/clientes?op=agregar">Añadir Sesión</a> -->
	<a class="btn btn-primary" href="admin/clientes?op=agregar">Añadir Cliente</a>
	<a class="btn btn-primary" href="admin/clientes?op=agregar">Añadir Trabajador</a>
	<a class="btn btn-primary" href="admin/clientes?op=agregar">Añadir Servicio</a>

</section>

<%@ include file="/WEB-INF/vistas/layout/footer.jsp" %>