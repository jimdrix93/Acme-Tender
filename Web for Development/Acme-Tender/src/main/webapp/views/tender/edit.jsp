<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestUri}" modelAttribute="tender" method="post">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="administrative" />
	<form:hidden path="offer" />
	<form:hidden path="tenderResult" />
		
	<span class="toRight"><spring:message code="tender.notice.change.dates"	/></span>
		
	<h5>
		<acme:textbox code="tender.reference" path="reference" css="formInput" readonly="true" />
		<br />
	</h5>
	<acme:textbox code="tender.title" path="title" css="formInput" />
	<br />
	<!-- Combo para la categoría -->
	<form:label path="category">
		<spring:message code="tender.category" />
 	</form:label>
 	<br/>
 	<spring:message code="tender.select.option" var="selectOption"/>
	<form:select path="category" class="formInput">
	    <form:option value="" label="${selectOption}" />
	    <form:options items="${category}" itemValue="id" itemLabel="name"/>
	</form:select>
	<form:errors path="category" cssClass="error" />
	<br />	
	<acme:textbox code="tender.expedient" path="expedient" css="formInput" />
	<br />
	<acme:input code="tender.estimatedAmount" path="estimatedAmount" type="number" min="0.0" step="0.1" css="formInput"/>
	<br />
	<acme:textbox code="tender.organism" path="organism" css="formInput" />
	<br />
	<acme:textbox code="tender.bulletin" path="bulletin" css="formInput" />
	<br />
	
	<jstl:if test="${tender.id == 0}" >
		<acme:textbox code="tender.bulletinDate" path="bulletinDate" css="formInput" placeholder="dd/MM/yyyy HH:mm"/>
		<br />
		<acme:textbox code="tender.openingDate" path="openingDate" css="formInput" placeholder="dd/MM/yyyy HH:mm"/>
		<br />
		<acme:textbox code="tender.maxPresentationDate" path="maxPresentationDate" css="formInput" placeholder="dd/MM/yyyy HH:mm" />
		<br />
	</jstl:if>
	
	<jstl:if test="${tender.id != 0}" >
		<acme:textbox code="tender.bulletinDate" path="bulletinDate" css="formInput" placeholder="dd/MM/yyyy HH:mm" readonly="true"/>
		<br />
		<acme:textbox code="tender.openingDate" path="openingDate" css="formInput" placeholder="dd/MM/yyyy HH:mm" readonly="true"/>
		<br />
		<acme:textbox code="tender.maxPresentationDate" path="maxPresentationDate" css="formInput" placeholder="dd/MM/yyyy HH:mm" readonly="true"/>
		<br />
	</jstl:if>
	
	<acme:input type="number" code="tender.executionTime" path="executionTime" css="formInput"/>
	<br />
	<acme:textarea code="tender.observations" path="observations" css="formTextArea" />
	<br />
	<acme:textbox code="tender.informationPage" path="informationPage" css="formInput" />
	<br />
	<!-- Combo para el interés -->
	<form:label path="interest">
		<spring:message code="tender.interest" />
 	</form:label>
 	<br/>
 	<spring:message code="tender.select.option" var="selectOption"/>
	<form:select path="interest" class="formInput">
	    <form:option value="" label="${selectOption}" />
	    <form:options items="${interest}" />
	</form:select>
	<form:errors path="interest" cssClass="error" />
	<br />
	<acme:textarea code="tender.interestComment" path="interestComment" css="formTextArea" />
	<br />
	
	<acme:submit name="save" code="tenderResult.save" css="formButton toLeft" />&nbsp;
	
	<jstl:if test="${tender.id == 0}" >
    	<acme:cancel url="tender/administrative/list.do" code="tenderResult.cancel" css="formButton toLeft" />
    </jstl:if>
	<jstl:if test="${tender.id != 0}" >
    	<acme:cancel url="tender/display.do?tenderId=${tender.id}" code="tenderResult.cancel" css="formButton toLeft" />
    </jstl:if>
    
	<br />

</form:form>
