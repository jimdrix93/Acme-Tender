<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<form:form action="administrativeRequest/edit.do" modelAttribute="administrativeRequest">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:hidden path="offer" />

	<input type="hidden" name="reject" value="${reject }" />
	
	<jstl:if test="${administrativeRequest.id == 0 }">
	<acme:textbox code="administrativeRequest.subSectionTitle" path="subSectionTitle" />
	<br />
	
	<acme:textarea code="administrativeRequest.subSectionDescription" path="subSectionDescription" />
	<br />
	
	<acme:textbox code="administrativeRequest.maxAcceptanceDate" path="maxAcceptanceDate" placeholder="dd/MM/yyyy"/>
	<br />
	
	<acme:textbox code="administrativeRequest.maxDeliveryDate" path="maxDeliveryDate" placeholder="dd/MM/yyyy"/>
	<br />
	
	<acme:select code="administrativeRequest.administrative" itemLabel="userAccount.username" items="${administratives }" path="administrative" id ="id"/>
	<br />
	</jstl:if>
	
	<jstl:if test="${administrativeRequest.id !=0 }">
	<acme:textarea code="administrativeRequest.rejectedReason" path="rejectedReason" />
	<br />
	</jstl:if>
	
	<acme:submit name="save" code="administrativeRequest.save" css="formButton toLeft" />&nbsp;

	
	<jstl:choose>
		<jstl:when
			test="${administrativeRequest.id == 0}">
			<acme:cancel
				url="offer/display.do?offerId=${administrativeRequest.offer.id }"
				code="administrativeRequest.cancel" css="formButton toLeft" />
		</jstl:when>
		<jstl:otherwise>
			<acme:cancel
				url="administrativeRequest/display.do?administrativeRequestId=${administrativeRequest.id }"
				code="administrativeRequest.cancel" css="formButton toLeft" />
		</jstl:otherwise>
	</jstl:choose>


</form:form>