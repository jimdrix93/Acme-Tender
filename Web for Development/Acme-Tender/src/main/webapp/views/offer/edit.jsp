<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="offer/commercial/edit.do" modelAttribute="offer" method="post">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="tender" />
	<form:hidden path="commercial" />

	<acme:selectcombo code="offer.state" items="${statesCombo}" path="state"/>
	<acme:textbox code="offer.amount" path="amount" />
	<br />

	<acme:submit name="save" code="offer.save" css="formButton toLeft" />&nbsp;
	
	<jstl:if test="${offer.id == 0}" >
    	<acme:cancel url="offer/list.do" code="offer.cancel" css="formButton toLeft" />
    </jstl:if>
	<jstl:if test="${offer.id != 0}" >
    	<acme:cancel url="offer/display.do?offerId=${offer.id}" code="offer.cancel" css="formButton toLeft" />
    </jstl:if>
    
	<br />

</form:form>