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

<!-- Listing grid -->


<fieldset>
	<legend>
		<spring:message code="category.parentCategory" />
	</legend>

	<jstl:if test="${parent != null}">
		<acme:labelvalue code="category.name" value="${parent.name}" />
		<acme:labelvalue code="category.description" value="${parent.description}" />
	</jstl:if>
	<jstl:if test="${parent == null}">
		<spring:message code="category.without.father" />
		<br />
	</jstl:if>
</fieldset>

<fieldset>
	<legend>
		<spring:message code="category.childCategories.text" />
	</legend>
	<display:table pagesize="5" class="displaytag" keepStatus="true" name="categories" requestURI="${requestUri}" id="row">

		<acme:column property="name" title="category.name" sortable="true"/>
		<acme:column property="description" title="category.description" sortable="true"/>

		<display:column>
			<a href="category/administrator/list.do?parentCategoryId=${row.id}"> <spring:message code="category.childCategories" />
			</a>
		</display:column>

		<display:column>
			<a href="category/administrator/edit.do?categoryId=${row.id}"> <spring:message code="master.page.edit" />
			</a>
		</display:column>

	</display:table>
</fieldset>

<jstl:if test="${parent != null}">
	<acme:button url="category/administrator/create.do?parentCategoryId=${parent.id}" text="category.create" css="formButton toLeft" />
</jstl:if>
<jstl:if test="${parent == null}">
	<acme:button url="category/administrator/create.do" text="category.create" css="formButton toLeft" />
</jstl:if>


<jstl:if test="${parent != null && parent.fatherCategory == null}">
	<acme:button url="category/administrator/list.do" text="category.move.to.up.level" css="formButton toLeft" />
</jstl:if>
<jstl:if test="${parent != null && parent.fatherCategory != null}">
	<acme:button url="category/administrator/list.do?parentCategoryId=${parent.fatherCategory.id}" text="category.move.to.up.level" css="formButton toLeft" />
</jstl:if>

