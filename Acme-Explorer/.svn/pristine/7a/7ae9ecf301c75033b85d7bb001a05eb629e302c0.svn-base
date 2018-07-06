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

        <br/>
        <br/>
        <h1><spring:message code="note.trip"/>:&nbsp;<jstl:out value="${note.trip.title}" /></h1>
        <br/>
        
        <b><spring:message code="note.moment"/>:&nbsp;</b><jstl:out value="${note.moment}" />
        <br/>

        <b><spring:message code="note.remark"/>:&nbsp;</b><jstl:out value="${note.remark}" />
        <br/>

        <b><spring:message code="note.reply"/>:&nbsp;</b><jstl:out value="${note.reply}" />
        <br/>
        
        <b><spring:message code="note.replyMoment"/>:&nbsp;</b><jstl:out value="${note.replyMoment}" />
        <br/>
        
        
        <b><spring:message code="note.auditor"/>:&nbsp;</b><jstl:out value="${note.auditor.name}" />
        <br/>
        
        
        

        <input type="button" name="cancel" value="<spring:message code="note.cancel" />"
                onclick="javascript: relativeRedir('${cancelURI}');" />