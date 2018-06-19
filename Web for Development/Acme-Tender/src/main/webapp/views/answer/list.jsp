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


<fieldset>
	<legend><spring:message code="answer.comment" /></legend>

	<acme:labelvalue code="comment.text" value="${comment.text}"/> 
	<acme:labelvalue code="comment.writingDate" value="${comment.writingDate}" isDatetime="true"/>
</fieldset>

<fieldset>
	<legend><spring:message code="answer.answers" /></legend>
	<display:table pagesize="5" class="displaytag" name="answers" requestURI="answer/list.do" id="row">
	
		<acme:column property="text" title="answer.text" />
		<acme:column property="writingDate" title="answer.writingDate" format="display.date.format"/>
		
		<spring:message code="answer.user" var="answerUser" />
		<display:column title="${answerUser}">
			<div>
				<a href="actor/display.do?actorId=${row.actor.id}">
					<jstl:out value="${row.actor.name} ${row.actor.surname}" />
				</a> 
			</div>
		</display:column>	
	
	</display:table>
</fieldset>

<acme:button url="answer/actor/create.do?commentId=${comment.id}" text="comment.answer.create" css="formButton toLeft" />
<acme:cancel url="tender/display.do?tenderId=${tenderId}" code="comment.back" css="formButton toLeft" />
