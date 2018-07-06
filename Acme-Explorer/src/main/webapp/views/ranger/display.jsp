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

<h3><spring:message code="ranger.name"/>:&nbsp;<jstl:out value="${ranger.name}" /></h3>
        
<b><spring:message code="ranger.surname"/>:&nbsp;</b><jstl:out value="${ranger.surname}" />
<br/>

<b><spring:message code="ranger.email"/>:&nbsp;</b><jstl:out value="${ranger.email}" />
<br/>

<b><spring:message code="ranger.address"/>:&nbsp;</b><jstl:out value="${ranger.address}" />
<br/>

<b><spring:message code="ranger.phone"/>:&nbsp;</b><jstl:out value="${ranger.phone}" />
<br/>
    
<jstl:if test="${ranger.curriculum != null}">	
	<b><spring:message code="ranger.curriculum"/>:&nbsp;</b><a href="curriculum/display.do?curriculumId=${ranger.curriculum.id}"><jstl:out value = "${ranger.curriculum.ticker}"/></a>
	<br/>
</jstl:if>

<input type="button" name="cancel" value="<spring:message code="ranger.cancel" />" onclick="window.history.back();"/>
                
                