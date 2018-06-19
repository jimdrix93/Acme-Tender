
<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="form">

	<form:form action="${requestUri}" modelAttribute="registerForm" >
		<form:hidden path="id" />		
	<br>
	
		<div class="seccion">
	<legend><spring:message code="actor.personal.data" /></legend>
		
		
			<br />
			<acme:textbox code="actor.name" path="name" css="formInput" readonly="${!edition}"/>
			<br />
			<acme:textbox code="actor.surname" path="surname" css="formInput" readonly="${!edition}" />
			<br />
			<acme:textbox code="actor.email" path="email" css="formInput" readonly="${!edition}" />
			<br />
			<acme:textbox code="actor.phone" path="phone" css="formInput" readonly="${!edition}" />
			<br />
			<acme:textbox code="actor.address" path="address" css="formInput" readonly="${!edition}" />
			<br />
		
		
	<jstl:if test="${!edition}">	
		<acme:backButton text="actor.back" css="formButton toLeft"/>
	</jstl:if>	
		</div>
	<jstl:if test="${edition}">
		<div class="seccion">
				
			
				<jstl:if test="${creation}">
						<legend><spring:message code="profile.userAccount" /></legend>
						<form:label path="${path}">
						<spring:message code="actor.authority.selection" />
					</form:label>
					<select id="authority" name="authority" class="formInput">
						<jstl:forEach items="${permisos}" var="permiso">
							<option value="${permiso}">
								<spring:message code="actor.authority.${permiso}" />
							</option>
							
						</jstl:forEach>
					</select>
					<br>
						<acme:textbox code="actor.username" path="username" css="formInput"/>
						<br />
						<acme:password code="profile.userAccount.password" path="password" css="formInput" id = "password" onkeyup="javascript: checkPassword();" />
						<br />
						<acme:password code="profile.userAccount.repeatPassword" path="confirmPassword"  id="confirm_password" css="formInput" onkeyup="javascript: checkPassword();"/>		
						<br>
				</jstl:if>
				<jstl:if test="${!creation}">
				<legend >
				<spring:message code="profile.userAccount" var="pass"/>
				<input type="button" value="${pass}" onclick="javascript: showUserAccount();" class="acordeon">
				</legend>	
				
					<div id="changePassword" style="display:none;">					
					<br>
					<form:hidden path="authority"/>
					<acme:label code="actor.authority" path="authority" />
					<acme:label code="actor.authority.${registerForm.authority}" path="authority" css="formInput" />
					<br />
					<acme:textbox code="actor.username" path="username" css="formInput"/>
					<br />
					<acme:password code="profile.userAccount.oldPassword" path="password" css="formInput" id = "password" onkeyup="javascript: checkEdition();" />
					<br />
					<acme:password code="profile.userAccount.newPassword" path="newPassword" css="formInput" id = "new_password" onkeyup="javascript: checkEdition();" />
					<br />
					<acme:password code="profile.userAccount.repeatPassword" path="confirmPassword"  id="confirm_password" css="formInput" onkeyup="javascript: checkEdition();"/>
					<br />
				</div>	
			</jstl:if>
	</div>
		<security:authorize access="isAnonymous()">
			<p class="terminos">
				<spring:message code="term.registration" />
			</p>
			<br />
		</security:authorize>
		<input type="submit" name="save" id="save"
			value='<spring:message code="actor.save"/>' class="formButton toLeft"/>&nbsp;
		<input type="button" name="cancel"
			value='<spring:message code="actor.cancel" />'
			onclick="javascript: relativeRedir('/');" class="formButton toLeft"/>
		<br />
	
	</jstl:if>
	
	</form:form>	

</div>