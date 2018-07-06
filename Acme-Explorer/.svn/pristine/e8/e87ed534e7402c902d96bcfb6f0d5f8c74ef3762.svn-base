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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<h1><spring:message code="sponsorship.banner"/>:&nbsp;</h1>
	<br/>
	<img src="http://bethelweb.org/wp-content/uploads/Mission-Trip-Web-Banner.png"/>

	<h2><spring:message code="sponsorship.information"/>:&nbsp;<jstl:out value="${sponsorship.information}" /></h2>
	<br/>

	<h3><spring:message code="creditCard.number"/>:&nbsp;<jstl:out value="${sponsorship.creditCard.number}" /></h3>
	<br/>
</div>

<input type="button" name="cancel" value="<spring:message code="sponsorship.cancel" />" onclick="javascript: relativeRedir('sponsorship/sponsor/list.do');" />










