<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/vistas/layout/head.jsp" %>

<section 
	class="container col col-sm-10 mt-4 justify-content-center" 
	id="sesiones">
	<h3 class="mb-4">Registro de Sesiones</h3>
	<h3>Sesiones</h3>
		<table
			class="table table-striped table-bordered table-hover table-sm table-responsive-xl">
			<thead class="thead-dark">
				<tr>
					<th>Id</th>
					<th>Cliente</th>
					<th>Trabajador</th>
					<th>Servicio</th>
					<th>Fecha</th>
					<th>Reseña</th>
					<th>Calificación</th>
					<th>Opciones</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${sesiones}" var="s">
					<tr>
						<td>${s.id}</td>
						<td>${s.cliente.nombre}${s.cliente.apellidos}</td>
						<td>${s.trabajador.nombre}${s.trabajador.apellidos}</td>
						<td>${s.servicio.nombre}</td>
						<td><fmt:formatDate value="${s.fecha}" pattern="dd-MM-yyyy HH:mm" /></td>
						<td><a href="javascript:alert('${s.resena}')"><%-- ${fn:substring(s.resena, 0, 20)}... --%></a></td>
						<td>${s.calificacion}</td>
						<td><a href="sesion?id=${s.id}"
							class="btn btn-primary btn-sm">Editar</a> <a href="#"
							class="btn btn-danger btn-sm">Borrar</a></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot class="thead-dark">
				<tr>
					<th>Id</th>
					<th>Cliente</th>
					<th>Trabajador</th>
					<th>Servicio</th>
					<th>Fecha</th>
					<th>Reseña</th>
					<th>Calificación</th>
					<th><a href="sesion" class="btn btn-primary btn-sm">Añadir</a>
					</th>
				</tr>
			</tfoot>
		</table>

	<!-- <a class="btn btn-primary" href="admin/clientes?op=agregar">Añadir Sesión</a> -->
	<a class="btn btn-primary" href="admin/clientes?op=agregar"><span><i class="fas fa-plus"></i></span> Añadir Cliente</a>
	<a class="btn btn-primary" href="admin/clientes?op=agregar"><span><i class="fas fa-plus"></i></span> Añadir Trabajador</a>
	<a class="btn btn-primary" href="admin/clientes?op=agregar"><span><i class="fas fa-plus"></i></span> Añadir Servicio</a>

</section>

<%@ include file="/WEB-INF/vistas/layout/footer.jsp" %>