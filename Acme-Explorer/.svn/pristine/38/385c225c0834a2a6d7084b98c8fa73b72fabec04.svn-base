<%--
 * list.jsp
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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="tags" id="row" pagesize="5" class="displaytag" requestURI="tags/admin/list.do">

	<display:column>
		<a href="tag/admin/delete.do?tagId=${row.id}"><spring:message code="tag.delete"/></a>
	</display:column>
	
	<display:column>
		<jstl:if test="${row.hasValues.size() == 0}">
			<a href="tag/admin/edit.do?tagId=${row.id}"><spring:message code="tag.edit"/></a>
		</jstl:if>
	</display:column>
	
	<spring:message var="nameHeader" code="tag.name" />
	<display:column title="${nameHeader}">
		<jstl:out value="${row.name}" />
	</display:column>
		
</display:table>

<a href="tag/admin/create.do"><spring:message code="tag.create"/></a>
<br/>

<input type="button" name="cancel" value="<spring:message code="tag.cancel" />" onclick="javascript: relativeRedir('configuration/admin/edit.do')" />
