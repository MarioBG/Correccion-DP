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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="bankaccount/bankAgent/edit.do"
	modelAttribute="bankAccount">

	<form:hidden path="id" />

	<acme:select items="${actors}" itemLabel="name" code="bankAccount.actor" path="actor"/>
	<br />
	<br />



	<input type="button" name="cancel"
		value="<spring:message code="bankAccount.cancel"/>"
		onclick="javascript: relativeRedir('/');" />

	<security:authorize access="hasRole('BANKAGENT')">
		<input type="submit" name="save"
			value="<spring:message code="bankAccount.save"/>" />&nbsp;
	</security:authorize>


</form:form>