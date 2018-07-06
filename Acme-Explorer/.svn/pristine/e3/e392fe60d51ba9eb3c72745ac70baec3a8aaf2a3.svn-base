<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="category/admin/edit.do" modelAttribute="category">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<form:hidden path="trips"/>
	<form:hidden path="childrenCategories"/>
	
	<spring:message var="categoryParentCategory" code="category.parentCategory"/>
	<b><form:label path="parentCategory"/>${categoryParentCategory}:&nbsp;</b>
	<form:select path="parentCategory">
		<form:options items="${categories}" itemLabel="name" itemValue="id"/>
	</form:select>
	<form:errors path="parentCategory" cssClass="error"/>
	<br/>
	
	<spring:message var="categoryName" code="category.name"/>
	<b><form:label path="name"/>${categoryName}:&nbsp;</b>
	<form:input path="name"/>
	<form:errors path="name" cssClass="error"/>
	<br/>
	
	<spring:message var="categorySave" code="category.save"/>
	<input type="submit" name="save" value="${categorySave}"/>
	<jstl:if test="${category.id != 0}"> 
		<spring:message var="categoryDelete" code="category.delete"/>
		<input type="submit" name="delete" value="${categoryDelete}"/>
	</jstl:if>
	
	<spring:message var="categoryCancel" code="category.cancel"/>
	<input type="button" name="cancel" value="${categoryCancel}" onclick="javascript:relativeRedir('category/list.do')"/>
</form:form>
