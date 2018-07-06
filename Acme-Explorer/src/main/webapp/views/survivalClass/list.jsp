<%--
 * list.jsp
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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="survivalClasses" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">

	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<jstl:if test="${row.trip.startDateTrip gt date}">
				<a href="survivalClass/manager/edit.do?survivalClassId=${row.id}"><spring:message code="survivalClass.edit"/></a>		
			</jstl:if>
		</display:column>
	</security:authorize>
	
	
	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
			<a href="survivalClass/explorer/display.do?survivalClassId=${row.id}"><spring:message code="survivalClass.display"/></a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<a href="survivalClass/manager/display.do?survivalClassId=${row.id}"><spring:message code="survivalClass.display"/></a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="(!hasRole('MANAGER'))&&(!hasRole('EXPLORER'))">
		<display:column>
			<a href="survivalClass/display.do?survivalClassId=${row.id}"><spring:message code="survivalClass.display"/></a>
		</display:column>
	</security:authorize>
	
	
	<spring:message var="titleHeader" code="survivalClass.title"/>
	<display:column property="title" title="${titleHeader}"/>
	
	<spring:message var="tripHeader" code="survivalClass.trip"/>
	<display:column title="${tripHeader}" sortable="true">
		<jstl:out value="${row.trip.ticker}"/>
	</display:column>
	
	<spring:message var="momentHeader" code="survivalClass.moment"/>
	<spring:message var="formatDate" code="event.format.date"/>
	<display:column property="moment" title="${momentHeader}" sortable="true" format="${formatDate}"/>
	
</display:table>

	<security:authorize access="hasRole('MANAGER')">
		<a href="survivalClass/manager/create.do"><spring:message code="survivalClass.create"/></a>
	</security:authorize>


