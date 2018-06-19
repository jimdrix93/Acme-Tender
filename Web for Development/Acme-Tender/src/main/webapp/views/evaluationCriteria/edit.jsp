<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="evaluationCriteria/administrative/edit.do" modelAttribute="evaluationCriteria">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="tender" />

	<acme:textbox path="title" code="evaluationCriteria.title" />
	<acme:textarea path="description" code="evaluationCriteria.description" />
	<acme:input type="number" path="maxScore" code="evaluationCriteria.maxScore" css="formInput"/>
	<acme:select itemLabel="name" blankValue="false" items="${evaluationCriteriaTypes}" path="evaluationCriteriaType" code="evaluationCriteria.evaluationCriteriaType" id="id"/>

	<br/>
	
	<acme:submit name="save" code="evaluationCriteria.save" css="formButton toLeft" />&nbsp;
	<jstl:if test="${evaluationCriteria.id != 0}">
		<acme:submit name="delete" code="evaluationCriteria.delete" css="formButton toLeft" />&nbsp;
	</jstl:if>	
	
 	 <acme:cancel url="/tender/display.do?tenderId=${tenderId}" code="evaluationCriteria.cancel" css="formButton toLeft" /> 

</form:form>