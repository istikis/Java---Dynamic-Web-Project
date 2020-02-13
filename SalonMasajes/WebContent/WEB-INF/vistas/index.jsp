<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/vistas/layout/head.jsp"%>


<header class="d-flex justify-content-center mt-4 mb-4">
	<h1 class="display-6">Nuestros Servicios!</h1>
</header>

<section id="masajes" class="row d-flex justify-content-around">

	<article id="masaje"
		class="masaje card col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 mb-5">
		<div class="card-body m-0">
			<a href="admin/index"><img src="img/logo.png" class="card-img-top" alt="masaje facial"></a>
			<h3 class="card-title text-justify text-center"><a href="vistas/masaje.jsp">Masaje Facial</a></h3>
			<p class="text-justify">Además de alisar y reafirmar, te garantiza una relajación óptima. El masaje facial permite 
				...</p>
		</div>
	</article>

	<article id="masaje"
		class="masaje card col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 mb-5">
		<div class="card-body m-0">
		<a href="vistas/masaje.jsp"><img src="img/logo.png" class="card-img-top" alt=""></a>
		<h3 class="card-title text-justify text-center"><a href="vistas/masaje.jsp">Drenaje Linfático</a></h3>
		<p class="text-justify">Además de alisar y reafirmar, te garantiza una relajación óptima. 
			El masaje facial perm<a href="admin/index">...Leer más</a></p>			
		</div>
	</article>

	<article id="masaje"
		class="masaje card col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 mb-5">
		<div class="card-body m-0">
			<a href="vistas/masaje.jsp"><img src="img/logo.png" class="card-img-top" alt="cobertura de algas"></a>
			<h3 class="card-title text-justify text-center"><a href="vistas/masaje.jsp">Cobertura de Algas</a></h3>
			<p class="text-justify p-0">Además de alisar y reafirmar, te garantiza una relajación óptima.</p>
		</div>
	</article>

	<article id="masaje"
		class="masaje card col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 mb-5">
		<div class="card-body m-0">
			<a href="vistas/masaje.jsp"><img src="img/logo.png" class="card-img-top" alt="cobertura de barro"></a>
			<h3 class="card-title text-justify text-center"><a href="vistas/masaje.jsp">Cobertura de Barro</a></h3>
			<p class="text-justify p-0">Además de alisar y reafirmar, te garantiza una relajación óptima.</p>
		</div>
	</article>
	
</section>

<%@ include file="/WEB-INF/vistas/layout/footer.jsp"%>