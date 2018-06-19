<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<fieldset>
	<legend><spring:message code="collaborationRequest.tender.data" /></legend>

	<acme:labelvalue code="collaborationRequest.tender.reference"  value="${collaborationRequest.offer.tender.reference}" />
	<acme:labelvalue code="collaborationRequest.tender.title" value="${collaborationRequest.offer.tender.title} " />
</fieldset>

<fieldset>
	<legend><spring:message code="collaborationRequest.data" /></legend>
	<jstl:choose>
		<jstl:when test="${principal eq collaborationRequest.offer.commercial }">
			<acme:labelvalue code="collaborationRequest.receiver" value="${collaborationRequest.commercial.name} ${collaborationRequest.commercial.surname} " />
		</jstl:when>
		<jstl:when test="${principal eq collaborationRequest.commercial }">
			<acme:labelvalue code="collaborationRequest.sender" value="${collaborationRequest.offer.commercial.name} ${collaborationRequest.offer.commercial.surname}" />
		</jstl:when>
	</jstl:choose>


	<jstl:choose>
		<jstl:when test="${collaborationRequest.section eq 'TECHNICAL_OFFER' }">
			<spring:message code="collaborationRequest.section.technicaloffer" var="sectionVar" />
		</jstl:when>
		<jstl:when test="${collaborationRequest.section eq 'ECONOMICAL_OFFER' }">
			<spring:message code="collaborationRequest.section.economicaloffer" var="sectionVar" />
		</jstl:when>
	</jstl:choose>
	
	<acme:labelvalue code="collaborationRequest.section" value="${sectionVar}"/>
	<acme:labelvalue code="collaborationRequest.section"  value="${sectionVar} " />
	<acme:labelvalue code="collaborationRequest.subSectionTitle"   value="${collaborationRequest.subSectionTitle} " />
	<acme:labelvalue code="collaborationRequest.subSectionDescription"   value="${collaborationRequest.subSectionDescription}" />
	<acme:labelvalue code="collaborationRequest.benefitsPercentage"  value="${collaborationRequest.benefitsPercentage} " />
	<acme:labelvalue code="collaborationRequest.benefits.amount"  value="${(collaborationRequest.benefitsPercentage/100) * (benefitsPercentage/100) * collaborationRequest.offer.amount} " isCurrency="true" />
	
	
	<acme:labelvalue code="collaborationRequest.requirements"   value="${collaborationRequest.requirements}" />
	<acme:labelvalue code="collaborationRequest.numberOfPages"   value="${collaborationRequest.numberOfPages}" />
	
	<spring:message code="date.pattern" var="dateFormat" />
	<fmt:formatDate value="${collaborationRequest.maxAcceptanceDate}" pattern="${dateFormat}" var="maxAcceptanceDateVar" />	
	<acme:labelvalue code="collaborationRequest.maxAcceptanceDate"   value="${maxAcceptanceDateVar}" />
	
	<fmt:formatDate value="${collaborationRequest.maxDeliveryDate}" pattern="${dateFormat}" var="maxDeliveryDateVar" />	
	<acme:labelvalue code="collaborationRequest.maxDeliveryDate"   value="${maxDeliveryDateVar}" />


	<jstl:choose>
		<jstl:when test="${collaborationRequest.accepted eq true }">
			<spring:message code="collaborationRequest.state.accepted" var="acceptedVar" />
		</jstl:when>
		<jstl:when test="${collaborationRequest.accepted eq false }">
			<spring:message code="collaborationRequest.state.rejected" var="acceptedVar" />
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="collaborationRequest.state.withoutReply" var="acceptedVar" />
		</jstl:otherwise>
	</jstl:choose>
	<acme:labelvalue code="collaborationRequest.state"   value="${acceptedVar}" />

	<jstl:if test="${not empty collaborationRequest.rejectedReason }">
		<acme:labelvalue code="collaborationRequest.rejectedReason"  value="${collaborationRequest.rejectedReason}" />
	</jstl:if>
	<br />

</fieldset>

<jstl:choose>
	<jstl:when test="${principal eq collaborationRequest.offer.commercial }">
		<acme:cancel url="collaborationRequest/commercial/listSent.do" code="collaborationRequest.back" css="formButton toLeft" />
	</jstl:when>
	<jstl:when test="${principal eq collaborationRequest.commercial }">
		<acme:cancel url="collaborationRequest/commercial/listReceived.do" code="collaborationRequest.back" css="formButton toLeft" />
	</jstl:when>
</jstl:choose>


<jstl:if test="${principal eq collaborationRequest.commercial && collaborationRequest.accepted == null  && !collaborationRequest.offer.published && collaborationRequest.answerable}">
	<acme:button url="collaborationRequest/commercial/reject.do?requestId=${collaborationRequest.id }" text="collaborationRequest.reject" css="formButton toLeft" />
	<acme:button url="collaborationRequest/commercial/accept.do?requestId=${collaborationRequest.id }" text="collaborationRequest.accept" css="formButton toLeft" />
</jstl:if>

