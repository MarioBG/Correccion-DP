<%--
 * list.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- displaying grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="user" requestURI="${requestURI }" id="row">

	<!-- Attributes -->

	<spring:message code="user.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="user.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}"
		sortable="true" />

	<spring:message code="user.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="user.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true" />

	<spring:message code="user.address" var="addressHeader" />
	<display:column property="address" title="${addressHeader}"
		sortable="true" />

	<spring:message code="user.birth" var="birthHeader" />
	<spring:message var="formatDate" code="user.format.date" />
	<display:column property="birth" title="${birthHeader}"
		format="${formatDate}" sortable="true" />

	<spring:message code="user.rendezvouses" var="rsvpdRendezvous" />
	<display:column title="${rsvpdRendezvous}">
		<jstl:if test="${not empty row.rsvpdRendezvouses}">
			<a href="rendezvous/list-rspv.do?userId=${row.id }"> <spring:message
					code="user.rendezvouses.link" />
			</a>
		</jstl:if>
	</display:column>

	<%-- <spring:message code="user.answer" var="answerHeader"/>
	<display:column title="${answerHeader}">
		<a href="answer/user/list.do?answerId=${row.id }">
			<spring:message code="user.answer.link"/>
		</a>
	</display:column> --%>

</display:table>

<jstl:choose>
	<jstl:when test="${requestURI == 'user/listRendezvousAttends.do'}">
		<acme:cancel url="rendezvous/display.do?rendezvousId=${rendezvous.id}"
			code="terms.back" />
	</jstl:when>
	<jstl:otherwise>
		<acme:cancel url="welcome/index.do" code="terms.back" />
	</jstl:otherwise>
</jstl:choose>
