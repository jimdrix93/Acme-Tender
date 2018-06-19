<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="answer" method="post">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="writingDate" />
	<form:hidden path="comment" />
	<form:hidden path="actor" />

	<acme:textarea code="answer.text" path="text" />
	<br />

	<acme:submit name="save" code="answer.save" css="formButton toLeft" />&nbsp;
    <acme:cancel url="answer/actor/list.do?commentId=${answer.comment.id}" code="answer.cancel" css="formButton toLeft" />
	<br />

</form:form>