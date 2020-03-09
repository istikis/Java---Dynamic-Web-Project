<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/vistas/layout/head.jsp"%>

<div class="row">
	<form action="login" method="post"
		class="offset-xl-3 offset-md-2 offset-sm-1 col-sm-10 col-md-8 col-xl-6">
		<fieldset>
			<legend>Inicio de Sesión</legend>

			<div class="form-group row">
				<label for="nombre" class="col-sm-2 col-form-label">Correo</label>
				<div class="col-sm-10">
					<input type="email" placeholder="admin@email.com / pass" class="form-control" id="email" name="email"
						value="${email}">
				</div>

			</div>
			<div class="form-group row">
				<label for="password" class="col-sm-2 col-form-label">Contraseña</label>
				<div class="col-sm-10">
					<input type="password" placeholder="pass" class="form-control" id="password" name="password">
				</div>

			</div>
			<div class="form-group row">
				<div class="offset-sm-2 col-sm-10">
					<button type="submit" class="btn btn-primary">Aceptar</button>
				</div>
			</div>
		</fieldset>
	</form>
</div>

<%@ include file="/WEB-INF/vistas/layout/footer.jsp"%>