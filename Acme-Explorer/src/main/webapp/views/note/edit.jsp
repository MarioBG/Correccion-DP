


<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<form:form action="${actionURI}" modelAttribute="note">


	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="auditor"/>
	
	<security:authorize access="hasRole('AUDITOR')">
		<form:label path="trip"><spring:message code="note.trip"/></form:label>
		<form:select path="trip">
			<form:option label="----" value="0"/>
			<form:options items="${trips}" itemLabel="title" itemValue="id"/>
		</form:select>
		<form:errors path="trip" cssClass="error"/>
		<br/>
		
		<form:label path="moment"><spring:message code="note.moment"/></form:label>
		<form:input path="moment" placeholder="dd/mm/yyyy hh:mm"/>
		<form:errors path="moment" cssClass="error"/>
		<br/>
		
		<form:label path="remark"><spring:message code="note.remark"/></form:label>
		<form:input path="remark"/>
		<form:errors path="remark" cssClass="error"/>
		<br/>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
		<form:hidden path="trip"/>
		<form:hidden path="remark"/>
		<form:hidden path="moment"/>
		<form:label path="reply"><spring:message code="note.reply"/></form:label>
		<form:textarea path="reply"/>
		<form:errors path="reply" cssClass="error"/>
		<br/>
		
<%-- 		<form:label path="replyMoment"><spring:message code="note.replyMoment"/></form:label>
		<form:input path="replyMoment" placeholder="dd/mm/yyyy hh:mm"/>
		<form:errors path="replyMoment" cssClass="error"/>
		<br/>
 --%>
		<form:hidden path="replyMoment"/>
		
	</security:authorize>
	
	<input type="submit" name="save" value="<spring:message code="note.save"/>" />
	
	<input type="button" name="cancel" value="<spring:message code="note.cancel" />" 
			onclick="javascript: relativeRedir('${cancelURI}');" />

</form:form>

