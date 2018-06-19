<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="tenderResult/administrative/edit.do" modelAttribute="tenderResult" method="post">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="tender" />

	<acme:textarea code="tenderResult.description" path="description" />
	<acme:textbox code="tenderResult.tenderDate" path="tenderDate" css="formInput" placeholder="dd/MM/yyyy HH:mm"/>	
	<br />

	<acme:submit name="save" code="tenderResult.save" css="formButton toLeft" />&nbsp;
	
	<jstl:if test="${tenderResult.id == 0}" >
    	<acme:cancel url="tender/administrative/list.do" code="tenderResult.cancel" css="formButton toLeft" />
    </jstl:if>
	<jstl:if test="${tenderResult.id != 0}" >
    	<acme:cancel url="tenderResult/display.do?tenderResultId=${tenderResult.id}" code="tenderResult.cancel" css="formButton toLeft" />
    </jstl:if>
    
	<br />

</form:form>