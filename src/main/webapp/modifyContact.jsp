<%@include file="jsp_includes/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee Update Page</title>

        <%@include file="jsp_includes/head.jsp"%>
    </head>
    <body>
        <%@include file="jsp_includes/global_menu.jsp" %>
        <div id="mystyle" class="myform">
            <form id="form" name="form" action="${pageContext.request.contextPath}/contacts/${contact.id}" method="post">

                <h1>Update Contact ID:<c:out value="${contact.id}"/></h1>
                <p>Modify the following information to update contact :<c:out value="${contact.fullName}"/></p>

                <label><input type="hidden" name="contactId" id="contactId" value="${contact.id}"/><span class="small"></span></label>                    

                <label>Full Name<span class="small">Enter full name</span></label>
                <input type="text" name="fullname" id="fullname" value="${contact.fullName}" required/>

                <label>Nickname<span class="small">Enter nickname</span></label>
                <input type="text" name="nickname" id="nickname" value="${contact.nickname}"/>

                <label>Notes<span class="small">Enter notes</span></label>
                <input type="text" name="notes" id="notes" value="${contact.notes}"/>

                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />

                <p>Modify <c:out value="${contact.fullName}"/>'s emails</p>

                <c:forEach var="email" items="${emailsList}">
                    <tr>                    
                        <td>
                            <label>Email address<span class="small">Modify email address</span></label>
                            <input type="text" name="address" id="address" value="${email.address}"/>
                        </td>
                        <td>
                            <label>Category<span class="small">Change category</span></label>
                            <select name="category" id="category">
                                <c:forEach var="mailCategory" items="${mailCategories}">
                                    <option value="${mailCategory.byteValue}" ${email.category.byteValue == mailCategory.byteValue ? 'selected': ''}>
                                        ${mailCategory.name()}
                                    </option>
                                </c:forEach>                    
                            </select>
                        </td>
                        <td>
                            <div>
                                <a href="<c:url value="/contacts/${contact.id}/delete_email/${email.id}"/>">Delete</a>                                  
                            </div>
                        </td>
                    <p></p>
                    </tr>
                </c:forEach>

                <button type="submit">Modify Contact</button>

                <div class="spacer"></div>
            </form>                                
        </div>   
    </body>
</html>

