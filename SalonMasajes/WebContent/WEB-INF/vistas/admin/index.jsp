<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/vistas/layout/head.jsp"%>

<div class="div-menu row">
	<section class="section-panel col col-sm-2">
		<%@ include file="/WEB-INF/vistas/layout/menu_admin.jsp"%>
	</section>
	<section class="container col col-sm-10 mt-4 justify-content-center"
		id="sesiones">
		<h3>Sesiones</h3>
		<!-- <h3 class="mb-4">Registro de Sesiones</h3> -->
		<table
			class="table table-striped table-bordered table-hover table-sm table-responsive-xl">
			<thead class="head-tabla">
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
						<td><fmt:formatDate value="${s.fecha}"
								pattern="dd-MM-yyyy HH:mm" /></td>
						<td><a class="text-reset"
							href="javascript:alert('${s.resena}')">
								${fn:substring(s.resena, 0, 20)}... </a></td>
						<td>${s.calificacion}</td>
						<td class="text-center"><a
							class="btn-add btn btn-primary btn-sm" href="sesion"><i
								class="fas fa-plus"></i></a> <a
							class="btn-edit btn btn-warning btn-sm" href="sesion?id=${s.id}"><i
								class="far fa-edit"></i></a> <a
							class="btn-del btn btn-danger btn-sm" href="#"><i
								class="far fa-trash-alt"></i></a></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot class="foot-tabla">
				<tr>
					<th>Id</th>
					<th>Cliente</th>
					<th>Trabajador</th>
					<th>Servicio</th>
					<th>Fecha</th>
					<th>Reseña</th>
					<th>Calificación</th>
					<th class="text-center"><a href="sesion"
						class="btn-add btn btn-primary btn-sm"><span> <i
								class="fas fa-plus"></i>
						</span> Añadir</a></th>
				</tr>
			</tfoot>
		</table>

		<!-- <a class="btn btn-primary" href="admin/clientes?op=agregar">Añadir Sesión</a> -->
		<a class="btn-add btn btn-primary btn-sm"
			href="admin/clientes?op=agregar"><i class="fas fa-plus"></i>
			Añadir Cliente</a> <a class="btn-add btn btn-primary btn-sm"
			href="admin/clientes?op=agregar"><i class="fas fa-plus"></i>
			Añadir Trabajador</a> <a class="btn-add btn btn-primary btn-sm"
			href="admin/clientes?op=agregar"><i class="fas fa-plus"></i>
			Añadir Servicio</a>

	</section>
</div>

<%@ include file="/WEB-INF/vistas/layout/footer.jsp"%>