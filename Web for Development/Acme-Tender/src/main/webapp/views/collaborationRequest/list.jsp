<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<display:table pagesize="5" class="displaytag" name="collaborationRequests" requestURI="${requestURI }" id="row">
	
	<acme:linkColumn linkUrl="tender/display.do?tenderId=${row.offer.tender.id}" 
						linkName="${row.offer.tender.reference}" 
						title="collaborationRequest.tender.reference" sortable="true" />
	
	<jstl:choose>
		<jstl:when test="${row.section eq 'TECHNICAL_OFFER' }">
			<spring:message code="collaborationRequest.section.technicaloffer" var="sectionVar" />
		</jstl:when>
		<jstl:when test="${row.section eq 'ECONOMICAL_OFFER' }">
			<spring:message code="collaborationRequest.section.economicaloffer" var="sectionVar" />
		</jstl:when>
	</jstl:choose>
	<spring:message code="collaborationRequest.section" var="codeSectionVar" />
	<display:column value="${sectionVar}" title="${codeSectionVar}" sortable="true" />

	<acme:column property="subSectionTitle" title="collaborationRequest.subSectionTitle" sortable="true" />

	<jstl:choose>
		<jstl:when test="${requestURI == 'collaborationRequest/commercial/listSent.do' }">
			<spring:message code="collaborationRequest.receiver" var="receiverVar" />
			<display:column value="${row.commercial.name } ${row.commercial.surname }" title="${receiverVar }" sortable="true" />
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="collaborationRequest.sender" var="senderVar" />
			<display:column value="${row.offer.commercial.name } ${row.offer.commercial.surname }" title="${senderVar }" sortable="true" />
		</jstl:otherwise>
	</jstl:choose>

	<spring:message code="date.pattern" var="dateFormat" />
	<spring:message code="collaborationRequest.maxAcceptanceDate" var="codeAcceptanceVar" />
	<fmt:formatDate value="${row.maxAcceptanceDate}" pattern="${dateFormat}" var="acceptanceVar" />
	<display:column value="${acceptanceVar }" title="${codeAcceptanceVar}" sortable="true" />


	<spring:message code="collaborationRequest.maxDeliveryDate" var="codeDeliveryVar" />
	<fmt:formatDate value="${row.maxDeliveryDate}" pattern="${dateFormat}" var="deliveryVar" />
	<display:column value="${deliveryVar }" title="${codeDeliveryVar}" sortable="true" />


	<jstl:choose>
		<jstl:when test="${row.accepted eq true }">
			<spring:message code="collaborationRequest.state.accepted" var="acceptedVar" />
		</jstl:when>
		<jstl:when test="${row.accepted eq false }">
			<spring:message code="collaborationRequest.state.rejected" var="acceptedVar" />
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="collaborationRequest.state.withoutReply" var="acceptedVar" />
		</jstl:otherwise>
	</jstl:choose>
	<spring:message code="collaborationRequest.state" var="codeAcceptedVar" />
	<display:column value="${acceptedVar}" title="${codeAcceptedVar}" sortable="true" />

	<display:column>
		<a href="collaborationRequest/commercial/display.do?collaborationRequestId=<jstl:out value="${row.id}"/>">
			<spring:message code="master.page.display" />
		</a>
	</display:column>


</display:table>