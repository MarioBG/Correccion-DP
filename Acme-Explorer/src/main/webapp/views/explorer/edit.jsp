<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="${actionURI}" modelAttribute="explorer" onsubmit="return validateForm()">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="userAccount"/>
	<form:hidden path="folders" />
	<form:hidden path="contacts" />
	<form:hidden path="socialIdentities" />
	<form:hidden path="stories" />
	<form:hidden path="applications" />
	<form:hidden path="finder" />
	<form:hidden path="survivalClasses" />
	
	<jstl:if test="${explorer.id == 0}">
		<form:hidden path="userAccount.authorities" />
	
		<b><form:label path="userAccount.username"><spring:message code="explorer.userAccount.username" /></form:label>:&nbsp;</b>
		<form:input path="userAccount.username" />
		<form:errors path="userAccount.username" cssClass="error"  />
		<br />

		<b><form:label path="userAccount.password"><spring:message code="explorer.userAccount.password" /></form:label>:&nbsp;</b>
		<form:password path="userAccount.password" />
		<form:errors path="userAccount.password" cssClass="error"  />
		<br />
	</jstl:if>
	
	<b><form:label path="name"><spring:message code="explorer.name"/></form:label>:&nbsp;</b>
	<form:input path="name"/>
	<form:errors path="name" cssClass="error"/>
	<br/>
	
	<b><form:label path="surname"><spring:message code="explorer.surname"/></form:label>:&nbsp;</b>
	<form:input path="surname"/>
	<form:errors path="surname" cssClass="error"/>
	<br/>
	
	<b><form:label path="email"><spring:message code="explorer.email"/></form:label>:&nbsp;</b>
	<form:input path="email"/>
	<form:errors path="email" cssClass="error"/>
	<br/>
	
	<b><form:label path="address"><spring:message code="explorer.address"/></form:label>:&nbsp;</b>
	<form:input path="address"/>
	<form:errors path="address" cssClass="error"/>
	<br/>
	
	<b><form:label path="phone"><spring:message code="explorer.phone" /></form:label>:&nbsp;</b>
  	<form:input id="phoneId" path="phone" placeholder="+CC (AC) PN" />
  	<form:errors cssClass="error" path="phone" />
  	<br />
  	
  	<jstl:if test="${explorer.id != 0}">
	  	<spring:message var="listContacts" code="explorer.contacts"/>
		<a href="contact/explorer/list.do"><jstl:out value="${listContacts}"/></a>
		<br/>
	</jstl:if>
  
	<input type="submit" name="save" value="<spring:message code="explorer.save"/>" />
	&nbsp;
	<input type="button" name="cancel" value="<spring:message code="explorer.cancel" />" onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>

<script>

function validateForm() {
 <spring:message code="explorer.phone.ask" var="ask"/>
    var x = document.getElementById("phoneId").value;
    var patt = new RegExp("^(\\+[1-9][0-9]{2}|\\+[1-9][0-9]|\\+[1-9])(\\s\\([1-9][0-9]{2}\\)|\\ \\([1-9][0-9]\\)|\\ \\([1-9]\\))?(\\ \\d{4,})|(\\d{4,})$");
    if(x != "" && !patt.test(x)){
        return confirm('<jstl:out value="${ask}"/>');
    } 
}

</script>