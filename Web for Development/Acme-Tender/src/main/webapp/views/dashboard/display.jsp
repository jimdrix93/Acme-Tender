
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="dashboard">

	<h5><spring:message code="dashboard.level"></spring:message> C</h5>
	<br>

	<!-- Dashboard C - 1. Tenders por administrative. -->
	<h3>
		<spring:message code="dashboard.administrative.tenders.administrative" />
	</h3>
	<display:table class="displaytag" name="c1Datos" id="row">
		<spring:message code="dashboard.administrative" var="nameAdministratives" />
		<display:column title="${nameAdministratives}">
			<jstl:out value="${row[1]}" />
		</display:column>
		<spring:message code="dashboard.number.tender" var="numberTender" />
		<display:column title="${numberTender}">
			<jstl:out value="${row[2]}" />
		</display:column>
	</display:table>
	
	
	<!-- Dashboard C - 2. Tenders by interest level. -->
	<h3>
		<spring:message code="dashboard.administrative.tenders.interest" />
	</h3>
	
	<display:table class="displaytag" name="c2Datos" id="row">
		<spring:message code="dashboard.interest.undefined" var="interestUndefined" />
		<display:column title="${interestUndefined}">
			<jstl:out value="${row[0]}" />
		</display:column>
		
		<spring:message code="dashboard.interest.high" var="interestHigh" />
		<display:column title="${interestHigh}">
			<jstl:out value="${row[1]}" />
		</display:column>
		
		<spring:message code="dashboard.interest.medium" var="interestMedium" />
		<display:column title="${interestMedium}">
			<jstl:out value="${row[2]}" />
		</display:column>
		
		<spring:message code="dashboard.interest.low" var="interestLow" />
		<display:column title="${interestLow}">
			<jstl:out value="${row[3]}" />
		</display:column>
		
	</display:table>
	
	<!-- Dashboard C - 3. Offers by state. -->
	<h3>
		<spring:message code="dashboard.administrative.offers.state" />
	</h3>
	
	<display:table class="displaytag" name="c3Datos" id="row">
		<spring:message code="dashboard.state.created" var="stateCreated" />
		<display:column title="${stateCreated}">
			<jstl:out value="${row[0]}" />
		</display:column>
		
		<spring:message code="dashboard.state.inDevelopment" var="stateInDevelopment" />
		<display:column title="${stateInDevelopment}">
			<jstl:out value="${row[1]}" />
		</display:column>
		
		<spring:message code="dashboard.state.abandoned" var="stateAbandoned" />
		<display:column title="${stateAbandoned}">
			<jstl:out value="${row[2]}" />
		</display:column>
		
		<spring:message code="dashboard.state.presented" var="statePresented" />
		<display:column title="${statePresented}">
			<jstl:out value="${row[3]}" />
		</display:column>
		
		<spring:message code="dashboard.state.winned" var="stateWinned" />
		<display:column title="${stateWinned}">
			<jstl:out value="${row[4]}" />
		</display:column>
		
		<spring:message code="dashboard.state.lossed" var="stateLossed" />
		<display:column title="${stateLossed}">
			<jstl:out value="${row[5]}" />
		</display:column>
		
		<spring:message code="dashboard.state.challenged" var="stateChallenged" />
		<display:column title="${stateChallenged}">
			<jstl:out value="${row[6]}" />
		</display:column>
		
		<spring:message code="dashboard.state.denied" var="stateDenied" />
		<display:column title="${stateDenied}">
			<jstl:out value="${row[7]}" />
		</display:column>
		
	</display:table>
	
	
	<!-- Dashboard C - 4. Offers by State and Executive. -->
	<h3>
		<spring:message code="dashboard.administrative.offers.state.executive" />
	</h3>
	
	<display:table class="displaytag" name="c4Datos" id="row">
		
		<spring:message code="dashboard.executive.name" var="executiveName" />
		<display:column title="${executiveName}">
			<jstl:out value="${row[0]}" />
		</display:column>
		
		<spring:message code="dashboard.state.created" var="stateCreated" />
		<display:column title="${stateCreated}">
			<jstl:out value="${row[1]}" />
		</display:column>
		
		<spring:message code="dashboard.state.inDevelopment" var="stateInDevelopment" />
		<display:column title="${stateInDevelopment}">
			<jstl:out value="${row[2]}" />
		</display:column>
		
		<spring:message code="dashboard.state.abandoned" var="stateAbandoned" />
		<display:column title="${stateAbandoned}">
			<jstl:out value="${row[3]}" />
		</display:column>
		
		<spring:message code="dashboard.state.presented" var="statePresented" />
		<display:column title="${statePresented}">
			<jstl:out value="${row[4]}" />
		</display:column>
		
		<spring:message code="dashboard.state.winned" var="stateWinned" />
		<display:column title="${stateWinned}">
			<jstl:out value="${row[5]}" />
		</display:column>
		
		<spring:message code="dashboard.state.lossed" var="stateLossed" />
		<display:column title="${stateLossed}">
			<jstl:out value="${row[6]}" />
		</display:column>
		
		<spring:message code="dashboard.state.challenged" var="stateChallenged" />
		<display:column title="${stateChallenged}">
			<jstl:out value="${row[7]}" />
		</display:column>
		
		<spring:message code="dashboard.state.denied" var="stateDenied" />
		<display:column title="${stateDenied}">
			<jstl:out value="${row[8]}" />
		</display:column>
		
	</display:table>

	<!-- Dashboard C - 5. Offers by state ratio. -->
	<h3>
		<spring:message code="dashboard.administrative.offers.state.ratio" />
	</h3>
	
	<display:table class="displaytag" name="c5Datos" id="row">
		<spring:message code="dashboard.state.created" var="stateCreated" />
		<display:column title="${stateCreated}">
			<jstl:out value="${row[0]}" />
		</display:column>
		
		<spring:message code="dashboard.state.inDevelopment" var="stateInDevelopment" />
		<display:column title="${stateInDevelopment}">
			<jstl:out value="${row[1]}" />
		</display:column>
		
		<spring:message code="dashboard.state.abandoned" var="stateAbandoned" />
		<display:column title="${stateAbandoned}">
			<jstl:out value="${row[2]}" />
		</display:column>
		
		<spring:message code="dashboard.state.presented" var="statePresented" />
		<display:column title="${statePresented}">
			<jstl:out value="${row[3]}" />
		</display:column>
		
		<spring:message code="dashboard.state.winned" var="stateWinned" />
		<display:column title="${stateWinned}">
			<jstl:out value="${row[4]}" />
		</display:column>
		
		<spring:message code="dashboard.state.lossed" var="stateLossed" />
		<display:column title="${stateLossed}">
			<jstl:out value="${row[5]}" />
		</display:column>
		
		<spring:message code="dashboard.state.challenged" var="stateChallenged" />
		<display:column title="${stateChallenged}">
			<jstl:out value="${row[6]}" />
		</display:column>
		
		<spring:message code="dashboard.state.denied" var="stateDenied" />
		<display:column title="${stateDenied}">
			<jstl:out value="${row[7]}" />
		</display:column>
		
	</display:table>
	
	<!-- Dashboard C - 6. Offers by State and Executive Ratio. -->
	<h3>
		<spring:message code="dashboard.administrative.offers.state.executive.ratio" />
	</h3>
	
	<display:table class="displaytag" name="c6Datos" id="row">
		
		<spring:message code="dashboard.executive.name" var="executiveName" />
		<display:column title="${executiveName}">
			<jstl:out value="${row[0]}" />
		</display:column>
		
		<spring:message code="dashboard.state.created" var="stateCreated" />
		<display:column title="${stateCreated}">
			<jstl:out value="${row[1]}" />
		</display:column>
		
		<spring:message code="dashboard.state.inDevelopment" var="stateInDevelopment" />
		<display:column title="${stateInDevelopment}">
			<jstl:out value="${row[2]}" />
		</display:column>
		
		<spring:message code="dashboard.state.abandoned" var="stateAbandoned" />
		<display:column title="${stateAbandoned}">
			<jstl:out value="${row[3]}" />
		</display:column>
		
		<spring:message code="dashboard.state.presented" var="statePresented" />
		<display:column title="${statePresented}">
			<jstl:out value="${row[4]}" />
		</display:column>
		
		<spring:message code="dashboard.state.winned" var="stateWinned" />
		<display:column title="${stateWinned}">
			<jstl:out value="${row[5]}" />
		</display:column>
		
		<spring:message code="dashboard.state.lossed" var="stateLossed" />
		<display:column title="${stateLossed}">
			<jstl:out value="${row[6]}" />
		</display:column>
		
		<spring:message code="dashboard.state.challenged" var="stateChallenged" />
		<display:column title="${stateChallenged}">
			<jstl:out value="${row[7]}" />
		</display:column>
		
		<spring:message code="dashboard.state.denied" var="stateDenied" />
		<display:column title="${stateDenied}">
			<jstl:out value="${row[8]}" />
		</display:column>
		
	</display:table>
	
	<!-- Dashboar B.1  -->
	<display:table class="displaytag" name="consultaB1" id="row">
		
		<spring:message code="dashboard.executive.name" var="executiveName" />
		<display:column title="${executiveName}">
			<jstl:out value="${row[0]}" />
		</display:column>
		
		<spring:message code="dashboard.state.created" var="stateCreated" />
		<display:column title="${stateCreated}">
			<jstl:out value="${row[1]}" />
		</display:column>
		
		<spring:message code="dashboard.state.inDevelopment" var="stateInDevelopment" />
		<display:column title="${stateInDevelopment}">
			<jstl:out value="${row[2]}" />
		</display:column>
		
		<spring:message code="dashboard.state.abandoned" var="stateAbandoned" />
		<display:column title="${stateAbandoned}">
			<jstl:out value="${row[3]}" />
		</display:column>
		
		<spring:message code="dashboard.state.presented" var="statePresented" />
		<display:column title="${statePresented}">
			<jstl:out value="${row[4]}" />
		</display:column>
		
		<spring:message code="dashboard.state.winned" var="stateWinned" />
		<display:column title="${stateWinned}">
			<jstl:out value="${row[5]}" />
		</display:column>
		
		<spring:message code="dashboard.state.lossed" var="stateLossed" />
		<display:column title="${stateLossed}">
			<jstl:out value="${row[6]}" />
		</display:column>
		
		<spring:message code="dashboard.state.challenged" var="stateChallenged" />
		<display:column title="${stateChallenged}">
			<jstl:out value="${row[7]}" />
		</display:column>
		
		<spring:message code="dashboard.state.denied" var="stateDenied" />
		<display:column title="${stateDenied}">
			<jstl:out value="${row[8]}" />
		</display:column>
		
	</display:table>	
	
</div>

<div class="dashboard">

	<h5><spring:message code="dashboard.level"></spring:message> B</h5>
	<br>
	<h3>
		<spring:message code="dashboard.last.month.top10.offers" />
	</h3>
	<display:table class="displaytag" name="consultaB1" id="row">
		<acme:column property="amount" title="offer.amount"/>
		<acme:column property="commercial.userAccount.username" title="offer.commercial"/>
		<acme:column property="tender.title" title="offer.tender.title"/>
		<acme:column property="presentationDate" title="offer.presentationDate"/>
	</display:table>
	<br>
	<h3>
		<spring:message code="dashboard.last.3month.top10.wined.offers" />
	</h3>
	<display:table class="displaytag" name="consultaB2" id="row">
		<acme:column property="amount" title="offer.amount"/>
		<acme:column property="commercial.userAccount.username" title="offer.commercial"/>
		<acme:column property="tender.title" title="offer.tender.title"/>
		<acme:column property="presentationDate" title="offer.presentationDate"/>
	</display:table>
	<br>
	<h3>
		<spring:message code="dashboard.average.ratio.amount.offer.tender" />
	</h3>
	<display:table class="displaytag" name="consultaB3" id="row">
		<spring:message code="companyResult.company" var="titleCompanyName" />
		<display:column title="${titleCompanyName}">
			<jstl:out value="${row[0]}" />
		</display:column>
		<spring:message code="dashboard.ratio" var="titleRatio" />
		<display:column title="${titleRatio}">
			<jstl:out value="${row[1]}" />
		</display:column>
	</display:table>
	<br>
	<h3>
		<spring:message code="dashboard.Top10.offers.companies" />
	</h3>
	<display:table class="displaytag" name="consultaB4" id="row">
		<spring:message code="companyResult.company" var="titleCompanyName" />
		<display:column title="${titleCompanyName}">
			<jstl:out value="${row[0]}" />
		</display:column>
		<spring:message code="dashboard.number" var="titleRatio" />
		<display:column title="${titleRatio}">
			<jstl:out value="${row[1]}" />
		</display:column>
	</display:table>
	<br>
	<h3>
		<spring:message code="dashboard.Top10.winned.companies" />
	</h3>
	<display:table class="displaytag" name="consultaB5" id="row">
		<spring:message code="companyResult.company" var="titleCompanyName" />
		<display:column title="${titleCompanyName}">
			<jstl:out value="${row[0]}" />
		</display:column>
		<spring:message code="dashboard.number" var="titleRatio" />
		<display:column title="${titleRatio}">
			<jstl:out value="${row[1]}" />
		</display:column>
	</display:table>
	<br>
	
</div>
<div class="dashboard">

	<h5><spring:message code="dashboard.level"></spring:message> A</h5>
	<br>
	<h3>
		<spring:message code="dashboard.high.interest.tenders" />
		<spring:message code="dashboard.high.interest.no.offer.tenders" />
	</h3>
	
	<display:table class="displaytag" name="consultaA1" id="row">
		<acme:column property="organism" title="tender.organism"/>		
		<spring:message code="tender.title" var="tituloColumna"/>
		<display:column title="${tituloColumna}" class="${classTd}">
			<div>
				<a href="tender/display.do?tenderId=${row.id}"> 
					<jstl:out value="${row.title}"/>
				</a>
			</div>
		</display:column>
		<acme:column property="maxPresentationDate" title="tender.maxPresentationDate"/>
		<acme:column property="executionTime" title="tender.executionTime"/>
		<acme:column property="estimatedAmount" title="tender.estimatedAmount"/>
	</display:table>
	<br>
	<h3>
		<spring:message code="dashboard.high.interest.tenders" />
		<spring:message code="dashboard.high.interest.abandoned.tenders" />
	</h3>
	
	<display:table class="displaytag" name="consultaA2" id="row">
		
		<acme:column property="organism" title="tender.organism"/>		
		<spring:message code="tender.title" var="tituloColumna"/>
		<display:column title="${tituloColumna}" class="${classTd}">
			<div>
				<a href="tender/display.do?tenderId=${row.id}"> 
					<jstl:out value="${row.title}"/>
				</a>
			</div>
		</display:column>
		<acme:column property="maxPresentationDate" title="tender.maxPresentationDate"/>
		<acme:column property="executionTime" title="tender.executionTime"/>
		<acme:column property="estimatedAmount" title="tender.estimatedAmount"/>
		
	</display:table>
	<br>
	<!-- Listado de solicitudes administrativas rechazadas por el usuario administrativo, mostrando el motivo. -->
	<h3>
		<spring:message code="dashboard.A3" />
	</h3>
	<display:table class="displaytag" name="consultaA3" id="row">
		<spring:message code="offer.tender.title" var="tituloColumna"/>
		<display:column title="${tituloColumna}" class="${classTd}">
			<div>
				<a href="tender/display.do?tenderId=${row.offer.tender.id}"> 
					<jstl:out value="${row.offer.tender.title}"/>
				</a>
			</div>
		</display:column>
		<acme:column property="administrative.userAccount.username" title="actor.name"/>
		<acme:column property="maxAcceptanceDate" title="tender.executionTime"/>
		<acme:column property="rejectedReason" title="administrativeRequest.rejectedReason"/>
		<acme:column property="offer.amount" title="offer.amount"/>
		
	</display:table>
	<br>
	<!-- Listado de solicitudes de colaboraciÃ³n rechazadas por comerciales,
		 * mostrando el motivo. --> 
	<h3>
		<spring:message code="dashboard.A4" />
	</h3>
	<display:table class="displaytag" name="consultaA4" id="row">
		<spring:message code="offer.tender.title" var="tituloColumna"/>
		<display:column title="${tituloColumna}" class="${classTd}">
			<div>
				<a href="tender/display.do?tenderId=${row.offer.tender.id}"> 
					<jstl:out value="${row.offer.tender.title}"/>
				</a>
			</div>
		</display:column>
		<acme:column property="commercial.userAccount.username" title="actor.name"/>
		<acme:column property="maxAcceptanceDate" title="tender.executionTime"/>
		<acme:column property="rejectedReason" title="administrativeRequest.rejectedReason"/>
		<acme:column property="offer.amount" title="offer.amount"/>
		
	</display:table>	
	<br>
	<!-- Media y desviación tÃ­pica del porcentaje de beneficios ofertado en Solicitudes de colaboracón aceptadas. -->

	<h3>
		<spring:message code="dashboard.A5" />
	</h3>
	<display:table class="displaytag" name="consultaA5" id="row">
		<spring:message code="dashboard.avg" var="title" />
		<display:column title="${title}">
			<jstl:out value="${row[0]}" />
		</display:column>
		<spring:message code="dashboard.dev" var="title" />
		<display:column title="${title}">
			<jstl:out value="${row[1]}" />
		</display:column>
	</display:table>
	<br>
	
	
	<!--  Media y desviacion tipica del porcentaje de beneficios ofertado en Solicitudes de colabora rechazadas. -->
	<h3>
		<spring:message code="dashboard.A6" />
	</h3>
	<display:table class="displaytag" name="consultaA6" id="row">
		<spring:message code="dashboard.avg" var="title" />
		<display:column title="${title}">
			<jstl:out value="${row[0]}" />
		</display:column>
		<spring:message code="dashboard.dev" var="title" />
		<display:column title="${title}">
			<jstl:out value="${row[1]}" />
		</display:column>
	</display:table>
	<br>
	
</div>
