<%@ include file="/pages/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<s:layout-definition>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml" lang="en">
        <head>
            <meta http-equiv="content-type" content="text/html; charset=utf-8" />
            <meta http-equiv="cache-control" content="no-cache" />
            <meta http-equiv="Pragma" content="no-cache" />
            <meta http-equiv="Expires" content="0" />
            <meta name="copyright" content="2010, Reno Natalino" />
            <meta name="author" content="Reno Natalino" />
            <meta name="distribution" content="internal" />
            <meta name="description" content="School Information System (sisy)" />
            <meta name="keywords" content="information system, school information system, sisy, kindergarden" />
            <link rel="stylesheet" type="text/css" media="screen,projection,print" href="${contextPath}/css/ui-kmsystem-layout.css" />
            <link rel="stylesheet" type="text/css" media="screen,projection,print" href="${contextPath}/js/jquery/plugin/jqueryui/css/redmond/jquery-ui-1.8.6.custom.css" />
            <link rel="stylesheet" type="text/css" media="screen,projection,print" href="${contextPath}/css/ui.jqgrid.css" />
            <script type="text/javascript" src="${contextPath}/js/jquery/jquery-1.4.4.min.js"></script>
            <script type="text/javascript" src="${contextPath}/js/jquery/plugin/jqueryui/jquery-ui-1.8.6.custom.min.js"></script>
            <script type="text/javascript" src="${contextPath}/js/jquery/plugin/localization/grid.locale-en.js"></script>
            <script type="text/javascript" src="${contextPath}/js/jquery/plugin/jqgrid/jquery.jqGrid.min.js"></script>
            <script type="text/javascript" src="${contextPath}/js/jquery/plugin/jquery.pngFix.js"></script>
            <script type="text/javascript" src="${contextPath}/js/jquery/plugin/jquery.easing.1.3.js"></script>
            <script type="text/javascript" src="${contextPath}/js/jquery/plugin/jquery.jBreadCrumb.1.1.js"></script>
            <script type="text/javascript" src="${contextPath}/js/jquery/plugin/validation/jquery.validate.js"></script>
            <script type="text/javascript" src="${contextPath}/js/jquery/plugin/validation/additional-methods.js"></script>
            <script type="text/javascript" src="${contextPath}/js/jquery/plugin/jquery.simplemodal-1.3.5.min.js"></script>
            <script type="text/javascript" src="${contextPath}/js/jquery/plugin/jquery.lestari.common.js"></script>
            <script type="text/javascript" src="${contextPath}/js/app.js"></script>
            <script type="text/javascript" src="${contextPath}/js/jquery/plugin/localization/message_en.js"></script>
            <link rel="shortcut icon" type="image/x-icon" href="${contextPath}/img/favicon.ico" />
            <title><fmt:message key="application.title"/> | ${title}</title>
        </head>
        <body id="body">
            <div class="ui-page-container">
                <div class="ui-header">
                    <s:layout-component name="header">
                        <jsp:include page="/pages/layout/header.jsp"/>
                    </s:layout-component>
                </div>
                <s:layout-component name="breadcumb">
                    <jsp:include page="/pages/layout/breadcumb.jsp"/>
                </s:layout-component>
                <div class="ui-container-form">
                    <s:layout-component name="contentright"/>
                    <div class="clearer"></div>
                </div>
                <s:layout-component name="footer">
                    <jsp:include page="/pages/layout/footer.jsp"/>
                </s:layout-component>
            </div>
        </body>
    </html>
</s:layout-definition>