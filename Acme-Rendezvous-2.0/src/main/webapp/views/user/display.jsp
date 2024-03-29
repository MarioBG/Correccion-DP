<%--
 * display.jsp
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h3>
	<b><spring:message code="user.name" />:&nbsp;</b>
	<jstl:out value="${user.name}" />
</h3>

<h3>
	<b><spring:message code="user.surname" />:&nbsp;</b>
	<jstl:out value="${user.surname}" />
</h3>

<b><spring:message code="user.email" />:&nbsp;</b>
<jstl:out value="${user.email}" />
<br />

<b><spring:message code="user.phone" />:&nbsp;</b>
<jstl:out value="${user.phone}" />
<br />

<b><spring:message code="user.address" />:&nbsp;</b>
<jstl:out value="${user.address}" />
<br />

<spring:message var="patternDate" code="user.pattern.date"/>
<b><spring:message code="user.birth" />:&nbsp;</b>
<fmt:formatDate value="${user.birth}" pattern="${patternDate}"/>
<br />

<acme:cancel code="user.cancel" url="welcome/index.do" />