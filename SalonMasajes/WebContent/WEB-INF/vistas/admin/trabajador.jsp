<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/vistas/layout/head.jsp" %>


<div class="div-menu row">
	<section class="section-panel col col-sm-2">
		<%@ include file="/WEB-INF/vistas/layout/menu_admin.jsp"%>
	</section>
	<section  class="container col col-sm-8 mt-4 justify-content-center" id="trabajadores">
<h3 class="mb-4">Tabla Trabajadores</h3>
	<table
		class="table table-striped table-bordered table-hover table-sm table-responsive-xl">
		<thead class="head-tabla">
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
					<td class="text-center">
						<a class="btn-add btn btn-primary btn-sm" href="admin/add_trabajador?op=agregar"><i class="fas fa-plus"></i></a>
						<a class="btn-edit btn btn-warning btn-sm" href="admin/add_trabajador?id=${trabajador.id}&op=modificar"><span><i class="far fa-edit"></i></span></a>
						<a class="btn-del btn btn-danger btn-sm" href="admin/borrartrabajador?id=${trabajador.id}"><span><i class="far fa-trash-alt"></i></span></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<a class="btn btn-primary" href="admin/add_trabajador?op=agregar"><span><i class="fas fa-plus"></i></span> Añadir</a>

</section>
</div>


<%@ include file="/WEB-INF/vistas/layout/footer.jsp" %>