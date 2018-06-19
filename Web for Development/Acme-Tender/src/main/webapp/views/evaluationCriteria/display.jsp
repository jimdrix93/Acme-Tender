<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:labelvalue code="evaluationCriteria.title"  value="${evaluationCriteria.title}"/>  <br/>
<acme:labelvalue code="evaluationCriteria.description"  value="${evaluationCriteria.description}"/>  <br/>
<acme:labelvalue code="evaluationCriteria.maxScore"  value="${evaluationCriteria.maxScore}" /> <br/>
<acme:labelvalue code="evaluationCriteria.evaluationCriteriaType.name"  value="${evaluationCriteria.evaluationCriteriaType.name} "/> <br/>
<acme:labelvalue code="evaluationCriteria.evaluationCriteriaType.description"  value="${evaluationCriteria.evaluationCriteriaType.description} "/> <br/>

<br />

<jstl:if test="${evaluationCriteria.tender.administrative.id == actor.id}" >
	<acme:button url="evaluationCriteria/administrative/edit.do?evaluationCriteriaId=${evaluationCriteria.id}" text="evaluationCriteria.edit" css="formButton toLeft" />
</jstl:if>

<acme:button url="tender/display.do?tenderId=${evaluationCriteria.tender.id}" text="evaluationCriteria.back" css="formButton toLeft" />


