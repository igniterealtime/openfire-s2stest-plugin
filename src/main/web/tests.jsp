<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page
        import="org.jivesoftware.openfire.XMPPServer,
           org.igniterealtime.openfire.s2sconformancetest.S2STestPlugin"
%>

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

    <c:if test="${testMode}">
        <admin:contentBox title="Test Results">
            <c:forEach var="result" items="${results}">
                <c:out value="${result}"/><br/>
            </c:forEach>
        </admin:contentBox>
    </c:if>

    <admin:contentBox title="Test Settings">
        <p>Edit these settings in <a href="/server-properties.jsp?searchPlugin=S2S+Conformance+Test">System Properties</a>.</p>

        <table>
            <tr><fmt</tr>
        </table>

    </admin:contentBox>
</body>
</html>
