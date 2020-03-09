<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/vistas/layout/head.jsp" %>

<div class="div-menu row">
	
	<%@ include file="/WEB-INF/vistas/layout/menu_admin.jsp"%>
	
	<section class="col col-sm-9 offset-2 mt-4" id="servicios">

	<h3 class="mb-4">Tabla Servicios</h3>
	 <table
		class="table table-striped table-borderless table-hover table-sm table-responsive-xl" >
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
		<tfoot class="foot-tabla">
				<tr>
					<th>Id</th>
					<th>Nombre</th>
					<th>Precio</th>
					<th></th>
				</tr>
		</tfoot>
	</table> 
</section>
</div>


<%@ include file="/WEB-INF/vistas/layout/footer.jsp" %>