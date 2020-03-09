<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/vistas/layout/head.jsp" %>

<div class="div-menu row">

	<%@ include file="/WEB-INF/vistas/layout/menu_admin.jsp"%>
		
	<section  class="section-cliente col col-sm-9 offset-2 mt-4" id="clientes">

		<h3 class="mb-4">Tabla Clientes</h3>
		
			<table
				class="table table-striped table-borderless table-hover table-sm table-responsive-xl">
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
					<c:forEach items="${clientes}" var="cliente">
						<tr>
							<th>${cliente.id}</th>
							<td>${cliente.nombre}</td>
							<td>${cliente.apellidos}</td>
							<td>${cliente.dni}</td>
							<td class="text-center">
								<a class="btn-add btn btn-primary btn-sm" href="admin/add_cliente?op=agregar"><i class="fas fa-plus"></i></a>
								<a class="btn-edit btn btn-warning btn-sm" href="admin/add_cliente?id=${cliente.id}&op=modificar"><i class="far fa-edit"></i></a> 
								<a class="btn-del btn btn-danger btn-sm" href="admin/borrarcliente?id=${cliente.id}"><i class="far fa-trash-alt"></i></a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</section>
</div>


<%@ include file="/WEB-INF/vistas/layout/footer.jsp" %>