<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/vistas/layout/head.jsp" %>

<section class="container col col-sm-6 mt-4 justify-content-center" id="servicios">

<h3 class="mb-4">Tabla Servicios</h3>
	 <table
		class="table table-striped table-bordered table-hover table-sm table-responsive" >
		<thead class="thead-dark">
			<tr>
				<th>Id</th>
				<th>Nombre</th>
				<th>Precio</th>
				<th class="pr-5">Opciones</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${servicios}" var="servicio">
				<tr>
					<th>${servicio.id}</th>
					<td>${servicio.nombre}</td>
					<td> <fmt:formatNumber value = "${servicio.precio}" type = "currency"/></td>
					<td class="text-center">
						<a class="btn btn-warning btn-sm" href="admin/add_servicio?id=${servicio.id}&op=modificar"><i class="far fa-edit"></i></a>
						<a class="btn btn-danger btn-sm" href="admin/borrarservicio?id=${servicio.id}"><i class="far fa-trash-alt"></i></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table> 

	<a class="btn btn-primary" href="admin/add_servicio?op=agregar"><span><i class="fas fa-plus"></i></span> Nuevo Servicio</a>

</section>

<%@ include file="/WEB-INF/vistas/layout/footer.jsp" %>