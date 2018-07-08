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

<display:table name="rendezvouses" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">


	<jstl:if
		test="${requestURI == 'rendezvous/admin/list.do' or requestURI == 'rendezvous/user/list-organised.do' or requestURI == 'rendezvous/user/list-rspv.do'}">
		<jstl:choose>
			<jstl:when test="${date gt row.moment}">
				<jstl:set var="backgroundStyle"
					value="background-color: lightgreen; color: black;" />
				<jstl:set var="colorStyle" value="color: black;" />
			</jstl:when>
			<jstl:otherwise>
				<jstl:set var="backgroundStyle" value="" />
				<jstl:set var="colorStyle" value="" />
			</jstl:otherwise>
		</jstl:choose>
	</jstl:if>

	<security:authorize access="hasRole('ADMIN')">
		<jstl:if test="${requestURI == 'rendezvous/admin/list.do'}">
			<display:column style="${backgroundStyle}">
				<a href="rendezvous/admin/delete.do?rendezvousId=${row.id}"><spring:message
						code="rendezvous.delete" /></a>
			</display:column>
		</jstl:if>
	</security:authorize>


	<jstl:choose>
		<jstl:when
			test="${requestURI == 'rendezvous/user/list-organised.do' and row.finalVersion == false and row.deleted == false}">
			<display:column style="${backgroundStyle}">
				<a href="rendezvous/user/edit.do?rendezvousId=${row.id}"><spring:message
						code="rendezvous.edit" /></a>
			</display:column>
		</jstl:when>
		<jstl:when
			test="${requestURI == 'rendezvous/user/list-organised.do' and row.finalVersion == true and row.deleted == false}">
			<display:column style="${backgroundStyle}">
				<a href="rendezvous/user/list-link.do?rendezvousId=${row.id}"><spring:message
						code="rendezvous.assignRendezvouses" /></a>
			</display:column>
		</jstl:when>
		<jstl:when
			test="${requestURI == 'rendezvous/user/list-organised.do' and row.finalVersion == false and row.deleted == true}">
			<display:column style="${backgroundStyle}">

			</display:column>
		</jstl:when>
		<jstl:when test="${requestURI == 'rendezvous/user/list-rspv.do'}">
			<display:column style="${backgroundStyle}">
				<a href="comment/user/edit.do?rendezvousId=${row.id}"><spring:message
						code="rendezvous.comment" /></a>
			</display:column>
		</jstl:when>
		<jstl:when test="${requestURI == 'rendezvous/user/list-link.do'}">
			<jstl:if test="${!rendezvousSource.linkedRendezvouses.contains(row)}">
				<display:column style="${backgroundStyle}">
					<a
						href="rendezvous/user/assign.do?rendezvousSourceId=${rendezvousSource.id}&rendezvousTargetId=${row.id}"><spring:message
							code="rendezvous.assign" /></a>
				</display:column>
			</jstl:if>
			<jstl:if test="${rendezvousSource.linkedRendezvouses.contains(row)}">
				<display:column style="${backgroundStyle}">
					<a
						href="rendezvous/user/unassign.do?rendezvousSourceId=${rendezvousSource.id}&rendezvousTargetId=${row.id}"><spring:message
							code="rendezvous.unassign" /></a>
				</display:column>
			</jstl:if>
		</jstl:when>
		<jstl:when test="${requestURI == 'rendezvous/admin/list.do'}">

		</jstl:when>
		<jstl:otherwise>

		</jstl:otherwise>
	</jstl:choose>

	<jstl:if test="${requestURI != 'rendezvous/user/list-link.do'}">
		<display:column style="${backgroundStyle}">
			<a href="rendezvous/display.do?rendezvousId=${row.id}"><spring:message
					code="rendezvous.display" /></a>
		</display:column>
	</jstl:if>

	<spring:message var="nameHeader" code="rendezvous.name" />
	<display:column property="name" title="${nameHeader}"
		style="${backgroundStyle}" />

	<spring:message var="momentHeader" code="rendezvous.moment" />
	<spring:message var="formatDate" code="rendezvous.format.date" />
	<display:column property="moment" title="${momentHeader}"
		format="${formatDate}" sortable="true" style="${backgroundStyle}" />

	<spring:message var="adultHeader" code="rendezvous.onlyAdults" />
	<display:column title="${adultHeader}" sortable="true"
		style="${backgroundStyle}">
		<jstl:choose>
			<jstl:when test="${row.adult == true}">
				<spring:message code="rendezvous.yes" />
			</jstl:when>
			<jstl:when test="${row.adult == false}">
				<spring:message code="rendezvous.no" />
			</jstl:when>
		</jstl:choose>
	</display:column>

	<jstl:if test="${requestURI == 'rendezvous/user/list-organised.do'}">
		<spring:message var="deletedHeader" code="rendezvous.deleted" />
		<display:column title="${deletedHeader}" sortable="true"
			style="${backgroundStyle}">
			<jstl:choose>
				<jstl:when test="${row.deleted == true}">
					<spring:message code="rendezvous.yes" />
				</jstl:when>
				<jstl:when test="${row.deleted == false}">
					<spring:message code="rendezvous.no" />
				</jstl:when>
			</jstl:choose>
		</display:column>

		<spring:message var="finalVersionHeader"
			code="rendezvous.finalVersion" />
		<display:column title="${finalVersionHeader}" sortable="true"
			style="${backgroundStyle}">
			<jstl:choose>
				<jstl:when test="${row.finalVersion == true}">
					<spring:message code="rendezvous.yes" />
				</jstl:when>
				<jstl:when test="${row.finalVersion == false}">
					<spring:message code="rendezvous.no" />
				</jstl:when>
			</jstl:choose>
		</display:column>
	</jstl:if>

</display:table>

<jstl:if
	test="${requestURI == 'rendezvous/admin/list.do' or requestURI == 'rendezvous/user/list-organised.do' or requestURI == 'rendezvous/user/list-rspv.do'}">
	<span style="background-color: lightgreen; color: black;"><spring:message
			code="rendezvous.past" /></span>
	<br />
</jstl:if>

<security:authorize access="hasRole('USER')">
	<a href="rendezvous/user/create.do"><spring:message
			code="rendezvous.create" /></a>
	<br />
</security:authorize>

<jstl:choose>
	<jstl:when
		test="${requestURI == 'rendezvous/user/list-link.do' and rendezvousSource.finalVersion == false}">
		<acme:cancel
			url="rendezvous/user/edit.do?rendezvousId=${rendezvousSource.id}"
			code="rendezvous.cancel" />
	</jstl:when>
	<jstl:when
		test="${requestURI == 'rendezvous/user/list-link.do' and rendezvousSource.finalVersion == true}">
		<acme:cancel url="rendezvous/user/list-organised.do"
			code="rendezvous.cancel" />
	</jstl:when>
	<jstl:when test="${requestURI == 'rendezvous/list-rspv.do'}">
		<acme:cancel url="user/list.do" code="rendezvous.cancel" />
	</jstl:when>
	<jstl:otherwise>
		<acme:cancel url="welcome/index.do" code="rendezvous.cancel" />
	</jstl:otherwise>
</jstl:choose>


