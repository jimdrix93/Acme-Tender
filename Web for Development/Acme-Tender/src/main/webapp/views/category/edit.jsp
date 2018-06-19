<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="category/administrator/edit.do" modelAttribute="category">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="fatherCategory" />
	
	
	<acme:textbox code="category.name" path="name" />
	<acme:textarea code="category.description" path="description"/>

	<br/><br/>
	
	<acme:submit name="save" code="category.save" css="formButton toLeft"/>&nbsp;
	
	<jstl:if test="${category.id != 0}">
		<acme:submit name="delete" code="category.delete" css="formButton toLeft"/>
	</jstl:if>&nbsp;
	
	<jstl:if test="${category.fatherCategory == null}" >
		<acme:cancel url="/category/administrator/list.do" code="category.cancel" css="formButton toLeft"/>
	</jstl:if>
	<jstl:if test="${category.fatherCategory != null}" >
		<acme:cancel url="/category/administrator/list.do?parentCategoryId=${category.fatherCategory.id}" code="category.cancel" css="formButton toLeft"/>
	</jstl:if>
	
</form:form>