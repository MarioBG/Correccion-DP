
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

<form:form action="advertisement/agent/edit.do"
	modelAttribute="advertisementForm">

	<form:hidden path="id" />
	<form:hidden path="newspaperId" />

	<acme:textbox code="advertisement.title" path="title" />
	<br />

	<acme:textbox code="advertisement.bannerURL" path="banner" />
	<br />

	<acme:textbox code="advertisement.infoPageLink" path="page" />
	<br />


	<fieldset>
		<legend>
			<spring:message code="advertisement.introduceCreditCard" />
		</legend>
		<br />

		<acme:textbox code="advertisement.title" path="holder" />
		<br />


		<acme:textbox code="creditCard.holderName" path="title" />
		<br />


		<acme:textbox code="creditCard.brand" path="brand" />
		<br />


		<acme:textbox code="creditCard.number" path="number" />
		<br />


		<acme:textbox code="creditCard.expirationMonth" path="expirationMonth" />
		<br />


		<acme:textbox code="creditCard.expirationYear" path="expirationYear" />
		<br />

		<acme:textbox code="creditCard.CVV" path="cvv" />
		<br />


	</fieldset>

	<br />

	<input type="submit" name="save"
		value="<spring:message code="advertisement.save" />" />&nbsp; 

	<%-- SOLO SE PUEDE ELIMINAR SI ESTAMOS EDITANDO, NO SI ESTAMOS CREANDO --%>
	<jstl:if test="${advertisementForm.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="advertisement.delete" />"
			onclick="return confirm('<spring:message code="advertisement.confirm.delete" />')" />&nbsp; 
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="advertisement.cancel" />"
		onclick="javascript: relativeRedir('advertisement/agent/list.do');" />

</form:form>
