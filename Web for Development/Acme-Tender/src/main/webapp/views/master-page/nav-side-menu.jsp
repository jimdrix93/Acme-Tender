<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
  
 
<div class="brand">
	<img src="${banner}" style="max-width: 300px;" />
</div>


<div class="menu-list">
 
	<ul id="menu-content" class="menu-content collapse out">

               
		<security:authorize access="hasRole('ADMINISTRATIVE')">
			<spring:message code="file.delete.confirmation" var="deleteAlert"/>	
			<!-- Concursos -->
			<li data-toggle="collapse" data-target="#tenders" class="collapsed">
				<a><i class="fa fa-balance-scale fa-lg"></i><span class="texto-menu"><spring:message code="master.page.tenders" /></span><span class="arrow"></span></a>
			</li>
			<ul class="sub-menu collapse" id="tenders">
				<li><button onclick="javascript: relativeRedir('tender/list.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.tenders.list" />
				</button></li>
				<li><button onclick="javascript: relativeRedir('tender/listOffertable.do');" class="menuButton texto-submenu">
					<spring:message code="master.page.tenders.list.offertable" />
				</button></li>
				<li><button onclick="javascript: relativeRedir('tender/search.do');" class="menuButton texto-submenu">
					<spring:message code="master.page.tenders.search" />
				</button></li>
				<li><button onclick="javascript: relativeRedir('tender/administrative/list.do');" class="menuButton texto-submenu">
					<spring:message code="master.page.my.tenders" />
				</button>
				<li><button onclick="javascript: relativeRedir('tender/administrative/create.do');" class="menuButton texto-submenu">
					<spring:message code="master.page.tenders.create" />
				</button></li>	
				
			</ul>
							
			<!-- Ofertas -->
			<li data-toggle="collapse" data-target="#offers" class="collapsed">				
				<a><i class="fa fa-money fa-lg"></i><span class="texto-menu"><spring:message code="master.page.offers" /></span><span class="arrow"></span></a>
			</li>
			<ul class="sub-menu collapse" id="offers">
				<li><button onclick="javascript: relativeRedir('offer/list.do');" class="menuButton texto-submenu">
					<spring:message code="master.page.offers.list" />
				</button></li>
				<li><button onclick="javascript: relativeRedir('offer/search.do');" class="menuButton texto-submenu">
					<spring:message code="master.page.offers.search" />
				</button></li>
				<li><button onclick="javascript: relativeRedir('offer/administrative/listOffersByCollaboration.do');" class="menuButton texto-submenu">
					<spring:message code="master.page.my.offers.collaboration" />
				</button></li>		
			</ul>
			
			<!-- Solicitudes administrativas -->
			<li data-toggle="collapse" data-target="#administrativeRequests" class="collapsed">
				<a><i class="fa fa-copy fa-lg"></i><span class="texto-menu"><spring:message code="master.page.administrative.requests" /></span><span class="arrow"></span></a>
			</li>
			<ul class="sub-menu collapse" id="administrativeRequests">				
				<li><button onclick="javascript: relativeRedir('administrativeRequest/listReceived.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.administrative.requests.received" />
				</button></li>	
			</ul>				
			
			<!-- Tipos de criterios de valoración -->
			<li data-toggle="collapse" data-target="#evaluationCriteriaTypes" class="collapsed">
				<a href="evaluationCriteriaType/administrative/list.do"><i class="fa fa-list fa-lg"></i><span class="texto-menu"><spring:message code="master.page.evaluation.criteria.types" /></span></a>
			</li>
			
			<!-- Mensajeria -->
			<li data-toggle="collapse" data-target="#messages" class="collapsed">
				<a><i class="fa fa-envelope fa-lg"></i><span class="texto-menu"><spring:message code="master.page.messages" /></span><span class="arrow"></span></a>
				</li>
			<ul class="sub-menu collapse" id="messages">								 
				<li><button onclick="javascript: relativeRedir('myMessage/create.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.newmessage" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('folder/list.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.myfolders" />
				</button></li>	
			</ul>
			
			<!-- Users y profile -->
			<li data-toggle="collapse" data-target="#users" class="collapsed">
				<a><i class="fa fa-users fa-lg"></i><span class="texto-menu"><spring:message code="master.page.users" /></span><span class="arrow"></span></a>
			</li>
			<ul class="sub-menu collapse" id="users">				
				<li><button onclick="javascript: relativeRedir('actor/list.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.actor.list" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('actor/edit.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.profile" />
				</button></li>	
			</ul>
		</security:authorize>            
		
		
		<security:authorize access="hasRole('COMMERCIAL')">
				
			<!-- Concursos -->
			<li data-toggle="collapse" data-target="#tenders" class="collapsed">
				<a><i class="fa fa-balance-scale fa-lg"></i><span class="texto-menu"><spring:message code="master.page.tenders" /></span><span class="arrow"></span></a>
			</li>
			<ul class="sub-menu collapse" id="tenders">
				<li><button onclick="javascript: relativeRedir('tender/list.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.tenders.list" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('tender/listOffertable.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.tenders.list.offertable" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('tender/search.do');" class="texto-submenu menuButton"> 
					<spring:message code="master.page.tenders.search" />
				</button></li>	
			</ul>
							
			<!-- Ofertas -->
			<li data-toggle="collapse" data-target="#offers" class="collapsed">				
				<a><i class="fa fa-money fa-lg"></i><span class="texto-menu"><spring:message code="master.page.offers" /></span><span class="arrow"></span></a>
			</li>
			<ul class="sub-menu collapse" id="offers">
				<li><button onclick="javascript: relativeRedir('offer/list.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.offers.list" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('offer/search.do');" class="texto-submenu menuButton"> 
					<spring:message code="master.page.offers.search" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('offer/commercial/listOffersByPropietary.do');" class="texto-submenu menuButton"> 
					<spring:message code="master.page.my.offers" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('offer/commercial/listOffersByCollaboration.do');" class="texto-submenu menuButton"> 
					<spring:message code="master.page.my.offers.collaboration" />
				</button></li>	
			</ul>

			<!-- Solicitudes de colaboración -->
			<li data-toggle="collapse" data-target="#collaborationRequests" class="collapsed">
				<a><i class="fa fa-credit-card fa-lg"></i><span class="texto-menu"><spring:message code="master.page.collaboration.requests" /></span><span class="arrow"></span></a>
			</li>
			<ul class="sub-menu collapse" id="collaborationRequests">				
				<li><button onclick="javascript: relativeRedir('collaborationRequest/commercial/listReceived.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.collaboration.requests.received" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('collaborationRequest/commercial/listSent.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.collaboration.requests.sended" />
				</button></li>	
				
			</ul>				

			<!-- Solicitudes administrativas -->
			<li data-toggle="collapse" data-target="#administrativeRequests" class="collapsed">
				<a><i class="fa fa-copy fa-lg"></i><span class="texto-menu"><spring:message code="master.page.administrative.requests" /></span><span class="arrow"></span></a>
			</li>
			<ul class="sub-menu collapse" id="administrativeRequests">				
				<li><button onclick="javascript: relativeRedir('administrativeRequest/listSent.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.administrative.requests.sended" />
				</button></li>	
			</ul>				
			
			<!-- Mensajeria -->
			<li data-toggle="collapse" data-target="#messages" class="collapsed">
				<a><i class="fa fa-envelope fa-lg"></i><span class="texto-menu"><spring:message code="master.page.messages" /></span><span class="arrow"></span></a>
				</li>
			<ul class="sub-menu collapse" id="messages">								 
				<li><button onclick="javascript: relativeRedir('myMessage/create.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.newmessage" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('folder/list.do');" class="texto-submenu menuButton"> 
					<spring:message code="master.page.myfolders" />
				</button></li>	
			</ul>
			
			<!-- Users y profile -->
			<li data-toggle="collapse" data-target="#users" class="collapsed">
				<a><i class="fa fa-users fa-lg"></i><span class="texto-menu"><spring:message code="master.page.users" /></span><span class="arrow"></span></a>
			</li>
			<ul class="sub-menu collapse" id="users">				
				<li><button onclick="javascript: relativeRedir('actor/list.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.actor.list" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('actor/edit.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.profile" />
				</button></li>	
			</ul>
		</security:authorize>    
		
		
		<security:authorize access="hasRole('ADMIN')">
				
			<!-- Concursos -->
			<li data-toggle="collapse" data-target="#tenders" class="collapsed">
				<a><i class="fa fa-balance-scale fa-lg"></i><span class="texto-menu"><spring:message code="master.page.tenders" /></span><span class="arrow"></span></a>
			</li>
			<ul class="sub-menu collapse" id="tenders">
				<li><button onclick="javascript: relativeRedir('tender/list.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.tenders.list" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('tender/listOffertable.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.tenders.list.offertable" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('tender/search.do');" class="texto-submenu menuButton"> 
					<spring:message code="master.page.tenders.search" />
				</button></li>	
			</ul>
							
			<!-- Ofertas -->
			<li data-toggle="collapse" data-target="#offers" class="collapsed">				
				<a><i class="fa fa-money fa-lg"></i><span class="texto-menu"><spring:message code="master.page.offers" /></span><span class="arrow"></span></a>
			</li>
			<ul class="sub-menu collapse" id="offers">
				<li><button onclick="javascript: relativeRedir('offer/list.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.offers.list" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('offer/administrator/listNotPublished.do');" class="texto-submenu menuButton"> 
					<spring:message code="master.page.offers.list.notpublished" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('offer/search.do');" class="texto-submenu menuButton"> 
					<spring:message code="master.page.offers.search" />
				</button></li>	
			</ul>
			
			<!-- Categorias de concurso -->
			<li data-toggle="collapse" data-target="#categories" class="collapsed">
				<a href="category/administrator/list.do"><i class="fa fa-list fa-lg"></i><span class="texto-menu"><spring:message code="master.page.tender.categories" /></span></a>
			</li>
			
			
			<!-- Palabras tabu -->
			<li data-toggle="collapse" data-target="#taboo" class="collapsed">
				<a><i class="fa fa-ban fa-lg"></i><span class="texto-menu"><spring:message code="master.page.tabooWords" /></span><span class="arrow"></span></a>
			</li>
			<ul class="sub-menu collapse" id="taboo">
				<li><button onclick="javascript: relativeRedir('tabooWord/administrator/list.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.tabooWords.list" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('tender/administrator/listWithTabooWord.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.tabooWords.tenders" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('offer/administrator/listWithTabooWord.do');" class="texto-submenu menuButton"> 
					<spring:message code="master.page.tabooWords.offers" />
				</button></li>	
			</ul>	
			
			<!-- Config -->
			<li data-toggle="collapse" data-target="#config" class="collapsed">
				<a href="configuration/administrator/edit.do"><i class="fa fa-dashboard fa-lg"></i><span class="texto-menu"></span><spring:message code="master.page.configuration" /></a>
			</li>					
			
			<!-- Mensajeria -->
			<li data-toggle="collapse" data-target="#messages" class="collapsed">
				<a><i class="fa fa-envelope fa-lg"></i><span class="texto-menu"><spring:message code="master.page.messages" /></span><span class="arrow"></span></a>
				</li>
			<ul class="sub-menu collapse" id="messages">								 
				<li><button onclick="javascript: relativeRedir('myMessage/create.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.newmessage" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('myMessage/administrator/create.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.broadcast" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('folder/list.do');" class="texto-submenu menuButton"> 
					<spring:message code="master.page.myfolders" />
				</button></li>	
			</ul>
			
			<!-- Users y profile -->
			<li data-toggle="collapse" data-target="#users" class="collapsed">
				<a><i class="fa fa-users fa-lg"></i><span class="texto-menu"><spring:message code="master.page.users" /></span><span class="arrow"></span></a>
			</li>
			<ul class="sub-menu collapse" id="users">				
				<li><button onclick="javascript: relativeRedir('actor/administrator/list.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.actor.list" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('actor/edit.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.profile" />
				</button></li>	
			</ul>
		</security:authorize>    
		
		
		
		<security:authorize access="hasRole('EXECUTIVE')">
				
			<!-- Concursos -->
			<li data-toggle="collapse" data-target="#tenders" class="collapsed">
				<a><i class="fa fa-balance-scale fa-lg"></i><span class="texto-menu"><spring:message code="master.page.tenders" /></span><span class="arrow"></span></a>
			</li>
			<ul class="sub-menu collapse" id="tenders">
				<li><button onclick="javascript: relativeRedir('tender/list.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.tenders.list" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('tender/listOffertable.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.tenders.list.offertable" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('tender/search.do');" class="texto-submenu menuButton"> 
					<spring:message code="master.page.tenders.search" />
				</button></li>	
			</ul>
							
			<!-- Ofertas -->
			<li data-toggle="collapse" data-target="#offers" class="collapsed">				
				<a><i class="fa fa-money fa-lg"></i><span class="texto-menu"><spring:message code="master.page.offers" /></span><span class="arrow"></span></a>
			</li>
			<ul class="sub-menu collapse" id="offers">
				<li><button onclick="javascript: relativeRedir('offer/list.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.offers.list" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('offer/executive/listNotPublished.do');" class="texto-submenu menuButton"> 
					<spring:message code="master.page.offers.list.notpublished" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('offer/search.do');" class="texto-submenu menuButton"> 
					<spring:message code="master.page.offers.search" />
				</button></li>	
			</ul>
			
			<!-- Mensajeria -->
			<li data-toggle="collapse" data-target="#messages" class="collapsed">
				<a><i class="fa fa-envelope fa-lg"></i><span class="texto-menu"><spring:message code="master.page.messages" /></span><span class="arrow"></span></a>
				</li>
			<ul class="sub-menu collapse" id="messages">								 
				<li><button onclick="javascript: relativeRedir('myMessage/create.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.newmessage" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('folder/list.do');" class="texto-submenu menuButton"> 
					<spring:message code="master.page.myfolders" />
				</button></li>	
			</ul>
			
			<!-- Users y profile -->
			<li data-toggle="collapse" data-target="#users" class="collapsed">
				<a><i class="fa fa-users fa-lg"></i><span class="texto-menu"><spring:message code="master.page.users" /></span><span class="arrow"></span></a>
			</li>
			<ul class="sub-menu collapse" id="users">				
				<li><button onclick="javascript: relativeRedir('actor/list.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.actor.list" />
				</button></li>	
				<li><button onclick="javascript: relativeRedir('actor/edit.do');" class="texto-submenu menuButton">
					<spring:message code="master.page.profile" />
				</button></li>	
			</ul>
			
			<!-- Dashboard -->
			<li data-toggle="collapse" data-target="#dashboard" class="collapsed">
				<a href="dashboard/executive/display.do"><i class="fa fa-dashboard fa-lg"></i><span class="texto-menu"><spring:message code="master.page.dashboard" /></span></a>
			</li>			
		</security:authorize>    		
           
          
	</ul>
</div>