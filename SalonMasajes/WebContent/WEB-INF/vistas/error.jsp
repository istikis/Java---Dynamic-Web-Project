<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/vistas/layout/head.jsp" %>

<div class="jumbotron">
<h2 class="display-3 text-danger">HA HABIDO UN ERROR</h2>

<p>${exception.getMessage()}</p>
</div>
<%@ include file="/WEB-INF/vistas/layout/footer.jsp" %>