<%@ include file="/pages/taglibs.jsp"%>
<fmt:message key="application.title.main" var="pageTitle"/>
<s:layout-render name="/pages/layout/layout-nosidebar.jsp" title="${pageTitle}">
    <s:layout-component name="contentright">
        <div class="ui-content-right">
            <s:form beanclass="com.app.kmsystem.action.MainActionBean" id="formMainAction">
                
            </s:form>
        </div>
        <script type="text/javascript">
            G.Main = function() {
                return {
                    init: function() {

                    }
                };
            }();
            G.InitModule(G.Main.init);
        </script>
    </s:layout-component>
</s:layout-render>