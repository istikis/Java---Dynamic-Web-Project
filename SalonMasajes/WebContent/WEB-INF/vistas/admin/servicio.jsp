<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/vistas/layout/head.jsp" %>

<div class="div-menu row">
	<section class="section-panel col col-sm-2">
		<%@ include file="/WEB-INF/vistas/layout/menu_admin.jsp"%>
	</section>
	<section class="container col col-sm-8 mt-4 justify-content-center" id="servicios">

<h3 class="mb-4">Tabla Servicios</h3>
	 <table
		class="table table-striped table-bordered table-hover table-sm table-responsive-xl" >
		<thead class="head-tabla">
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
					<td> <fmt:formatNumber value = "${servicio.precio}" type = "currency"/></td>
					<td class="text-center">
						<a class="btn-add btn btn-primary btn-sm" href="admin/add_servicio?op=agregar"><i class="fas fa-plus"></i></a>
						<a class="btn-edit btn btn-warning btn-sm" href="admin/add_servicio?id=${servicio.id}&op=modificar"><i class="far fa-edit"></i></a>
						<a class="btn-del btn btn-danger btn-sm" href="admin/borrarservicio?id=${servicio.id}"><i class="far fa-trash-alt"></i></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table> 

	<a class="btn btn-primary" href="admin/add_servicio?op=agregar"><span><i class="fas fa-plus"></i></span> Nuevo Servicio</a>

</section>
</div>


<%@ include file="/WEB-INF/vistas/layout/footer.jsp" %>