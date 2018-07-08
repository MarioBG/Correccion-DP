<%--
 * register.jsp
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
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI }" modelAttribute="commentForm">

	<form:hidden path="rendezvousId"/>
	<form:hidden path="commentParentId"/>
	<form:hidden path="userId"/>
	
	<acme:textarea code="comment.text" path="text"/>
	<br/>
	
	<acme:textbox code="comment.picture" path="picture"/>
	<br/>
	
	<input type="button" name="cancel"
			value="<spring:message code="comment.cancel" />"
			onclick="javascript: relativeRedir('/');" />
	
	
	<security:authorize access="hasRole('USER')">
	<jstl:choose>
		<jstl:when test="${requestURI == 'comment/user/editReplies.do'}">
			<input type="submit" name="saveReply"
				value="<spring:message code="comment.save" />" />&nbsp; 
		</jstl:when>
		<jstl:when test="${requestURI == 'comment/user/edit.do'}">
			<input type="submit" name="save"
				value="<spring:message code="comment.save" />" />&nbsp; 
		</jstl:when>
	</jstl:choose>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${commentForm.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="comment.delete" />"
			onclick="return confirm('<spring:message code="comment.confirm.delete" />')" />&nbsp;
	</jstl:if>
	</security:authorize>
		
</form:form>