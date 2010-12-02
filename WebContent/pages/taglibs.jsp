<%--
    This page used by all screen including layout to include taglibs definition
    @Author : Reno Natalino
--%>

<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="prefix" value="${actionBean.class.name}"/>
<c:set var="actionPrefix" value="com.app.kmsystem.action"/>
<c:set var="actionPath" value="${fn:replace(fn:replace(fn:replace(prefix, actionPrefix, ''), '.', '/'), 'ActionBean', '.action')}"/>
<c:set var="actionPathAjax" value="${contextPath}${actionPath}"/>
<c:set var="pageId" value="${fn:replace(contextPath,'/','')}${fn:replace(fn:replace(actionPath,'.action',''), '/', '-')}"/>
