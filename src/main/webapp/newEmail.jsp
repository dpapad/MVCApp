<%@include file="jsp_includes/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <%@include file="jsp_includes/head.jsp"%>
    </head>
    <body>
        <%@include file="jsp_includes/global_menu.jsp" %>

        <div id="mystyle" class="myform">
            <form id="form" name="form"  method="post" action="${pageContext.request.contextPath}/contacts/${contactId}/modify/new_email_submitted">
                <h1>Email</h1>
                <p>To add a new Email enter following information</p>
                <br/>
                <br/>
                <div style="color: #FF0000;">${errorMessage}</div>

                <label>Address<span class="small">Enter Email Address</span></label>
                <input type="email" name="address" id="address" required/>

                <label>Category</label>
                <select name="category" id="category">
                    <c:forEach var="mailCategory" items="${mailCategories}">
                        <option value="${mailCategory.byteValue}">
                            <c:out value="${mailCategory.name()}" />
                        </option>
                    </c:forEach>                    
                </select>

                <input type="hidden" name="contactId" id="contactId" value="${param.contactId}" />

                <button type="submit">Add New Email</button>
                <div class="spacer"></div>
            </form>
        </div>
    </body>
</html>
