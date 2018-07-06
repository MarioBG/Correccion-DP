<%-- edit.jsp de Application --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h3>
	<spring:message var="applicationTrip" code="application.trip"/>
	<b>${applicationTrip}:&nbsp;</b> <jstl:out value="${application.trip.title}"/>
</h3>

<spring:message var="applicationComments" code="application.comments"/>
<b>${applicationComments}:&nbsp;</b> <jstl:out value="${application.comments}"/>
<br/>

<jstl:choose>
	<jstl:when test="${application.status eq 'REJECTED'}">
		<spring:message var="applicationRejectReason" code="application.rejectReason"/>
		<b>${applicationRejectReason}:&nbsp;</b> <jstl:out value="${application.rejectReason}"/>
		<br/>
	</jstl:when>
</jstl:choose>


<spring:message var="applicationStatusLabel" code="application.statusLabel"/>
<b>${applicationStatusLabel}:&nbsp;</b> <jstl:out value="${application.status}"/>
<br/>
<br/>
<jstl:if test="${not empty application.creditCard}">
	<fieldset>
		<spring:message var="applicationCreditCardLegend" code="application.creditCard.legend"/>
		<legend><b><jstl:out value="${applicationCreditCardLegend}"/>:&nbsp;</b></legend>
		
		<spring:message var="applicationCreditCardHolder" code="application.creditCard.holder"/>
		<b>${applicationCreditCardHolder}:&nbsp;</b><jstl:out value="${application.creditCard.holder}"/>
		<br/>
		
		<spring:message var="applicationCreditCardBrand" code="application.creditCard.brand"/>
		<b> ${applicationCreditCardBrand}:&nbsp;</b><jstl:out value="${application.creditCard.brand}"/>
		<br/>
		
		<spring:message var="applicationCreditCardNumber" code="application.creditCard.number"/>
		<b> ${applicationCreditCardNumber}:&nbsp;</b>  <jstl:out value="${application.creditCard.number}"/>
		<br/>
		
		<spring:message var="applicationCreditCardExpirationMonth" code="application.creditCard.expirationMonth"/>
		<b> ${applicationCreditCardExpirationMonth}:&nbsp;</b> <jstl:out value="${application.creditCard.expirationMonth}"/>
		<br/>
		
		<spring:message var="applicationCreditCardExpirationYear" code="application.creditCard.expirationYear"/>
		<b> ${applicationCreditCardExpirationYear}:&nbsp;</b> <jstl:out value="${application.creditCard.expirationYear}"/>
		<br/>
		
		<spring:message var="applicationCreditCardCvv" code="application.creditCard.cvv"/>
		<b> ${applicationCreditCardCvv}:&nbsp;</b> <jstl:out value="${application.creditCard.cvv}"/>
		<br/>
	</fieldset>
</jstl:if>

<security:authorize access="hasRole('RANGER')">
	<spring:message var="applicationEditDisplay" code="application.displayEdit"/>
	<a href="application/manager/application/edit.do?applicationId=${application.id}">${applicationEditDisplay}</a>
</security:authorize>
<br/>

<spring:message var="applicationCancel" code="application.cancel" />
<input type="button" name="cancel" value="${applicationCancel}" onclick="javascript: relativeRedir('${cancelURI}');" />
<br/>
<br/>