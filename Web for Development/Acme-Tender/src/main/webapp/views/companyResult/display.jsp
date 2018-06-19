<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	
	
<acme:labelvalue code="companyResult.name" value="${companyResult.name}"/> 
<acme:labelvalue code="companyResult.economicalOffer" value="${companyResult.economicalOffer}" isCurrency="true"/> 
<acme:labelvalue code="companyResult.score"  value="${companyResult.score}" />
<acme:labelvalue code="companyResult.position"  value="${companyResult.position}"/> 
<acme:labelvalue code="companyResult.comments"  value="${companyResult.comments}" /> 
<acme:labelvalue code="companyResult.state" value="${companyResult.state}" /> 

<br/>

<jstl:if test="${companyResult.tenderResult.tender.administrative.id == actor.id}" >
	<acme:button url="companyResult/administrative/edit.do?companyResultId=${companyResult.id}" text="companyResult.edit" css="formButton toLeft" />
</jstl:if>
<acme:button url="tenderResult/display.do?tenderResultId=${companyResult.tenderResult.id}" text="companyResult.back" css="formButton toLeft" />

&nbsp;
