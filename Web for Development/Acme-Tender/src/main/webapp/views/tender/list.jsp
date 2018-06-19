<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jstl:if test="${pageSize == null}" >
		<jstl:set value="5" var="pageSize"/>
</jstl:if>
<form:form action="${requestUri}" method="GET">
		<spring:message code="pagination.size" />
		<input hidden="true" name="word" value="${word}">
		<input type="number" name="pageSize" min="1" max="100" value="${pageSize}">
  		<input type="submit" value=">" >
</form:form>
<display:table pagesize="${pageSize}" class="displaytag" name="tenders" requestURI="${requestUri}" id="row">

	<jstl:if test="${row.interest == 'UNDEFINED'}" >
		<spring:message var="rowCss" text="undefinedInterestColor" />
	</jstl:if>
	<jstl:if test="${row.interest == 'LOW'}" >
		<spring:message var="rowCss" text="lowInterestColor" />
	</jstl:if>
	<jstl:if test="${row.interest == 'MEDIUM'}" >
		<spring:message var="rowCss" text="mediumInterestColor" />
	</jstl:if>
	<jstl:if test="${row.interest == 'HIGH'}" >
		<spring:message var="rowCss" text="hightInterestColor" />
	</jstl:if>


	<acme:column property="reference" title="tender.reference" css="${rowCss}" sortable="true" />
	<acme:column property="title" title="tender.title" css="${rowCss}" sortable="true"/>
	<acme:column property="interest" title="tender.interest" css="${rowCss}" sortable="true"/>
	<acme:column property="maxPresentationDate" title="tender.maxPresentationDate" css="${rowCss}" format="display.date.time.format" sortable="true"/>
	
	<spring:message code="tender.comision" var="titleTenderComision"/>
	<display:column title="${titleTenderComision}" class="${rowCss}" >
		<fmt:formatNumber value="${row.estimatedAmount * (benefitsPercentaje/100)}" pattern="${formpriceformat}" var="varTenderComision" />
		<div>
			${varTenderComision} <spring:message code="currency.symbol" />
		</div>
	</display:column>	
	
	<spring:message code="tender.administrative" var="tenderAdministrative" />
	<display:column title="${tenderAdministrative}" class="${rowCss}" sortable="true">
		<div>
			<jstl:if test="${row.administrative.id == actor.id}" >
				<spring:message code="tender.mine" />
			</jstl:if>
			<jstl:if test="${row.administrative.id != actor.id}" >
				<a href="actor/display.do?actorId=${row.administrative.id}">
					${row.administrative.name} ${row.administrative.surname}
				</a>
			</jstl:if>
		</div>
	</display:column>
	
	<display:column class="${rowCss}">
		<div>
			<a href="tender/display.do?tenderId=${row.id}">
				<spring:message code="master.page.display" />
			</a>
		</div>
	</display:column>
	

	<display:column class="${rowCss}">
		<jstl:if test="${row.tenderResult != null}"  >
			<a href="tenderResult/display.do?tenderResultId=${row.tenderResult.id}">
				<spring:message code="tender.tenderResult.display" />
			</a>
		</jstl:if>
		<jstl:if test="${row.tenderResult == null && row.administrative.id == actor.id}"  >
			<a href="tenderResult/administrative/create.do?tenderId=${row.id}">
				<spring:message code="tender.tenderResult.create" />
			</a>
		</jstl:if>
	</display:column>
	
	<security:authorize access="hasRole('ADMIN')">
		<display:column class="${rowCss}">
			<jstl:if test="${row.offer == null}" >
				<a href="tender/administrator/delete.do?tenderId=${row.id}">
					<spring:message code="master.page.delete" />
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>	
	
</display:table>
<br/><br/>

<security:authorize access="hasRole('ADMINISTRATIVE')">
	<acme:button url="tender/administrative/create.do" text="tender.new" css="formButton toLeft" />
</security:authorize>
