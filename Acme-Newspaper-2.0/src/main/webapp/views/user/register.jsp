<%--
 * register.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<script type="text/javascript">

$(document).ready(function() {
	 $("#userForm").submit(function(){
	var m = document.getElementById("phone").value;
	var expreg = /^(\+[1-9][0-9]{2}|\+[1-9][0-9]|\+[1-9])(\s\([1-9][0-9]{2}\)|\ \([1-9][0-9]\)|\ \([1-9]\))?(\ \d{4,})|(\d{4,})|(\d+)$/;
	
	if(!expreg.test(m)){
		
		return confirm("Are you sure you want to save this phone?");
	}
});
});

</script>

<form:form action="user/register.do" modelAttribute="userForm">
	
	<acme:textbox code="user.name" path="name"/>
	<br/>
	<acme:textbox code="user.surname" path="surname"/>
	<br/>
	<acme:textbox code="user.email" path="email"/>
	<br/>
	<acme:textbox code="user.phone" path="phone"/>
	<br/>
	<acme:textbox code="user.address" path="address"/>
	<br/>
	<acme:textbox code="user.userName" path="username"/>
	<br/>
	<acme:password code="user.password" path="password"/>
	<br/>
	<acme:password code="user.repeatPassword" path="repeatPassword"/>
	<br/>
	
	<acme:checkbox code="user.acceptTerms" path="termsAndConditions"/>
	
	<a href="terms/list.do"><spring:message code="user.acceptTermsLink"/></a>
	<br />
	
	<acme:submit name="save" code="user.save"/>
	<acme:cancel code="user.cancel" url="welcome/index.do"/>
	
</form:form>
	
	
	
	
	
	
	
	
	
	
