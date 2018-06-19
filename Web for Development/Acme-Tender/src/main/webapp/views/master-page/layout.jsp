
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="shortcut icon" href="favicon.ico" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="scripts/acme.js"></script>
<script type="text/javascript" src="scripts/cookiePopups.js"></script>  
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.js"></script>


<link href="http://netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="styles/side-menu.css" type="text/css">
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="styles/acme.css" type="text/css">
<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/cookie.css" type="text/css">
<link rel="stylesheet" href="styles/topnav.css" type="text/css">
<link rel="stylesheet" href="styles/displaytag.css" type="text/css">

<title><tiles:insertAttribute name="title" ignore="true" /></title>

<!-- Formats -->
<!-- Metemos en estas variables los formatos -->
<spring:message code="display.price.format" var="displaypriceformat" scope="request"/>
<spring:message code="display.date.format" var="displaydateformat" scope="request"/>
<spring:message code="display.date.time.format" var="displaydatetimeformat" scope="request"/>

<spring:message code="form.price.format" var="formpriceformat" scope="request"/>
<spring:message code="form.date.format" var="formdateformat" scope="request"/>
<spring:message code="form.date.time.format" var="formdatetimeformat" scope="request"/>

</head>

<body>

	<div class="top-menu">
		<tiles:insertAttribute name="top-menu" />
	</div>	

	<div class="nav-side-menu">
		<tiles:insertAttribute name="nav-side-menu" />
	</div>

	<div class="content">
		<div class="panel cuerpo">
			<jstl:if test="${message != null}">
				<br />
				<span class="message"><spring:message code="${message}" /></span>
			</jstl:if>
			
			<h2>
				<tiles:insertAttribute name="title" />
			</h2>
			<hr/>
	
			<tiles:insertAttribute name="body" />
		</div>
	</div>
	
	<div class="footer">
		<tiles:insertAttribute name="footer" />
	</div>	

</body>
</html>