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

<display:table name="contacts" id="row" pagesize="5" class="displaytag" requestURI="${requestURI }">
	
	<display:column>
		<a href="contact/explorer/edit.do?contactId=${row.id}"><spring:message code="contact.edit"/></a>
	</display:column>
		
	<spring:message var="nameHeader" code="contact.name"/>
	<display:column property="name" title="${nameHeader}"/>
	
	<spring:message var="emailHeader" code="contact.email"/>
	<display:column property="email" title="${emailHeader}"/>
	
	<spring:message var="phoneHeader" code="contact.phone"/>
	<display:column property="phone" title="${phoneHeader}"/>
	
</display:table>

<security:authorize access="hasRole('EXPLORER')">
	<a href="contact/explorer/create.do"><spring:message code="contact.create"/></a>
</security:authorize>
      