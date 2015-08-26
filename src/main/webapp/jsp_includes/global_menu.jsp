<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="mystyle" style="border: none;">
    <h1>Contacts Web Application</h1>
    <p>
        <b>MVC app with JSP/Servlets/MySQL</b>
        <br/>
        <br/>
        <a href="<c:url value="/contacts" />">View all contacts</a> | <a href="<c:url value="/contacts/new" />">Add New Contact</a>
    </p>
</div>