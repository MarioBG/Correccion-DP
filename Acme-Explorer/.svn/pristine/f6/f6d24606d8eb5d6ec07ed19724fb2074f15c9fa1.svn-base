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

<form:form action="legalText/admin/edit.do" modelAttribute="legalText">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="moment"/>
	<form:hidden path="trips"/>
	
	<b><form:label path="title"><spring:message code="legalText.title"/>:&nbsp;</form:label></b>
	<form:input path="title" />
	<form:errors path="title" cssClass="error"/>
	<br/>
	
	<b><form:label path="body"><spring:message code="legalText.body"/>:&nbsp;</form:label></b>
	<form:textarea path="body"/>
	<form:errors path="body" cssClass="error"/>
	<br/>
	
	<b><form:label path="laws"><spring:message code="legalText.laws" />:&nbsp;</form:label></b>
	<form:textarea path="laws" />
	<div id="lawsExplanation">
		<spring:message code="legalText.eachLaw"/>
	</div>
	<form:errors path="laws" cssClass="error"/>
	<br/>
	
	<b><form:label path="draft"><spring:message code="legalText.draft" />:&nbsp;</form:label></b>
	<form:checkbox path="draft" />
	<form:errors path="draft" cssClass="error"/>
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="legalText.save"/>"/>
	&nbsp;
	<jstl:if test="${legalText.id != 0 && legalText.draft == true}">
		<input type="submit" name="delete" value="<spring:message code="legalText.delete"/>"/>
		&nbsp;
	</jstl:if>
	<input type="button" name="cancel" value="<spring:message code="legalText.cancel" />" onclick="javascript: relativeRedir('legalText/admin/list.do');" />

</form:form>