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
	<legend>
		<spring:message code="administrativeRequest.tender.data" />
	</legend>

	<acme:labelvalue code="administrativeRequest.tender.reference" value="${administrativeRequest.offer.tender.reference}" />
	<acme:labelvalue code="administrativeRequest.tender.title" value="${administrativeRequest.offer.tender.title} " />

</fieldset>

<fieldset>
	<legend>
		<spring:message code="administrativeRequest.data" />
	</legend>

	<jstl:choose>
		<jstl:when test="${principal eq administrativeRequest.offer.commercial }">
			<acme:labelvalue code="administrativeRequest.receiver"  value="${administrativeRequest.administrative.name} ${administrativeRequest.administrative.surname} " />
			<br />
		</jstl:when>
		<jstl:when test="${principal eq administrativeRequest.administrative }">
			<acme:labelvalue code="administrativeRequest.sender"  value="${administrativeRequest.offer.commercial.name} ${administrativeRequest.offer.commercial.surname}" />
			<br />
		</jstl:when>
	</jstl:choose>


	<acme:labelvalue code="administrativeRequest.subSectionTitle"   value="${administrativeRequest.subSectionTitle} " />
	<acme:labelvalue code="administrativeRequest.subSectionDescription"   value="${administrativeRequest.subSectionDescription}" />
	
	<spring:message code="date.pattern" var="dateFormat" />
	<fmt:formatDate value="${administrativeRequest.maxAcceptanceDate}" pattern="${dateFormat}" var="maxAcceptanceDateVar" />
	<acme:labelvalue code="administrativeRequest.maxAcceptanceDate"   value="${maxAcceptanceDateVar}" />
	
	<fmt:formatDate value="${administrativeRequest.maxDeliveryDate}" pattern="${dateFormat}" var="maxDeliveryDateVar" />
	<acme:labelvalue code="administrativeRequest.maxDeliveryDate"   value="${maxDeliveryDateVar}" />

	<jstl:choose>
		<jstl:when test="${administrativeRequest.accepted eq true }">
			<spring:message code="administrativeRequest.state.accepted" var="acceptedVar" />
		</jstl:when>
		<jstl:when test="${administrativeRequest.accepted eq false }">
			<spring:message code="administrativeRequest.state.rejected" var="acceptedVar" />
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="administrativeRequest.state.withoutReply" var="acceptedVar" />
		</jstl:otherwise>
	</jstl:choose>
	<acme:labelvalue code="administrativeRequest.state"   value="${acceptedVar}" />

	<jstl:if test="${not empty administrativeRequest.rejectedReason }">
		<acme:labelvalue code="administrativeRequest.rejectedReason"   value="${administrativeRequest.rejectedReason}" />
	</jstl:if>
</fieldset>


<jstl:choose>
	<jstl:when test="${principal eq administrativeRequest.offer.commercial }">
		<acme:cancel url="administrativeRequest/listSent.do" code="administrativeRequest.back" css="formButton toLeft" />
	</jstl:when>
	<jstl:when test="${principal eq administrativeRequest.administrative }">
		<acme:cancel url="administrativeRequest/listReceived.do" code="administrativeRequest.back" css="formButton toLeft" />
	</jstl:when>
</jstl:choose>


<jstl:if test="${principal eq administrativeRequest.administrative && administrativeRequest.accepted == null && !administrativeRequest.offer.published && administrativeRequest.answerable}">
	<acme:button url="administrativeRequest/reject.do?requestId=${administrativeRequest.id }" text="administrativeRequest.reject" css="formButton toLeft" />
	<acme:button url="administrativeRequest/accept.do?requestId=${administrativeRequest.id }" text="administrativeRequest.accept" css="formButton toLeft" />
</jstl:if>

