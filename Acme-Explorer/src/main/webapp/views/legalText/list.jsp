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

<display:table name="legalTexts" id="row" pagesize="5" class="displaytag" requestURI="legalText/admin/list.do">

	<display:column>
		<jstl:if test="${row.draft == true}">
			<a href="legalText/admin/edit.do?legalTextId=${row.id}"><spring:message code="legalText.edit"/></a>
		</jstl:if>
	</display:column>
	
	<display:column>
		<jstl:if test="${row.draft == false}">
			<a href="legalText/display.do?legalTextId=${row.id}"><spring:message code="legalText.display"/></a>
		</jstl:if>
	</display:column>
	
	<spring:message var="titleHeader" code="legalText.title"/>
	<display:column property="title" title="${titleHeader}"/>
	
	<spring:message var="lawsHeader" code="legalText.laws"/>
	<display:column property="laws" title="${lawsHeader}"/>
	
	<spring:message var="momentHeader" code="legalText.moment"/>
	<display:column property="moment" title="${momentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>
	
</display:table>

<a href="legalText/admin/create.do"><spring:message code="legalText.create"/></a>