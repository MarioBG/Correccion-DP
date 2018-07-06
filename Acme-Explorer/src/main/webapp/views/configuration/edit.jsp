<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="configuration/admin/edit.do" modelAttribute="configuration">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
																											
	<form:label path="banner"><spring:message code="configuration.banner"/></form:label>
	<form:input path="banner" />
	<form:errors path="banner" cssClass="error"/>
	<br/>
	
	<form:label path="englishWelcome"><spring:message code="configuration.englishWelcome"/></form:label>
	<form:input path="englishWelcome"/>
	<form:errors path="englishWelcome" cssClass="error"/>
	<br/>
	
	<form:label path="spanishWelcome"><spring:message code="configuration.spanishWelcome"/></form:label>
	<form:textarea path="spanishWelcome"/>
	<form:errors path="spanishWelcome" cssClass="error"/>
	<br/>
	
	<form:label path="spamWords"><spring:message code="configuration.spamWords"/></form:label>
	<form:textarea path="spamWords" />
	<form:errors path="spamWords" cssClass="error"/>
	<br/>
	
	<form:label path="vat"><spring:message code="configuration.vat"/></form:label>
	<form:input path="vat" />
	<form:errors path="vat" cssClass="error"/>
	<br/>
	
	<form:label path="countryCode"><spring:message code="configuration.countryCode"/></form:label>
	<form:input path="countryCode" />
	<form:errors path="countryCode" cssClass="error"/>
	<br/>
	
	<form:label path="finderCache"><spring:message code="configuration.finderCache"/></form:label>
	<form:input path="finderCache" />
	<form:errors path="finderCache" cssClass="error"/>
	<br/>
	
	<form:label path="maxTripsDisplay"><spring:message code="configuration.maxTripDisplay"/></form:label>
	<form:input path="maxTripsDisplay" />
	<form:errors path="maxTripsDisplay" cssClass="error"/>
	<br/>
	
	<a href="tag/admin/list.do"><spring:message code="configuration.listTags"/></a>
	
	<input type="submit" name="save" value="<spring:message code="configuration.save" />" />
	<input type="button" name="cancel" value="<spring:message code="configuration.cancel" />" 
			onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>