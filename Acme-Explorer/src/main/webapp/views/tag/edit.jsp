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

<form:form action="tag/admin/edit.do" modelAttribute="tag">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="hasValues"/>
	
	<b><form:label path="name"/><spring:message code="tag.name"/>:&nbsp;</b>
	<form:input path="name"/>
	<form:errors path="name" cssClass="error"/>
	<br/>
		
	<input type="submit" name="save" value="<spring:message code="tag.save"/>"/>
	&nbsp;
	<jstl:if test="${tag.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="tag.delete"/>"/>
		&nbsp;
	</jstl:if>
	<input type="button" name="cancel" value="<spring:message code="tag.cancel" />" onclick="javascript: relativeRedir('hasValue/list.do?tripId=${hasValue.trip.id}');" />

</form:form>