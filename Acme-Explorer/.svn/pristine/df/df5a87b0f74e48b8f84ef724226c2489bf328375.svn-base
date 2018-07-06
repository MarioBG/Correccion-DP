<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<form:form action="auditRecord/auditor/edit.do" modelAttribute="auditRecord">


	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="auditor"/>
	
	
	<form:label path="trip"><spring:message code="auditRecord.trip"/></form:label>
	<form:select path="trip">
		<form:option label="----" value="0"/>
		<form:options items="${trips}" itemLabel="ticker" itemValue="id"/>
	</form:select>
	<form:errors path="trip" cssClass="error"/>
	<br/>
	
	<form:label path="moment"><spring:message code="auditRecord.moment"/></form:label>
	<form:input path="moment" placeholder="dd/mm/yyyy hh:mm"/>
	<form:errors path="moment" cssClass="error"/>
	<br/>
	
	<form:label path="title"><spring:message code="auditRecord.title"/></form:label>
	<form:input path="title"/>
	<form:errors path="title" cssClass="error"/>
	<br/>
	
	<form:label path="description"><spring:message code="auditRecord.description"/></form:label>
	<form:textarea path="description"/>
	<form:errors path="description" cssClass="error"/>
	<br/>
	
	<form:label path="attachments"><spring:message code="auditRecord.attachment"/></form:label>
	<form:input path="attachments" />
	<form:errors path="attachments" cssClass="error"/>
	<br/>
	
	<form:label path="finalVersion"><spring:message code="auditRecord.finalVersion"/></form:label>
	<form:checkbox path="finalVersion" ></form:checkbox>
		
	<form:errors path="trip" cssClass="error"/>
	<br/>
	
	<jstl:if test="${auditRecord.finalVersion == false }">
		<input type="submit" name="save" value="<spring:message code="auditRecord.save"/>" />
			<jstl:if test="${auditRecord.id !=0 }">
				<input type="submit" name="delete" value="<spring:message code="auditRecord.delete"/>" />	
			</jstl:if>
	</jstl:if>
	<input type="button" name="cancel" value="<spring:message code="auditRecord.cancel" />" 
			onclick="javascript: relativeRedir('auditRecord/auditor/list.do');" />

</form:form>