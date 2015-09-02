<%@include file="jsp_includes/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contact Details</title>

        <%@include file="jsp_includes/head.jsp"%>
    </head>
    <body>
        <%@include file="jsp_includes/global_menu.jsp" %>

        <div>
            <table>
                <thead>
                    <tr>
                        <th>Full Name</th>
                        <th>Nickname</th>
                        <th>Notes</th>
                    </tr>
                </thead>
                <tbody>contact
                    <c:set var="contact" value="${contact}"/>
                    <tr>
                        <td><c:out value="${contact.fullName}"/></td>  
                        <td><c:out value="${contact.nickname}" /></td>
                        <td><c:out value="${contact.notes}"/></td>

                        <td style="border: none;">                            
                            <div>
                                <a href="<c:url value="/contacts/${contact.id}/modify" />">Modify</a>
<!--                                <a href="<c:url value="/contacts/${contact.id}/new_email" />">Add New Email</a>-->
                            </div>                                    
                        </td>
                    </tr>                   
                </tbody>
            </table>                                     
            <table>
                <thead>
                    <tr>
                        <th>Email Address</th>
                        <th>Email Type</th>
                    </tr>
                </thead>
                <tbody>e-mails
                    <c:forEach var="email" items="${emailList}">

                        <tr>                    
                            <td><c:out value="${email.address}"/></td>
                            <td><c:out value="${email.category.name()}"/></td>
<!--                            <td>
                                <div>
                                    <a href="<c:url value="/contacts/${contactId}/delete_email/${email.id}"/>">Delete</a>                                  
                                </div>
                            </td>-->
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
