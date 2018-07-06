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

<form:form action="trip/manager/edit.do" modelAttribute="trip">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="manager"/>
	<form:hidden path="sponsorships"/>
	<form:hidden path="survivalClasses"/>
	<form:hidden path="stages"/>
	<form:hidden path="hasValues"/>
	<form:hidden path="notes"/>
	<form:hidden path="auditRecords"/>
	<form:hidden path="stories"/>
	<form:hidden path="applications"/>
	<form:hidden path="ticker" />
	<form:hidden path="price" />
	
	<b><form:label path="ticker"><spring:message code="trip.ticker"/>:&nbsp;</form:label></b>
	<input value="${trip.ticker}" readonly="readonly"/>
	<form:errors path="ticker" cssClass="error"/>
	<br/>
	
	<b><form:label path="title"><spring:message code="trip.title"/>:&nbsp;</form:label></b>
	<form:input path="title"/>
	<form:errors path="title" cssClass="error"/>
	<br/>
	
	<b><form:label path="description"><spring:message code="trip.description"/>:&nbsp;</form:label></b>
	<form:textarea path="description"/>
	<form:errors path="description" cssClass="error"/>
	<br/>
	
	<b><form:label path="category"><spring:message code="trip.category"/>:&nbsp;</form:label></b>
	<form:select path="category">
		<form:option label="----" value="0"/>
		<form:options items="${categories}" itemLabel="name" itemValue="id"/>
	</form:select>
	<form:errors path="category" cssClass="error"/>
	<br/>
	
	<b><form:label path="ranger"><spring:message code="trip.ranger"/>:&nbsp;</form:label></b>
	<form:select path="ranger">
		<form:option label="----" value="0"/>
		<form:options items="${rangers}" itemLabel="name" itemValue="id"/>
	</form:select>
	<form:errors path="ranger" cssClass="error"/>
	<br/>
	
	<b><form:label path="legalText"><spring:message code="trip.legalText"/>:&nbsp;</form:label></b>
	<form:select path="legalText">
		<form:option label="----" value="0"/>
		<form:options items="${legalTexts}" itemLabel="title" itemValue="id"/>
	</form:select>
	<form:errors path="legalText" cssClass="error"/>
	<br/>
	
	<b><form:label path="publicationDate"><spring:message code="trip.publicationDate"/>:&nbsp;</form:label></b>
	<form:input path="publicationDate" placeholder="dd/MM/yyyy HH:mm"/>
	<form:errors path="publicationDate" cssClass="error"/>
	<br/>
	
	<b><form:label path="startDateTrip"><spring:message code="trip.startDate"/>:&nbsp;</form:label></b>
	<form:input path="startDateTrip" placeholder="dd/MM/yyyy HH:mm"/>
	<form:errors path="startDateTrip" cssClass="error"/>
	<br/>
	
	<b><form:label path="endDateTrip"><spring:message code="trip.endDate"/>:&nbsp;</form:label></b>
	<form:input path="endDateTrip" placeholder="dd/MM/yyyy HH:mm"/>
	<form:errors path="endDateTrip" cssClass="error"/>
	<br/>
	
	<b><form:label path="requirements"><spring:message code="trip.requirements"/>:&nbsp;</form:label></b>
	<form:textarea path="requirements"/>
	<div id="requirementsExplanation">
		<spring:message code="trip.eachRequirement"/>
	</div>
	<form:errors path="requirements" cssClass="error"/>
	<br/>
	
	<jstl:if test="${trip.id != 0}">
		<b><form:label path="cancelledReason"><spring:message code="trip.cancelledReason"/>:&nbsp;</form:label></b>
		<jstl:choose>
			<jstl:when test="${trip.cancelledReason != null}">
				<form:textarea path="cancelledReason"  readonly="true"/>
			</jstl:when>
			<jstl:otherwise>
				<form:textarea path="cancelledReason"/>
			</jstl:otherwise>
		</jstl:choose>
		<form:errors path="cancelledReason" cssClass="error"/>
		<br/>
	</jstl:if>
	
	<jstl:if test="${trip.id != 0}">
		<a href="stage/list.do?tripId=${trip.id}"><spring:message code="trip.listStages"/></a>
		<br/>
		<a href="hasValue/list.do?tripId=${trip.id}"><spring:message code="trip.listTags"/></a>
		<br/>
		<a href="survivalClass/list.do?tripId=${trip.id}"><spring:message code="trip.listSurvivalClasses"/></a>
		<br/>
	</jstl:if>
	
	<input type="submit" name="save" value="<spring:message code="trip.save"/>"/>
	&nbsp;
	<jstl:if test="${trip.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="trip.delete"/>"/>
		&nbsp;
	</jstl:if>
	<input type="button" name="cancel" value="<spring:message code="trip.cancel" />" onclick="javascript: relativeRedir('trip/manager/list.do');" />
	&nbsp;
	<jstl:if test="${trip.id != 0}">
		<input type="submit" name="cancelTrip" value="<spring:message code="trip.cancelTrip" />" />
		&nbsp;
	</jstl:if>
	
</form:form>