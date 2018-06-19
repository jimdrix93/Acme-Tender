<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="subSectionEvaluationCriteria/commercial/edit.do" modelAttribute="subSectionEvaluationCriteria" method="post">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="subSection" />

	<acme:textarea code="subSectionEvaluationCriteria.comments" path="comments" />
	<acme:select itemLabel="title" blankValue="false" items="${evaluationCriterias}" code="subSectionEvaluationCriteria.evaluationCriteria" path="evaluationCriteria" id="id" />
	<br />

	<acme:submit name="save" code="subSectionEvaluationCriteria.save" css="formButton toLeft" />&nbsp;
	
	<jstl:if test="${subSectionEvaluationCriteria.id != 0}">
		<acme:submit name="delete" code="subSectionEvaluationCriteria.delete" css="formButton toLeft" />&nbsp;
	</jstl:if>
    <acme:cancel url="/subSection/display.do?subSectionId=${subSectionId}" code="subSectionEvaluationCriteria.cancel" css="formButton toLeft" />

</form:form>