

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

<form:form action="message/edit.do" modelAttribute="patata">

	<form:hidden path="id" />

	<jstl:choose>
		<jstl:when test="${ recipient!=null }">
			<form:hidden path="recipient"/>
			<b><label> <spring:message code="message.recipient" />:&nbsp;
			</label></b>
			<jstl:out value="${recipient.name}" />
			<br />
		</jstl:when>
		<jstl:when test="${patata.id == 0}">
			<acme:select items="${actors}" itemLabel="name"
				code="message.recipient" path="recipient" />
		</jstl:when>
		<jstl:otherwise>
			<b><label> <spring:message code="message.recipient" />:&nbsp;
			</label></b>
			<jstl:out value="${recipientName}" />
			<br />

			<b><label> <spring:message code="message.sender" />:&nbsp;
			</label></b>
			<jstl:out value="${senderName}"/>
			<br />

			<b><label> <spring:message code="message.moment" />:&nbsp;
			</label></b>
			<spring:message code="message.pattern.date" var="patternDate" />
			<fmt:formatDate value="${patata.moment}"
				pattern="${patternDate}" />
			<br />
		</jstl:otherwise>
	</jstl:choose>

	<jstl:choose>
		<jstl:when test="${patata.id == 0}">
			<acme:textbox code="message.subject" path="subject" />
		</jstl:when>
		<jstl:otherwise>
			<b><label> <spring:message code="message.subject" />:&nbsp;
			</label></b>
			<jstl:out value="${patata.subject}" />
			<br />
		</jstl:otherwise>
	</jstl:choose>

	<jstl:choose>
		<jstl:when test="${patata.id == 0}">
			<acme:textarea code="message.body" path="body" />
		</jstl:when>
		<jstl:otherwise>
			<b><label> <spring:message code="message.body" />:&nbsp;
			</label></b>
				<jstl:out value="${patata.body}" />
			<br />
		</jstl:otherwise>
	</jstl:choose>

	<jstl:choose>
		<jstl:when test="${patata.id == 0}">
			<b><form:label path="priority">
					<spring:message code="message.priority" />:&nbsp;</form:label></b>
			<form:select path="priority">
				<form:options items="${priorities}" />
			</form:select>
			<br />
		</jstl:when>
		<jstl:otherwise>
			<b><label> <spring:message code="message.priority" />:&nbsp;
			</label></b>
			<jstl:out value="${patata.priority}" />
			<br />
		</jstl:otherwise>
	</jstl:choose>

	<jstl:if test="${patata.id != 0}">
		<acme:selectObligatory items="${folders}" itemLabel="name"
			code="message.folder" path="folder" />
	</jstl:if>

	<jstl:choose>
		<jstl:when test="${patata.id == 0}">
			<acme:submit name="save" code="message.send" />
			&nbsp;
		</jstl:when>
		<jstl:when test="${patata.id != 0}">
			<acme:submit name="save" code="message.save" />
			&nbsp;
		</jstl:when>
	</jstl:choose>
	<jstl:if test="${patata.id != 0}">
		<acme:submit name="delete" code="message.delete" />
		&nbsp;
	</jstl:if>
	<acme:cancel url="folder/list.do" code="message.cancel" />

</form:form>