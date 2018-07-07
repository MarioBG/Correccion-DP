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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="folder/edit.do" modelAttribute="folderForm">

	<form:hidden path="id"/>
	
	<acme:textbox code="folder.name" path="name"/>

	<acme:select items="${folders}" itemLabel="name" code="folder.parent" path="parentId"/>

	<acme:submit name="save" code="folder.save"/>	
	&nbsp;
	<jstl:if test="${folderForm.id != 0}">
		<acme:submit name="delete" code="folder.delete"/>	
		&nbsp;
	</jstl:if>
	<acme:cancel url="folder/list.do" code="folder.cancel"/>

</form:form>