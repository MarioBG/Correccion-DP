<%-- list.jsp de Application --%>

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

<display:table name="services" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<jstl:choose>
		<jstl:when test="${row.cancelled == true}">
			<jstl:set var="backgroundStyle"
				value="background-color: lightcoral; color: black;" />
			<jstl:set var="colorStyle" value="color: black;" />
		</jstl:when>
		<jstl:otherwise>
			<jstl:set var="backgroundStyle" value="" />
			<jstl:set var="colorStyle" value="" />
		</jstl:otherwise>
	</jstl:choose>

	<security:authorize access="hasRole('ADMIN')">
		<display:column style="${backgroundStyle}">
			<jstl:if test="${row.cancelled == false}">
				<a href="service/admin/cancel.do?serviceId=${row.id}"><spring:message
						code="service.cancel" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<jstl:if test="${requestURI == 'service/manager/list.do'}">
		<display:column style="${backgroundStyle}">
			<jstl:if test="${empty row.requests}">
				<a href="service/manager/delete.do?serviceId=${row.id}"><spring:message
						code="service.delete" /></a>
			</jstl:if>
		</display:column>

		<display:column style="${backgroundStyle}">
			<a href="service/manager/edit.do?serviceId=${row.id}"><spring:message
					code="service.edit" /></a>
		</display:column>
	</jstl:if>

	<display:column style="${backgroundStyle}">
		<a href="service/display.do?serviceId=${row.id}"><spring:message
				code="service.display" /></a>
	</display:column>

	<spring:message var="nameHeader" code="service.name" />
	<display:column property="name" title="${nameHeader}"
		style="${backgroundStyle}" />

	<jstl:if test="${requestURI == 'service/list.do'}">
		<spring:message var="managerHeader" code="service.manager" />
		<display:column title="${nameHeader}" style="${backgroundStyle}">
			<jstl:out value="${row.manager.name} ${row.manager.surname}" />
		</display:column>
	</jstl:if>

	<spring:message var="categoryHeader" code="service.category" />
	<display:column title="${categoryHeader}" style="${backgroundStyle}">
		<jstl:out value="${row.category.name}" />
	</display:column>

	<spring:message var="cancelledHeader" code="service.cancelled" />
	<display:column title="${cancelledHeader}" sortable="true"
		style="${backgroundStyle}">
		<jstl:choose>
			<jstl:when test="${row.cancelled == true}">
				<spring:message code="service.yes" />
			</jstl:when>
			<jstl:when test="${row.cancelled == false}">
				<spring:message code="service.no" />
			</jstl:when>
		</jstl:choose>
	</display:column>

</display:table>

<span style="background-color: lightcoral; color: black;"><spring:message
		code="service.isCancelled" /></span>
<br />

<%-- <jstl:if test="${requestURI == 'service/manager/list.do'}"> --%>
<security:authorize access="hasRole('MANAGER')">
	<a href="service/manager/create.do"><spring:message
			code="service.create" /></a>
	<br />
</security:authorize>
<%-- </jstl:if> --%>

<jstl:choose>
	<jstl:when test="${rendezvous != null }">
		<acme:cancel code="service.cancel"
			url="rendezvous/display.do?rendezvousId=${rendezvous.id}" />
	</jstl:when>
	<jstl:otherwise>
		<acme:cancel code="service.cancel" url="welcome/index.do" />
	</jstl:otherwise>
</jstl:choose>
