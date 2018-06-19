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

<display:table pagesize="5" class="displaytag" name="evaluationCriteriaTypes" requestURI="evaluationCriteriaType/administrative/list.do" id="row">

	<acme:column property="name" title="evaluationCriteriaType.name" sortable="true"/>
	<acme:column property="description" title="evaluationCriteriaType.description" sortable="true"/>

	<display:column>
		<div>
			<a href="evaluationCriteriaType/administrative/edit.do?evaluationCriteriaTypeId=${row.id}">
				<spring:message code="master.page.edit" />
			</a>
		</div>
	</display:column>

</display:table>
<br/>
<br/>
<acme:button url="/evaluationCriteriaType/administrative/create.do" text="evaluationCriteriaType.create" css="formButton toLeft" />

