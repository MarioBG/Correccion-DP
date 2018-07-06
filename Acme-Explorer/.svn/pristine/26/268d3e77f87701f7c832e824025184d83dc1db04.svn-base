

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

<form:form action="message/edit.do" modelAttribute="messageEdit">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="sender"/>
	<form:hidden path="recipient"/>
	<form:hidden path="subject"/>
	<form:hidden path="body"/>
	<form:hidden path="priority"/>


	<b><form:label path="recipient">
		<spring:message code="message.recipient" />:&nbsp;</form:label></b>

		<jstl:out value="${messageEdit.recipient.userAccount.username}"/>
	<form:errors path="recipient" cssClass="error" />
	<br />

	<b><form:label path="subject">
			<spring:message code="message.subject" />:&nbsp;</form:label></b>

			<input value="${messageEdit.subject}" readonly="readonly" />
	<form:errors path="subject" cssClass="error" />
	<br />

	<b><form:label path="body">
			<spring:message code="message.body" />:&nbsp;</form:label></b>

			<textarea readonly="readonly">
				<jstl:out value="${messageEdit.body}" />
			</textarea>
	<form:errors path="body" cssClass="error" />
	<br />

	<b><form:label path="priority">
		<spring:message code="message.priority" />:&nbsp;</form:label></b>

			<input value="${messageEdit.priority}" readonly="readonly" />
	<form:errors path="priority" cssClass="error" />
	<br />

	<b><form:label path="folder">
		<spring:message code="message.folder" />:&nbsp;</form:label></b>

			<form:select path="folder">
				<form:options items="${folders}" itemLabel="name" itemValue="id" />
			</form:select>
	<form:errors path="folder" cssClass="error" />
	<br />


			<input type="submit" name="save"
				value="<spring:message code="message.save"/>" />
			&nbsp;

		<input type="submit" name="delete"
			value="<spring:message code="message.delete"/>" />
		&nbsp;

	<input type="button" name="cancel"
		value="<spring:message code="message.cancel" />"
		onclick="javascript: relativeRedir('folder/list.do');" />

</form:form>