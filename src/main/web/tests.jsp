<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page
        import="org.jivesoftware.openfire.XMPPServer,
           org.igniterealtime.openfire.s2sconformancetest.S2STestPlugin"
%>
<%@ page import="java.util.Map" %>
<%@ page import="org.igniterealtime.openfire.s2sconformancetest.S2STestResultRun" %>

<%@ taglib uri="admin" prefix="admin" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
    // Set up some shared vocabulary
    String testModeLabel = "runTests";
    pageContext.setAttribute("testModeLabel", testModeLabel);
%>

<%
    boolean testMode = request.getParameter(testModeLabel) != null;

    S2STestPlugin plugin = (S2STestPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("s2sconformancetest");
    if(testMode){
        S2STestResultRun results = null;
        try {
            results = plugin.runTests();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        pageContext.setAttribute("results", results);
    }

    pageContext.setAttribute("testMode", testMode);
%>

<html>
<head>
    <title><fmt:message key="s2sconformancetest.title" /></title>
    <meta name="pageID" content="tests"/>
</head>
<body>

    <admin:FlashMessage/> <%-- In case of CSRF errors --%>

    <admin:contentBox title="Run Tests">
        <form action="tests.jsp" method="post">
            <input type="hidden" name="csrf" value="<c:out value="${csrf}"/>" >
            <input type="submit" name="<c:out value="${testModeLabel}"/>" value="Run Tests"/>
        </form>
    </admin:contentBox>


    <% if(testMode){

        %>
    <admin:contentBox title="Test Results">
        <table>
            <c:forEach items="${results.getSuccessfulResults()}" var="result">
                <tr>
                    <td style="vertical-align: top">
                        <c:out value="${result.getDomain()}"/>
                    </td>
                    <td style="font-family: monospace;">
                        Expected: Success
                    </td>
                    <td style="font-family: monospace;">
                        Actual: <c:out value="${result.getResults().get('status')}"/>
                    </td>
                </tr>
            </c:forEach>
            <c:forEach items="${results.getSuccessfulResults()}" var="result">
                <tr>
                    <td style="vertical-align: top">
                        <c:out value="${result.getDomain()}"/>
                    </td>
                    <td style="font-family: monospace;">
                        Expected: Failure
                    </td>
                    <td style="font-family: monospace;">
                        Actual: <c:out value="${result.getResults().get('status')}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </admin:contentBox>
    <% } %>



    <admin:contentBox title="Test Settings">
        <p>Edit these settings in <a href="/server-properties.jsp?searchPlugin=S2S+Conformance+Test">System Properties</a>.</p>

        <table>
            <tr><fmt</tr>
        </table>

    </admin:contentBox>
</body>
</html>
