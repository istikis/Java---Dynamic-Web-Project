<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/vistas/layout/head.jsp" %>

<div class="row">

	<form 
		action="admin/add_trabajador" 
		method="post"
		class="offset-xl-3 offset-md-2 offset-sm-1 col-sm-10 col-md-8 col-xl-6">
		
		<fieldset>
		
			<legend class="mt-3">Trabajador</legend>

			<input type="hidden" id="op" name="op" value="${op}">

			<div class="form-group row">			
				<label for="id" class="col-sm-2 col-form-label">Id</label>				
				<div class="col-sm-10">
					<input 
						type="number" 
						class="form-control" 
						id="id" 
						name="id"
						value="${trabajador.id}" 
						readonly
						>
				</div>				
			</div>
			
			<div class="form-group row">			
				<label for="nombre" class="col-sm-2 col-form-label">Nombre</label>
				<div class="col-sm-10">
					<input 
						class="form-control" 
						id="nombre" 
						name="nombre"
						value="${trabajador.nombre}"
						>
					<div class="invalid-feedback"></div>
				</div>
			</div>
			
			<div class="form-group row">
				<label for="apellidos" class="col-sm-2 col-form-label">Apellidos</label>
				<div class="col-sm-10">
					<input 
						class="form-control ${primeravez ? '' : (trabajador.errorApellidos == null ? 'is-valid' : 'is-invalid') }" 
						id="apellidos" 
						name="apellidos"
						value="${trabajador.apellidos}"
						>
					<div class="invalid-feedback"></div>
				</div>
			</div>
			
			<div class="form-group row">
				<label for="dni" class="col-sm-2 col-form-label">DNI</label>
				<div class="col-sm-10">
					<input 
						class="form-control ${primeravez ? '' : (trabajador.errorDni == null ? 'is-valid' : 'is-invalid') }" 
						id="dni" 
						name="dni"
						value="${trabajador.dni}"
						>
					<div class="invalid-feedback"></div>
				</div>
			</div>
			<div class="form-group row">
				<div class="offset-sm-2 col-sm-10">
					<button type="submit" class="btn btn-primary">Añadir</button>
				</div>
			</div>
			
		</fieldset>
	</form>
</div>

<%@ include file="/WEB-INF/vistas/layout/footer.jsp" %>