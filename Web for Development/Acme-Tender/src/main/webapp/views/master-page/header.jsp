<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>

<!-- Menu and banner usually + "$") -->
<div class="topnav" id="myTopnav">
	
	<security:authorize access="isAnonymous()">
		<!-- Login y register -->
		<a href="security/login.do" style="float: left">
			<spring:message code="master.page.login" />
		</a>		
		<a href="actor/create.do" style="float: left"> 
			<spring:message code="master.page.register" />
		</a> 
	</security:authorize>
	
	<security:authorize access="hasRole('ADMINISTRATIVE')">
		<div class="dropdown" style="float: left">
			<button class="dropbtn">
				<spring:message code="master.page.menu" />
			</button>
			<div class="dropdown-content">
			
				<!-- Concursos -->
				<b><a><spring:message code="master.page.tenders" /></a></b>
				<a href="tender/list.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.tenders.list" /></i>
				</a>
				<a href="tender/search.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.tenders.search" /></i>
				</a>
				<a href="tender/administrative/list.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.my.tenders" /></i>
				</a>
				<a href="tender/administrative/create.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.tenders.create" /></i>
				</a>		
				<hr />
				
				<!-- Ofertas -->
				<b><a><spring:message code="master.page.offers" /></a> </b>
				<a href="offer/list.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.offers.list" /></i>
				</a>
				<a href="offer/search.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.offers.search" /></i>
				</a>
				<a href="offer/administrative/listOffersByCollaboration.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.my.offers.collaboration" /></i>
				</a>				
				<hr />
				
				<!-- Solicitudes administrativas -->
				<b><a><spring:message code="master.page.administrative.requests" /></a> </b>
				<a href="administrativeRequest/listReceived.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.administrative.requests.received" /></i>
				</a>	
				<hr />				
				
				<!-- Tipos de criterios de valoración -->
				<b><a href="evaluationCriteriaType/administrative/list.do"><spring:message code="master.page.evaluation.criteria.types" /></a></b> 
				<hr />				
				
				<!-- Mensajeria -->
				<b><a><spring:message code="master.page.messages" /></a> </b>
				<a href="myMessage/create.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.newmessage" /></i>
				</a>
				<a href="folder/list.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.myfolders" /></i>
				</a>
				<hr />

				<!-- Users y profile -->
				<b><a><spring:message code="master.page.users" /></a> </b>
				<a href="actor/list.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.actor.list" /></i>
				</a>
				<a href="actor/edit.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.profile" /></i>
				</a>					
			</div>
		</div>
	</security:authorize>
	

	<security:authorize access="hasRole('COMMERCIAL')">
		<div class="dropdown" style="float: left">
			<button class="dropbtn">
				<spring:message code="master.page.menu" />
			</button>
			<div class="dropdown-content">
			
				<!-- Concursos -->
				<b><a><spring:message code="master.page.tenders" /></a> </b>
				<a href="tender/list.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.tenders.list" /></i>
				</a>
				<a href="tender/search.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.tenders.search" /></i>
				</a>	
				<hr />
				
				<!-- Ofertas -->
				<b><a><spring:message code="master.page.offers" /></a> </b>
				<a href="offer/list.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.offers.list" /></i>
				</a>
				<a href="offer/search.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.offers.search" /></i>
				</a>
				<a href="offer/commercial/listOffersByPropietary.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.my.offers" /></i>
				</a>				
				<a href="offer/commercial/listOffersByCollaboration.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.my.offers.collaboration" /></i>
				</a>				
				<hr />
				
				<!-- Solicitudes de colaboración -->
				<b><a><spring:message code="master.page.collaboration.requests" /></a> </b>
				<a href="collaborationRequest/commercial/listReceived.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.collaboration.requests.received" /></i>
				</a>
				<a href="collaborationRequest/commercial/listSent.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.collaboration.requests.sended" /></i>
				</a>	
				<hr />					
				
				<!-- Solicitudes administrativas -->
				<b><a><spring:message code="master.page.administrative.requests" /></a> </b>
				<a href="administrativeRequest/listSent.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.administrative.requests.sended" /></i>
				</a>	
				<hr />					
				
				<!-- Mensajeria -->
				<b><a><spring:message code="master.page.messages" /></a> </b>
				<a href="myMessage/create.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.newmessage" /></i>
				</a>
				<a href="folder/list.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.myfolders" /></i>
				</a>
				<hr />
				
				<!-- Users y profile -->
				<b><a><spring:message code="master.page.users" /></a> </b>
				<a href="actor/list.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.actor.list" /></i>
				</a>
				<a href="actor/edit.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.profile" /></i>
				</a>							
			</div>
		</div>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
		<div class="dropdown" style="float: left">
			<button class="dropbtn">
				<spring:message code="master.page.menu" />
			</button>
			<div class="dropdown-content">
			
				<!-- Concursos -->
				<b><a><spring:message code="master.page.tenders" /></a> </b>
				<a href="tender/list.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.tenders.list" /></i>
				</a>
				<a href="tender/search.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.tenders.search" /></i>
				</a>	
				<hr />
				
				<!-- Ofertas -->
				<b><a><spring:message code="master.page.offers" /></a></b> 
				<a href="offer/list.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.offers.list" /></i>
				</a>
				<a href="offer/administrator/listNotPublished.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.offers.list.notpublished" /></i>
				</a>					
				<a href="offer/search.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.offers.search" /></i>
				</a>			
				<hr />
				
				<!-- Categorías de concurso -->
				<b><a href="category/administrator/list.do"><spring:message code="master.page.tender.categories" /></a></b> 		
				<hr />
				
				<!-- Palabras tabu -->
				<b><a><spring:message code="master.page.tabooWords" /></a></b> 
				<a href="tabooWord/administrator/list.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.tabooWords.list" /></i>
				</a>
				<a href="tender/administrator/listWithTabooWord.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.tabooWords.tenders" /></i>
				</a>		
				<a href="offer/administrator/listWithTabooWord.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.tabooWords.offers" /></i>
				</a>						
				<hr />	
				
				<!-- Configuración -->
				<b><a href="configuration/administrator/edit.do"><spring:message code="master.page.configuration" /></a></b> 		
				<hr />							
				
				<!-- Mensajeria -->
				<b><a><spring:message code="master.page.messages" /></a> </b>
				<a href="myMessage/create.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.newmessage" /></i>
				</a>
				<a href="myMessage/administrator/create.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.broadcast" /></i>
				</a>				
				<a href="folder/list.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.myfolders" /></i>
				</a>
				<hr />
				
				<!-- Users y profile -->
				<b><a><spring:message code="master.page.users" /></a> </b>
				<a href="actor/administrator/list.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.actor.list" /></i>
				</a>
				<a href="actor/edit.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.profile" /></i>
				</a>							
			</div>
		</div>
	</security:authorize>	
	

	<security:authorize access="hasRole('EXECUTIVE')">
		<div class="dropdown" style="float: left">
			<button class="dropbtn">
				<spring:message code="master.page.menu" />
			</button>
			<div class="dropdown-content">
			
				<!-- Concursos -->
				<b><a><spring:message code="master.page.tenders" /></a></b> 
				<a href="tender/list.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.tenders.list" /></i>
				</a>
				<a href="tender/search.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.tenders.search" /></i>
				</a>	
				<hr />
				
				<!-- Ofertas -->
				<b><a><spring:message code="master.page.offers" /></a></b> 
				<a href="offer/list.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.offers.list" /></i>
				</a>
				<a href="offer/executive/listNotPublished.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.offers.list.notpublished" /></i>
				</a>				
				<a href="offer/search.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.offers.search" /></i>
				</a>			
				<hr />
				
				<!-- Mensajeria -->
				<b><a><spring:message code="master.page.messages" /></a></b> 
				<a href="myMessage/create.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.newmessage" /></i>
				</a>	
				<a href="folder/list.do"> 
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.myfolders" /></i>
				</a>
				<hr />
				
				<!-- Users y profile -->
				<b><a><spring:message code="master.page.users" /></a></b> 
				<a href="actor/list.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.actor.list" /></i>
				</a>
				<a href="actor/edit.do">
					&nbsp;&nbsp;&nbsp;<i><spring:message code="master.page.profile" /></i>
				</a>							
				<hr />
				
				<!-- Dashboard -->
				<b><a href="dashboard/executive/display.do"><spring:message code="master.page.dashboard" /></a></b> 
			</div>
		</div>
	</security:authorize>	
	

	
	<!-- Lenguaje -->
	<!-- 	<a href="${requestScope['javax.servlet.forward.request_uri']} <my:replaceParam name='language' value='en' />">en</a> -->
	<!-- 	<a href="${requestScope['javax.servlet.forward.request_uri']} <my:replaceParam name='language' value='es' />">es</a> -->
	
	<div class="dropdown">
		<button class="dropbtn">
			i18n &nbsp;&nbsp;
		</button>
		<div class="dropdown-content">
		
			<a href="?language=es"><img src="images/es.png" /></a>
			<a href="?language=en"><img src="images/en.png" /></a> 	
		</div>
	</div>
	
	<a href="#about">
		<spring:message code="master.page.about" />
	</a>	
	
	<!-- Nombre de usuario -->
	<security:authorize access="isAuthenticated()">
		<div class="topnav" id="myTopnav">
			<div class="dropdown">
				<button class="dropbtn">
					<security:authentication property="principal.username" />
				</button>
				<div class="dropdown-content">
					<b>
						<a href="j_spring_security_logout"> 
							<spring:message code="master.page.logout" />
						</a>
					</b>
				</div>
			</div>		
		</div>
	</security:authorize>
</div>