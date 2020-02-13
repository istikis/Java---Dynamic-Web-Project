<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/vistas/layout/head.jsp" %>

<section  class="container col col-sm-6 mt-4 justify-content-center" id="clientes">

<h3 class="mb-4">Tabla Clientes</h3>

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
			<c:forEach items="${clientes}" var="cliente">
				<tr>
					<th>${cliente.idCliente}</th>
					<td>${cliente.nombre}</td>
					<td>${cliente.apellidos}</td>
					<td>${cliente.dni}</td>
					<td>
						<a class="btn btn-warning" href="admin/add_cliente?id=${cliente.idCliente}&op=modificar">Modificar</a> 
						<a class="btn btn-danger" href="admin/borrarcliente?id=${cliente.idCliente}">Borrar</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<a class="btn btn-primary" href="admin/add_cliente?op=agregar">Añadir</a>

</section>

<%@ include file="/WEB-INF/vistas/layout/footer.jsp" %>