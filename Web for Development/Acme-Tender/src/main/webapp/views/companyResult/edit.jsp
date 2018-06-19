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

<form:form action="companyResult/administrative/edit.do" modelAttribute="companyResult" method="post">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="tenderResult" />

	<acme:textbox code="companyResult.name" path="name" />
	<acme:textbox code="companyResult.economicalOffer" path="economicalOffer" />
	<acme:input type="number" code="companyResult.score" path="score" css="formInput"/>
	<acme:input type="number" code="companyResult.position" path="position" css="formInput"/>
	<acme:textarea code="companyResult.comments" path="comments" />
	<acme:selectcombo code="companyResult.state" items="${companyResultStatesCombo}" path="state"/>	
	<br />

	<acme:submit name="save" code="companyResult.save" css="formButton toLeft" />
	
	<jstl:if test="${companyResult.id != 0}" >
		<acme:submit name="delete" code="companyResult.delete" css="formButton toLeft" />
	</jstl:if>
    <acme:cancel url="tenderResult/display.do?tenderResultId=${companyResult.tenderResult.id}" code="companyResult.cancel" css="formButton toLeft" />
	<br />

</form:form>