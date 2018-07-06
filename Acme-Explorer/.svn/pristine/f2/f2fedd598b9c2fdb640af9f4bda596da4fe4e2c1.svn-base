<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<form:form action="story/explorer/edit.do" modelAttribute="story">


	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="writer"/>
	
	
	<form:label path="trip"><spring:message code="story.trip"/></form:label>
	<form:select path="trip">
		<form:option label="----" value="0"/>
		<form:options items="${trips}" itemLabel="ticker" itemValue="id"/>
	</form:select>
	<form:errors path="trip" cssClass="error"/>
	<br/>
	
	<form:label path="title"><spring:message code="story.title"/></form:label>
	<form:input path="title"/>
	<form:errors path="title" cssClass="error"/>
	<br/>
	
	<form:label path="text"><spring:message code="story.text"/></form:label>
	<form:textarea path="text"/>
	<form:errors path="text" cssClass="error"/>
	<br/>
	
	<form:label path="attachments"><spring:message code="story.attachments"/></form:label>
	<form:textarea path="attachments" />
	<form:errors path="attachments" cssClass="error"/>
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="story.save"/>" />
		<jstl:if test="${story.id !=0 }">
			<input type="submit" name="delete" value="<spring:message code="story.delete"/>" />	
		</jstl:if>
	<input type="button" name="cancel" value="<spring:message code="story.cancel" />" 
			onclick="javascript: relativeRedir('story/explorer/list.do');" />

</form:form>
