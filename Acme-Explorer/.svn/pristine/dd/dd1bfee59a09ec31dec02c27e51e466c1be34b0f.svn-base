<%-- edit.jsp de MiscellaneousRecord --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="miscellaneousRecord/ranger/edit.do" modelAttribute="miscellaneousRecord">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<spring:message var="miscellaneousRecordTitle" code="miscellaneousRecord.title"/>
	<b><form:label path="title"/>${miscellaneousRecordTitle}:&nbsp;</b>
	<form:input path="title"/>
	<form:errors path="title" cssClass="error"/>
	<br/>
	
	<spring:message var="miscellaneousRecordAttachment" code="miscellaneousRecord.attachment"/>
	<b><form:label path="attachment"/>${miscellaneousRecordAttachment}:&nbsp;</b>
	<form:input path="attachment"/>
	<form:errors path="attachment" cssClass="error"/>
	<br/>
	
	<spring:message var="miscellaneousRecordComments" code="miscellaneousRecord.comments"/>
	<b><form:label path="comments"/>${miscellaneousRecordComments}:&nbsp;</b>
	<form:input path="comments"/>
	<form:errors path="comments" cssClass="error"/>
	<br/>
	
	<spring:message var="miscellaneousRecordSave" code="miscellaneousRecord.save"/>
	<input type="submit" name="save" value="${miscellaneousRecordSave}"/>
	
	<jstl:if test="${miscellaneousRecord.id != 0}"> 
		<spring:message var="miscellaneousRecordDelete" code="miscellaneousRecord.delete"/>
		<input type="submit" name="delete" value="${miscellaneousRecordDelete}"/>
	</jstl:if>
	<spring:message var="miscellaneousRecordCancel" code="miscellaneousRecord.cancel"/>
	<input type="button" name="cancel" value="${miscellaneousRecordCancel}" onclick="javascript:relativeRedir('${cancelURI}')"/>
</form:form>