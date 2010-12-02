<%@ include file="/pages/taglibs.jsp"%>
<fmt:message key="application.title.login" var="pageTitle"/>
<s:layout-render name="/pages/layout/layout-nosidebar.jsp" title="${pageTitle}">
    <s:layout-component name="contentright">
        <div class="ui-content-right">
            <s:form beanclass="com.app.kmsystem.action.LoginActionBean" id="formLoginAction">
            </s:form>
        </div>
        <script type="text/javascript">
            G.Login = function() {
                return {
                    init: function() {

                    }
                };
            }();
            G.InitModule(G.Login.init);
        </script>
    </s:layout-component>
</s:layout-render>