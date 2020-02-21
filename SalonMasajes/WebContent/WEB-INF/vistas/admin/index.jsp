<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/vistas/layout/head.jsp"%>

<div class="div-menu row">

	<%@ include file="/WEB-INF/vistas/layout/menu_admin.jsp"%>
	
	<section 
		class="container col col-sm-10 mt-4 justify-content-center"
		id="sesion">
		
		<h3>Sesiones</h3>
		
		<table class="table table-striped table-bordered table-hover table-sm table-responsive-xl">
			
			<thead class="head-tabla">
				<tr>
					<th>Id ?</th>
					<th>Cliente</th>
					<th>Trabajador</th>
					<th>Servicio</th>
					<th>Fecha - Hora</th>
					<th>Reseña</th>
					<th>Calificación</th>
					<th>Opciones</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${sesiones}" var="s">
					<tr>
						<td>${s.id}</td>
						<td>${s.cliente.nombre} ${s.cliente.apellidos}</td>
						<td>${s.trabajador.nombre} ${s.trabajador.apellidos}</td>
						<td>${s.servicio.nombre}</td>
						<td>
							<fmt:formatDate 
								value="${s.fecha}"
								pattern="dd-MM-yyyy HH:mm" 
							/>
						</td>
						<td>
							<a 
								class="text-reset"
								href="javascript:alert('${s.resena}')">
								${fn:substring(s.resena, 0, 20)}... </a>
						</td>
						<td>${s.calificacion}</td>
						<td class="text-center">
							<a class="btn-add btn btn-primary btn-sm" href="admin/add_sesion?id=${s.id}"><i class="fas fa-plus"></i></a>
							<a class="btn-edit btn btn-warning btn-sm" href="admin/add_sesion?id=${s.id}&op=modificar"><i class="far fa-edit"></i></a>
							<a class="btn-del btn btn-danger btn-sm" onclick="return confirm('¿Estás seguro?')" href="admin/borrarsesion?id=${s.id}"><i class="far fa-trash-alt"></i></a>
						</td>
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
					<th></th>
				</tr>
			</tfoot>
		</table>
	</section>
</div>

<%@ include file="/WEB-INF/vistas/layout/footer.jsp"%>