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

<form:form action="folder/edit.do" modelAttribute="folder">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="children"/>
	<form:hidden path="messages"/>
	
	<b><form:label path="name"><spring:message code="folder.name"/>:&nbsp;</form:label></b>
	<form:input path="name"/>
	<form:errors path="name" cssClass="error"/>
	<br/>
	
	<b><form:label path="parent"><spring:message code="folder.parent"/>:&nbsp;</form:label></b>
	<form:select path="parent">
		<form:option label="----" value="0" />
		<form:options items="${folders}" itemLabel="name" itemValue="id"/>
	</form:select>
	<form:errors path="parent" cssClass="error"/>
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="folder.save"/>"/>
	&nbsp;
	<jstl:if test="${folder.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="folder.delete"/>"/>
		&nbsp;
	</jstl:if>
	<input type="button" name="cancel" value="<spring:message code="folder.cancel" />" onclick="javascript: relativeRedir('folder/list.do');" />

</form:form>