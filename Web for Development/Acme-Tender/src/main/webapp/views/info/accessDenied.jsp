
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@page import="java.util.*" %>


pues eso churri, que no puedes pasar, vete a tomar algo <br/><br/>

<%
Random rand = new Random();
int n = rand.nextInt(7);
%>
<% if (n==0) { %>
	<img height="300px" src="http://i.imgur.com/cg7Pbe8.gif" />
<% } %>
<% if (n==1) { %>
	<img height="300px" src="https://media1.tenor.com/images/06018a9dee54c6c6c3a187b114b7cfc9/tenor.gif?itemid=5714968" />
<% } %>
<% if (n==2) { %>
	<img height="300px" src="https://media1.tenor.com/images/a5fb111b44a3f592fe5a2ca90e714782/tenor.gif?itemid=5646504" />
<% } %>
<% if (n==3) { %>
	<img height="300px" src="https://media.giphy.com/media/3HAYjf9agsx7U3aXKXm/giphy.gif" />
<% } %>
<% if (n==4) { %>
	<img height="300px" src="https://media.giphy.com/media/l0OXWXUHdp4K9nitq/giphy.gif" />
<% } %>
<% if (n==5) { %>
	<img height="300px" src="https://media.giphy.com/media/nn2kmb1lRtpkY/giphy.gif" />
<% } %>
<% if (n==6) { %>
	<img height="300px" src="https://media.giphy.com/media/rzItSj1mBCJBS/giphy.gif" />
<% } %>



<br/><br/>

<acme:button text="offer.back" url="/"  css="formButton toLeft" />


