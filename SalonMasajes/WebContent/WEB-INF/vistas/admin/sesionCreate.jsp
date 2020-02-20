<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/vistas/layout/head.jsp" %>

<div class="row">

	<%@ include file="/WEB-INF/vistas/layout/menu_admin.jsp"%>
	
	<form action="sesion" method="post"
		class="col col-sm-6 offset-sm-1 mt-4">
		<fieldset>
			<legend>Sesión</legend>

			<div class="form-group row">
				<label for="id" class="col-sm-2 col-form-label">Id</label>
				<div class="col-sm-10">
					<input type="number" class="form-control" id="id" name="id"
						value="${sesion.id}" readonly>
				</div>
			</div>

			<div class="form-group row">
				<label for="cliente" class="col-sm-2 col-form-label">Cliente</label>
				<div class="col-sm-10">
					<select
						class="form-control ${sesion != null ? (sesion.errorCliente == null ? 'is-valid' : 'is-invalid') : '' } "
						id="cliente" name="cliente">
						<option disabled selected value="">Selecciona un cliente</option>
						<c:forEach items="${clientes}" var="cliente">
							<option<%--  ${cliente.id == sesion.cliente.id ? 'selected': '' } --%> value="${cliente.id}">${cliente.nombre} ${cliente.apellidos}</option>
						</c:forEach>
					</select>
					<div class="invalid-feedback">${sesion.errorCliente}</div>
				</div>
			</div>

			<div class="form-group row">
				<label for="trabajador" class="col-sm-2 col-form-label">Trabajador</label>
				<div class="col-sm-10">
					<select
						class="form-control<%--  ${sesion != null ? (sesion.errorTrabajador == null ? 'is-valid' : 'is-invalid') : '' } --%>"
						id="trabajador" name="trabajador">
						<option disabled selected value="">Selecciona un trabajador</option>
						<c:forEach items="${trabajadores}" var="trabajador">
							<option <%-- ${trabajador.id == sesion.trabajador.id ? 'selected': '' } --%> value="${trabajador.id}">${trabajador.nombre} ${trabajador.apellidos}</option>
						</c:forEach>
					</select>
					<div class="invalid-feedback">${sesion.errorTrabajador}</div>
				</div>
			</div>

			<div class="form-group row">
				<label for="servicio" class="col-sm-2 col-form-label">Servicio</label>
				<div class="col-sm-10">
					<select
						class="form-control<%--  ${sesion != null ? (sesion.errorServicio == null ? 'is-valid' : 'is-invalid') : '' } --%>"
						id="servicio" name="servicio">
						<option disabled selected value="">Selecciona un servicio</option>
						<c:forEach items="${servicios}" var="servicio">
							<option <%-- ${servicio.id == sesion.servicio.id ? 'selected': '' } --%> value="${servicio.id}">${servicio.nombre}</option>
						</c:forEach>
					</select>
					<div class="invalid-feedback">${sesion.errorServicio}</div>
				</div>
			</div>

			<div class="form-group row">
				<label for="fecha" class="col-sm-2 col-form-label">Fecha</label>
				<div class="col-sm-10">
					<input type="datetime-local"
						class="form-control ${sesion != null ? (sesion.errorFecha == null ? 'is-valid' : 'is-invalid') : '' }"
						id="fecha" name="fecha"
						value="<fmt:formatDate value="${sesion.fecha}" type="both" pattern="yyyy-MM-dd'T'HH:mm" />" />
					<div class="invalid-feedback">${sesion.errorFecha}</div>
				</div>
			</div>
			<div class="form-group row">
				<label for="resena" class="col-sm-2 col-form-label">Reseña</label>
				<div class="col-sm-10">
					<textarea
						class="form-control ${sesion != null ? 'is-valid' : '' }"
						id="resena" name="resena">${sesion.resena}</textarea>
					<div class="invalid-feedback">¿Puedes escribir un poco más?</div>
				</div>
			</div>
			<div class="form-group row">
				<label for="calificacion" class="col-sm-2 col-form-label">Calificación</label>
				<div class="col-sm-10">
					<select
						class="form-control ${sesion != null ? (sesion.errorCalificacion == null ? 'is-valid' : 'is-invalid') : '' }"
						id="calificacion" name="calificacion">
						<option selected value="">No ha calificado</option>
						<option
							${sesion.calificacion == 'No recomendable' ? 'selected': '' }>No
							recomendable</option>
						<option ${sesion.calificacion == 'Aceptable' ? 'selected': '' }>Aceptable</option>
						<option ${sesion.calificacion == 'Para repetir' ? 'selected': '' }>Para
							repetir</option>
					</select>
					<div class="invalid-feedback">${sesion.errorCalificacion}</div>
				</div>
			</div>
			<div class="form-group row">
				<div class="offset-sm-2 col-sm-10">
					<button type="submit" class="btn btn-secondary">Aceptar</button>
				</div>
			</div>
		</fieldset>
	</form>
</div>
<%@ include file="/WEB-INF/vistas/layout/footer.jsp" %>