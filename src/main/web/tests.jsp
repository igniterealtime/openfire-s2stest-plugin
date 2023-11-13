<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page
        import="org.jivesoftware.openfire.XMPPServer,
           org.igniterealtime.openfire.s2sconformancetest.S2STestPlugin"
%>
<%@ page import="java.util.Map" %>
<%@ page import="org.igniterealtime.openfire.s2sconformancetest.S2STestResultRun" %>

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
    <div class="jive-contentBoxHeader">
        <c:out value="Run Tests" />
    </div>
    <div class="jive-contentBox">
        <div class="jive-info">
            <table>
                <tbody>
                <tr>
                    <td class="jive-icon"><img src="/images/info-16x16.gif" alt=""/></td>
                    <td class="jive-icon-label">Be patient. This is going to take a couple of minutes.</td>
                </tr>
                </tbody>
            </table>
        </div>
        <form action="tests.jsp" method="post">
            <input type="hidden" name="csrf" value="<c:out value="${csrf}"/>" >
            <input type="submit" name="<c:out value="${testModeLabel}"/>" value="Run Tests"/>
        </form>
    </div>


    <% if(testMode){

        %>
    <div class="jive-contentBoxHeader">
        <c:out value="Test Results" />
    </div>
    <div class="jive-contentBox">
        <table style="width: 100%">
            <tr>
                <th>Domain</th>
                <th>Expected</th>
                <th>Actual</th>
            </tr>
            <c:forEach items="${results.getSuccessfulResults()}" var="result">
                <tr>
                    <td style="vertical-align: top">
                        <c:out value="${result.getDomain()}"/>
                    </td>
                    <td style="font-family: monospace;">
                        Success
                    </td>
                    <td style="font-family: monospace;">
                        <c:out value="${result.getResults().get('status')}"/>
                    </td>
                </tr>
            </c:forEach>
            <c:forEach items="${results.getUnsuccessfulResults()}" var="result">
                <tr>
                    <td style="vertical-align: top">
                        <c:out value="${result.getDomain()}"/>
                    </td>
                    <td style="font-family: monospace;">
                        Failure
                    </td>
                    <td style="font-family: monospace;">
                        <c:out value="${result.getResults().get('status')}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <% } %>



    <div class="jive-contentBoxHeader">
        <c:out value="Test Settings" />
    </div>
    <div class="jive-contentBox">
        <p>Edit these settings in <a href="/server-properties.jsp?searchPlugin=S2S+Conformance+Test">System Properties</a>.</p>
    </div>
</body>
</html>
