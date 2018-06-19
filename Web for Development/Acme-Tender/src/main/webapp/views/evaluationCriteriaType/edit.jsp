<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="evaluationCriteriaType/administrative/edit.do" modelAttribute="evaluationCriteriaType" method="post">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="evaluationCriteriaType.name" path="name" />
	<acme:textarea code="evaluationCriteriaType.description" path="description" />
	<br />

	<acme:submit name="save" code="evaluationCriteriaType.save" css="formButton toLeft" />&nbsp;
	<jstl:if test="${evaluationCriteriaType.id != 0}">
		<acme:submit name="delete" code="evaluationCriteriaType.delete" css="formButton toLeft" />&nbsp;
	</jstl:if>
    <acme:cancel url="/evaluationCriteriaType/administrative/list.do" code="evaluationCriteriaType.cancel" css="formButton toLeft" />

</form:form>