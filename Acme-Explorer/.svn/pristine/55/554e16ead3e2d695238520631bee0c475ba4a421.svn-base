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

<form:form action="${requestURI }" modelAttribute="survivalClass">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="explorers"/>
	
	<b><form:label path="title"><spring:message code="survivalClass.title"/>:&nbsp;</form:label></b>
	<form:input path="title"/>
	<form:errors path="title" cssClass="error"/>
	<br/>
	
	<b><form:label path="description"><spring:message code="survivalClass.description"/>:&nbsp;</form:label></b>
	<form:textarea path="description"/>
	<form:errors path="description" cssClass="error"/>
	<br/>
	
	<b><form:label path="trip"><spring:message code="survivalClass.trip"/>:&nbsp;</form:label></b>
	<form:select path="trip">
		<form:option label="----" value="0"/>
		<form:options items="${trips}" itemLabel="ticker" itemValue="id"/>
	</form:select>
	<form:errors path="trip" cssClass="error"/>
	<br/>
	
	<b><form:label path="moment"><spring:message code="survivalClass.moment"/>:&nbsp;</form:label></b>
	<form:input path="moment" placeholder="dd/MM/yyyy HH:mm"/>
	<form:errors path="moment" cssClass="error"/>
	<br/>
	
	<fieldset>
		<legend><spring:message code="survivalClass.GPSCoordinates"/></legend>
		
		<b><form:label path="location.name"><spring:message code="survivalClass.GPSCoordinates.name"/>:&nbsp;</form:label></b>
		<form:input path="location.name"/>
		<form:errors path="location.name" cssClass="error"/>
		<br/>
		
		<b><form:label path="location.longitude"><spring:message code="survivalClass.GPSCoordinates.longitude"/>:&nbsp;</form:label></b>
		<form:input path="location.longitude" placeholder="Min: -180.0, Max: 180.0"/>
		<form:errors path="location.longitude" cssClass="error"/>
		<br/>
		
		<b><form:label path="location.latitude"><spring:message code="survivalClass.GPSCoordinates.latitude"/>:&nbsp;</form:label></b>
		<form:input path="location.latitude" placeholder="Min: -90.0, Max: 90.0"/>
		<form:errors path="location.latitude" cssClass="error"/>
		<br/>
		
	</fieldset>

		<input type="submit" name="save" value="<spring:message code="survivalClass.save"/>"/>
		&nbsp;
	
	<jstl:if test="${survivalClass.id != 0 }">
		<input type="submit" name="delete" value="<spring:message code="survivalClass.delete"/>"/>
		&nbsp;
	</jstl:if>
	<input type="button" name="cancel" value="<spring:message code="survivalClass.cancel" />" 
		onclick="javascript: relativeRedir('${cancelURI}');" />

</form:form>