<%@include file="jsp_includes/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Email</title>

        <%@include file="jsp_includes/head.jsp"%>
    </head>
    <body>
        <%@include file="jsp_includes/global_menu.jsp" %>

        <div id="mystyle" class="myform">
            <form id="form" name="form"  method="post" action="${pageContext.request.contextPath}/contacts/${email.getfContactId()}/modify/update_email/${email.id}/email_updated">
                <h1>Email: ${email.address}</h1>
                <p>To update the specific Email edit the following information</p>
                <br/>
                <br/>
                <div style="color: #FF0000;">${errorMessage}</div>

                <label>Address<span class="small">Modify Email Address</span></label>
                <input required type="email" name="address" id="address" value="${email.address}" />

                <label>Category</label>
                <select name="category" id="category">
                    <c:forEach var="mailCategory" items="${mailCategories}">
                        <option value="${mailCategory.byteValue}" ${email.category.byteValue == mailCategory.byteValue ? 'selected': ''}>
                            ${mailCategory.name()}
                        </option>
                    </c:forEach>                    
                </select>

                <input type="hidden" name="contactId" id="contactId" value="${param.contactId}" />

                <button type="submit">Update Email</button>
                <div class="spacer"></div>
            </form>
        </div>
    </body>
</html>
