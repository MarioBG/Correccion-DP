<%--
 * edit.jsp
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

<form:form action="${actionURI}" modelAttribute="messageEdit">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="sender" />
	<%-- <form:hidden path="recipient"/> --%>
	<form:hidden path="folder" />
	<%-- <form:hidden path="priority"/> --%>

	<b><form:label path="recipient">
		<spring:message code="message.recipient" />:&nbsp;</form:label></b>

		<form:select path="recipient">
			<form:option label="----" value="0" />
			<form:options items="${actors}" itemLabel="name" itemValue="id" />
		</form:select>
	<form:errors path="recipient" cssClass="error" />
	<br />

	<b><form:label path="subject">
			<spring:message code="message.subject" />:&nbsp;</form:label></b>
	<jstl:choose>
		<jstl:when test="${messageEdit.id == 0}">
			<form:input path="subject" />
		</jstl:when>
		<jstl:otherwise>
			<input value="${messageEdit.subject}" readonly="readonly" />
		</jstl:otherwise>
	</jstl:choose>
	<form:errors path="subject" cssClass="error" />
	<br />

	<b><form:label path="body">
			<spring:message code="message.body" />:&nbsp;</form:label></b>
	<jstl:choose>
		<jstl:when test="${messageEdit.id == 0}">
			<form:textarea path="body" />
		</jstl:when>
		<jstl:otherwise>
			<textarea readonly="readonly">
				<jstl:out value="${messageEdit.body}" />
			</textarea>
		</jstl:otherwise>
	</jstl:choose>
	<form:errors path="body" cssClass="error" />
	<br />

	<b><form:label path="priority">
			<spring:message code="message.priority" />:&nbsp;</form:label></b>
	<jstl:choose>
		<jstl:when test="${messageEdit.id == 0}">
			<form:select path="priority">
				<form:options items="${priorities}" />
			</form:select>
		</jstl:when>
		<jstl:otherwise>
			<input value="${messageEdit.priority}" readonly="readonly" />
		</jstl:otherwise>
	</jstl:choose>
	<form:errors path="priority" cssClass="error" />
	<br />

	<jstl:choose>
		<jstl:when test="${messageEdit.id == 0}">
			<input type="submit" name="save"
				value="<spring:message code="message.send"/>" />
			&nbsp;
		</jstl:when>
		<jstl:when test="${messageEdit.id != 0}">
			<input type="submit" name="save"
				value="<spring:message code="message.save"/>" />
			&nbsp;
		</jstl:when>
	</jstl:choose>
	<jstl:if test="${messageEdit.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="message.delete"/>" />
		&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="message.cancel" />"
		onclick="javascript: relativeRedir('folder/list.do');" />

</form:form>