
<%@page import="org.springframework.test.web.ModelAndViewAssert"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<fieldset>
	<legend><spring:message code="subSection.tender.and.offer.data" /></legend>
	<acme:labelvalue code="offer.tender.reference" value="${subSection.offer.tender.reference}" />
	<acme:labelvalue code="offer.tender.title"  value="${subSection.offer.tender.title}" />
	<acme:labelvalue code="subSection.offer.state" value="${subSection.offer.state}" />
</fieldset>

<fieldset>
	<legend><spring:message code="subSection.data" /></legend>

	<acme:labelvalue code="subSection.title" value="${subSection.title}" />
	<acme:labelvalue code="subSection.section"  value="${subSection.section}" /> 
	<acme:labelvalue code="subSection.order"  value="${subSection.subsectionOrder}" />
	<acme:labelvalue code="subSection.shortDescription"  value="${subSection.shortDescription}" />
	<acme:labelvalue code="subSection.text"  value="${subSection.body}" /> 
	<acme:labelvalue code="subSection.lastReviewDate"  value="${subSection.lastReviewDate}" isDatetime="true" /> 
	<acme:labelvalue code="subSection.comments"  value="${subSection.comments}" /> 
	<br/><br/>

	<jstl:if test="${subSection.offer.inDevelopment && subSection.commercial.id == actorId}" >
		<acme:button text="subSection.edit" url="/subSection/commercial/edit.do?subSectionId=${subSection.id}" css="formButton toLeft" />
	</jstl:if>
	<jstl:if test="${subSection.offer.inDevelopment && subSection.administrative.id == actorId}" >
		<acme:button text="subSection.edit" url="/subSection/administrative/edit.do?subSectionId=${subSection.id}" css="formButton toLeft" />
	</jstl:if>
	<acme:button text="subSection.back" url="/offer/display.do?offerId=${subSection.offer.id}" css="formButton toLeft" />

</fieldset>

<jstl:if test="${subSection.section != 'ADMINISTRATIVE_ACREDITATION'}" >
	<fieldset>
		<legend><spring:message code="subSection.data.curriculums" /></legend>
	
		<display:table class="displaytag" name="curriculums" id="row">
			
			<acme:column property="name" title="curriculum.name" />
			<acme:column property="surname" title="curriculum.surname" />
			<acme:column property="dateOfBirth" title="curriculum.dateOfBirth" format="display.date.format"/>
			<acme:column property="minSalaryExpectation" title="curriculum.minSalaryExpectation" />
		
			<display:column>
				<div>
					<a href="curriculum/display.do?curriculumId=${row.id}"> 
						<spring:message code="master.page.display" />
					</a>
				</div>
			</display:column>
				
		</display:table>

		<jstl:if test="${subSection.offer.inDevelopment && subSection.commercial.id == actorId}" >
			<acme:button text="curriculum.create" url="/curriculum/create.do?subSectionId=${subSection.id}" css="formButton toLeft" />
		</jstl:if>
	</fieldset>
</jstl:if>

<!-- Archivos -->

<jstl:if test="${subSection.offer.inDevelopment && (subSection.commercial.id == actorId || subSection.administrative.id == actorId)}">
	<jstl:set var="allowed" value="true" />
	<jstl:set var="url" value="/file/create.do?id=${subSection.id}&type=subSection"/>
</jstl:if>

<%@ include file="/views/file/list.jsp" %> 

<!--  -->

<jstl:if test="${subSection.section != 'ADMINISTRATIVE_ACREDITATION'}" >
	<fieldset>
		<legend><spring:message code="subSection.data.evaluationCriterias" /></legend>
		
		<display:table class="displaytag" name="subSectionEvaluationCriterias" id="row">
			
			<acme:column property="evaluationCriteria.title" title="evaluationCriteria.title" />
			<acme:column property="evaluationCriteria.maxScore" title="evaluationCriteria.maxScore" />
			<acme:column property="comments" title="subSectionEvaluationCriteria.comments" />
			
			<display:column>
				<div>
					<a href="subSectionEvaluationCriteria/display.do?subSectionEvaluationCriteriaId=${row.id}"> 
						<spring:message code="master.page.display" />
					</a>
				</div>
			</display:column>	
			
		</display:table>
	
		<jstl:if test="${subSection.offer.inDevelopment && subSection.commercial.id == actorId && tenderHasEvaluationCriterias}" >
			<acme:button text="subSectionEvaluationCriteria.create" url="/subSectionEvaluationCriteria/commercial/create.do?subSectionId=${subSection.id}" css="formButton toLeft" />
		</jstl:if>
		<jstl:if test="${subSection.offer.inDevelopment && subSection.commercial.id == actorId && !tenderHasEvaluationCriterias}" >
			<spring:message code="subSection.cannot.add.subSectionEvaluationCriterias" />
		</jstl:if>
		
	</fieldset>
</jstl:if>
