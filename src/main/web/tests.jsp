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
        <div id="test-init">
            <p>This will run a series of tests to check if your server is configured correctly for S2S communication.</p>
            <form action="tests.jsp" method="post">
                <input type="hidden" name="csrf" value="<c:out value="${csrf}"/>" >
                <input type="submit" id="doTests" name="<c:out value="${testModeLabel}"/>" value="Run Tests"/>
            </form>
        </div>
        <div id="test-in-progress" style="display:none">
            <admin:infoBox type="info">Be patient. This could take a few of minutes, depending on your settings.</admin:infoBox>
        </div>
    </admin:contentBox>

    <script>
        const button = document.getElementById('doTests');
        const divToHide = document.getElementById('test-init');
        const divToShow = document.getElementById('test-in-progress');

        function changeContent() {
            divToHide.style.display = 'none';
            divToShow.style.display = 'block';
        }

        button.addEventListener('click', changeContent);
    </script>


    <% if(testMode){%>
        <admin:contentBox title="Test Results">
            <table style="width: 100%">
                <tr>
                    <th>Domain</th>
                    <th>Expected</th>
                    <th>Actual</th>
                    <th>Server Info</th>
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
                        <td>
                            <c:out value="${result.getResults().get('software')}"/>
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
                        <td>
                            <c:out value="${result.getResults().get('software')}"/>
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
