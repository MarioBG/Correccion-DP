<%--
 * action-1.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<h1><spring:message code="auditRecord.title"/>:&nbsp;<jstl:out value="${auditRecord.title}"/></h1>
<br/>

<b><spring:message code="auditRecord.moment"/>:&nbsp;</b><fmt:formatDate value="${auditRecord.moment}" pattern="dd/MM/yyyy HH:mm"/>
<br/>

<b><spring:message code="auditRecord.description"/>:&nbsp;</b><jstl:out value="${auditRecord.description}" />
<br/>
<b><spring:message code="auditRecord.attachment"/>:&nbsp;</b><jstl:forEach var="x" items="${auditRecord.attachments }">
			<jstl:out value="${x}" /></jstl:forEach>

<br/>
<br/>

<b><spring:message code="auditRecord.finalVersion"/>:&nbsp; </b>

<jstl:if test="${auditRecord.finalVersion==true}">
		<jstl:out value="YES" /></jstl:if>
<jstl:if test="${auditRecord.finalVersion==false}">
		 <jstl:out value="NO" />
</jstl:if>	

<br/>
<br/>

<input type="button" name="cancel" value="<spring:message code="auditRecord.cancel" />"      
  		onclick="javascript: relativeRedir('${cancelURI}');" />