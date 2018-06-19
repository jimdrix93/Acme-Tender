<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


	<spring:message code="subSectionEvaluationCriteria.evaluationCriteria.title" />: ${subSectionEvaluationCriteria.evaluationCriteria.title}  <br/>
	<spring:message code="subSectionEvaluationCriteria.evaluationCriteria.description" />: ${subSectionEvaluationCriteria.evaluationCriteria.description}  <br/>
	<spring:message code="subSectionEvaluationCriteria.evaluationCriteria.maxScore" />: ${subSectionEvaluationCriteria.evaluationCriteria.maxScore}  <br/><br/>

	<spring:message code="subSectionEvaluationCriteria.evaluationCriteriaType.name" />: ${subSectionEvaluationCriteria.evaluationCriteria.evaluationCriteriaType.name}  <br/>
	<spring:message code="subSectionEvaluationCriteria.evaluationCriteriaType.description" />: ${subSectionEvaluationCriteria.evaluationCriteria.evaluationCriteriaType.description}  <br/><br/>

	<spring:message code="subSectionEvaluationCriteria.comments" />: ${subSectionEvaluationCriteria.comments}  <br/>

<br>

<jstl:if test="${subSectionEvaluationCriteria.subSection.offer.inDevelopment && subSectionEvaluationCriteria.subSection.commercial.id == actorId}" >
	<acme:button url="subSectionEvaluationCriteria/commercial/edit.do?subSectionEvaluationCriteriaId=${subSectionEvaluationCriteria.id}" text="subSectionEvaluationCriteria.edit" css="formButton toLeft" />
</jstl:if>
<acme:button url="subSection/display.do?subSectionId=${subSectionEvaluationCriteria.subSection.id}" text="subSectionEvaluationCriteria.back" css="formButton toLeft" />


