<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	
	<h2><b><spring:message code="story.title"/>:&nbsp;</b><jstl:out value="${story.title}" /></h2>
	<br/>

	<b><spring:message code="story.text"/>:&nbsp;</b><jstl:out value="${story.text}" />
	<br/>

	<b><spring:message code="story.writer"/>:&nbsp;</b><jstl:forEach var="x" items="${story.writer.userAccount.username}">
				<jstl:out value="${x}" /></jstl:forEach>
	
	<br/>
	
	<b><spring:message code="story.trip"/>:&nbsp;</b><jstl:out value="${story.trip.title}" />
	<br/>
	
	<b><spring:message code="story.attachments"/>:&nbsp;</b><jstl:forEach var="x" items="${story.attachments }">
				<jstl:out value="${x}" /></jstl:forEach>
	
	<br/>
	

 	<jstl:if test="${story.writer.userAccount.username == pageContext.request.userPrincipal.name}">
		<input type="button" name="cancel" value="<spring:message code="story.cancel" />" 
			onclick="javascript: relativeRedir('story/explorer/list.do');" />
	</jstl:if>

	<jstl:if test="${story.writer.userAccount.username != pageContext.request.userPrincipal.name}">
		<input type="button" name="cancel" value="<spring:message code="story.cancel" />" 
			onclick="javascript: relativeRedir('welcome/index.do');" />
	</jstl:if> 



