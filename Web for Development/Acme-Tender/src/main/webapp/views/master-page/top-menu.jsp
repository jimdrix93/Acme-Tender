<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>


<div class="center">
	<security:authorize access="isAnonymous()">
		<div style="float: left; margin-left: 30px; vertical-align: middle; height: 100%; margin-top: 5px;">
			<!-- Login y register -->
			<a href="security/login.do" style="float: left; margin-right: 10px;" >
				<img src="images/power.ico" title="Login" class="iconoenlace" />
			</a>
			<a href="actor/create.do" style="float: left;" > 
				<img src="images/key.ico" title="Register" class="iconoenlace" />
			</a> 
		</div>
	</security:authorize>
	
	<security:authorize access="isAuthenticated()">
		<div style="float: left; margin-left: 30px; vertical-align: middle; height: 100%; margin-top: 5px;">

			<a href="j_spring_security_logout" style="float: left;"> 
				<img src="images/power.ico" title="Logout" class="iconoenlace" />
			</a>
		</div>
	</security:authorize>	

	<div style="float: right; margin-right: 50px; vertical-align: middle; height: 100%; margin-top: 5px;">
		<security:authorize access="isAuthenticated()">
			<a href="actor/edit.do" title="Profile">
				<img src="images/profile.ico" title="Profile" class="iconoenlace" style="margin-bottom: 5px;" />
				<span class="nombre-usuario"><security:authentication property="principal.username" /> </span>
			</a>
		</security:authorize>	
		<a href="?language=es" ><img src="images/spain.ico" class="iconoenlace" style="margin-bottom: 5px;" title="Cambiar a español"/></a>
		<a href="?language=en" ><img src="images/uk.ico" class="iconoenlace" style="margin-bottom: 5px;" title="Change to english"/></a> 			
	</div>
</div>