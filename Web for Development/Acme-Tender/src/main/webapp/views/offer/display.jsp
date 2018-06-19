
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
	<legend><spring:message code="offer.tender.data" /></legend>
	<acme:labelvalue code="offer.tender.reference" value="${offer.tender.reference}" />
	<acme:labelvalue code="offer.tender.title" value="${offer.tender.title}" />
</fieldset>

<fieldset>
	<legend><spring:message code="offer.data" /></legend>
		<jstl:choose>
			<jstl:when test="${offer.state eq 'CREATED' }">
				<spring:message code="offer.state.created" var="stateVar" />
			</jstl:when>
			<jstl:when test="${offer.state eq 'IN_DEVELOPMENT' }">
				<spring:message code="offer.state.indevelopment" var="stateVar" />
			</jstl:when>
			<jstl:when test="${offer.state eq 'ABANDONED' }">
				<spring:message code="offer.state.abandoned" var="stateVar" />
			</jstl:when>
			<jstl:when test="${offer.state eq 'PRESENTED' }">
				<spring:message code="offer.state.presented" var="stateVar" />
			</jstl:when>
			<jstl:when test="${offer.state eq 'WINNED' }">
				<spring:message code="offer.state.won" var="stateVar" />
			</jstl:when>
			<jstl:when test="${offer.state eq 'LOSED' }">
				<spring:message code="offer.state.lost" var="stateVar" />
			</jstl:when>
			<jstl:when test="${offer.state eq 'CHALLENGED' }">
				<spring:message code="offer.state.challenged" var="stateVar" />
			</jstl:when>
			<jstl:when test="${offer.state eq 'DENIED' }">
				<spring:message code="offer.state.denied" var="stateVar" />
			</jstl:when>
		</jstl:choose>
		<acme:labelvalue code="offer.state" value="${stateVar}" />
		
		<jstl:if test="${offer.presentationDate != null}" >
			<acme:labelvalue code="offer.presentationDate" value="${offer.presentationDate}" isDatetime="true" />
		</jstl:if>
		<acme:labelvalue code="offer.amount" value="${offer.amount}" isCurrency="true"/> 
		<acme:labelvalue code="offer.commercial" href="actor/display.do?actorId=${offer.commercial.id}" value="${offer.commercial.name} ${offer.commercial.surname}" />
		<acme:labelvalue code="offer.comision" value="${offer.amount * (benefitsPercentage/100)}" isCurrency="true" />
		<jstl:if test="${offer.denialReason != null}" >
			<acme:labelvalue code="offer.denialReason" value="${offer.denialReason}" /> 
		</jstl:if>
	
	<br/><br/>
	<jstl:if test="${offer.commercial.id == actor.id}" >
		<acme:button url="offer/commercial/edit.do?offerId=${offer.id}" text="offer.edit" css="formButton toLeft"/>
		<acme:button url="offer/commercial/listOffersByPropietary.do" text="offer.back" css="formButton toLeft"/>
		
		<jstl:if test="${offer.inDevelopment}" >
			<acme:button text="offer.createCollaborationRequest" url="collaborationRequest/commercial/create.do?offerId=${offer.id}" css="formButton toLeft" />
			<acme:button text="offer.createAdministrativeRequest" url="administrativeRequest/create.do?offerId=${offer.id}" css="formButton toLeft" />
		</jstl:if>
	</jstl:if>
	
	<security:authorize access="hasRole('EXECUTIVE')">
		<jstl:if test="${offer.inDevelopment}" >
			<acme:button text="offer.deny" url="offer/executive/edit.do?offerId=${offer.id}" css="formButton toLeft red" />
		</jstl:if>
	</security:authorize>
	
	<jstl:if test="${offer.commercial.id != actor.id}" >
		<jstl:if test="${offer.published}" >		
			<acme:button url="offer/list.do" text="offer.back" css="formButton toLeft"/>
		</jstl:if>
		<jstl:if test="${!offer.published}" >		
			<security:authorize access="hasRole('COMMERCIAL')">
				<acme:button url="offer/commercial/listOffersByCollaboration.do" text="offer.back" css="formButton toLeft"/>
			</security:authorize>
			<security:authorize access="hasRole('ADMINISTRATIVE')">
				<acme:button url="offer/administrative/listOffersByCollaboration.do" text="offer.back" css="formButton toLeft"/>
			</security:authorize>
			<security:authorize access="hasRole('EXECUTIVE')">
				<acme:button url="offer/executive/listNotPublished.do" text="offer.back" css="formButton toLeft"/>
			</security:authorize>
			<security:authorize access="hasRole('ADMIN')">
				<acme:button url="offer/administrator/listNotPublished.do" text="offer.back" css="formButton toLeft"/>
			</security:authorize>
		</jstl:if>
	</jstl:if>
</fieldset>


<fieldset>
	<legend><spring:message code="offer.administrative_acreditation" /></legend>
	<display:table class="displaytag" name="subSectionsAcreditation" id="row">
		
		<acme:column property="title" title="subSection.title" style="width:50%"/>
		<acme:column property="subsectionOrder" title="subSection.order" style="width:10%"/>
		
		<spring:message code="offer.subSection.property" var="offerSubSectionProperty" />
		<display:column title="${offerSubSectionProperty}" style="width:15%">
			<div>
				<jstl:if test="${row.commercial.id != null && row.commercial.id == actor.id}" >
					<spring:message code="offer.subSection.mine" />
				</jstl:if>
				<jstl:if test="${row.administrative.id != null && row.administrative.id == actor.id}" >
					<spring:message code="offer.subSection.mine" />
				</jstl:if>			
				<jstl:if test="${row.commercial.id != null && row.commercial.id != actor.id}" >
					<a href="actor/display.do?actorId=${row.commercial.id}">
						${row.commercial.name} ${row.commercial.surname}
					</a>
				</jstl:if>
				<jstl:if test="${row.administrative.id != null && row.administrative.id != actor.id}" >
					<a href="actor/display.do?actorId=${row.administrative.id}">
						${row.administrative.name} ${row.administrative.surname}
					</a>
				</jstl:if>
			</div>
		</display:column>
	
		<acme:column property="lastReviewDate" title="subSection.lastReviewDate" format="display.date.time.format" style="width:15%"/>
	
		<display:column style="width:10%">
			<div>
				<a href="subSection/display.do?subSectionId=${row.id}"> 
					<spring:message code="master.page.display" />
				</a>
			</div>
		</display:column>
			
	</display:table>
</fieldset>

<fieldset>
	<legend><spring:message code="offer.technical_offer" /></legend>
	
	<display:table class="displaytag" name="subSectionsTechnical"  id="row">
	
		<acme:column property="title" title="subSection.title" style="width:50%"/>
		<acme:column property="subsectionOrder" title="subSection.order" style="width:10%"/>
		
		<spring:message code="offer.subSection.property" var="offerSubSectionProperty" />
		<display:column title="${offerSubSectionProperty}" style="width:15%">
			<div>
				<jstl:if test="${row.commercial.id != null && row.commercial.id == actor.id}" >
					<spring:message code="offer.subSection.mine" />
				</jstl:if>
				<jstl:if test="${row.administrative.id != null && row.administrative.id == actor.id}" >
					<spring:message code="offer.subSection.mine" />
				</jstl:if>			
				<jstl:if test="${row.commercial.id != null && row.commercial.id != actor.id}" >
					<a href="actor/display.do?actorId=${row.commercial.id}">
						${row.commercial.name} ${row.commercial.surname}
					</a>
				</jstl:if>
				<jstl:if test="${row.administrative.id != null && row.administrative.id != actor.id}" >
					<a href="actor/display.do?actorId=${row.administrative.id}">
						${row.administrative.name} ${row.administrative.surname}
					</a>
				</jstl:if>
			</div>
		</display:column>	
		
		<acme:column property="lastReviewDate" title="subSection.lastReviewDate" format="display.date.time.format" style="width:15%"/>
	
		<display:column style="width:10%">
			<div>
				<a href="subSection/display.do?subSectionId=${row.id}"> 
					<spring:message code="master.page.display" />
				</a>
			</div>
		</display:column>
	
	</display:table>
</fieldset>


<fieldset>
	<legend><spring:message code="offer.economical_offer" /></legend>
	
	<display:table class="displaytag" name="subSectionsEconomical" id="row">
	
		<acme:column property="title" title="subSection.title" style="width:50%"/>
		<acme:column property="subsectionOrder" title="subSection.order" style="width:10%"/>
		
		<spring:message code="offer.subSection.property" var="offerSubSectionProperty" />
		<display:column title="${offerSubSectionProperty}" style="width:15%">
			<div>
				<jstl:if test="${row.commercial.id != null && row.commercial.id == actor.id}" >
					<spring:message code="offer.subSection.mine" />
				</jstl:if>
				<jstl:if test="${row.administrative.id != null && row.administrative.id == actor.id}" >
					<spring:message code="offer.subSection.mine" />
				</jstl:if>			
				<jstl:if test="${row.commercial.id != null && row.commercial.id != actor.id}" >
					<a href="actor/display.do?actorId=${row.commercial.id}">
						${row.commercial.name} ${row.commercial.surname}
					</a>
				</jstl:if>
				<jstl:if test="${row.administrative.id != null && row.administrative.id != actor.id}" >
					<a href="actor/display.do?actorId=${row.administrative.id}">
						${row.administrative.name} ${row.administrative.surname}
					</a>
				</jstl:if>
			</div>
		</display:column>	
		
		<acme:column property="lastReviewDate" title="subSection.lastReviewDate" format="display.date.time.format" style="width:15%"/>
	
		<display:column style="width:10%">
			<div>
				<a href="subSection/display.do?subSectionId=${row.id}"> 
					<spring:message code="master.page.display" />
				</a>
			</div>
		</display:column>
	</display:table>
</fieldset>

<security:authorize access="hasRole('COMMERCIAL')">
	<jstl:if test="${offer.inDevelopment && offer.commercial.id == actor.id}" >
		<acme:button text="subSection.create" url="subSection/commercial/create.do?offerId=${offer.id}" css="formButton toLeft" />
	</jstl:if>
</security:authorize>


