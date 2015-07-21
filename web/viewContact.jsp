<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:url value="/style.css"/>" rel="stylesheet" type="text/css"/>
        <title>Contact Details</title>
    </head>
    <body>
        <%@include file="header.jsp"%>
        
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
                        <td><c:out value="${contact.fullname}"/></td>  
                        <td><c:out value="${contact.nickname}" /></td>
                        <td><c:out value="${contact.notes}"/></td>
                          
                        <td style="border: none;">                            
                            <div>
                                <a href="<c:url value="/addEmail?contactId=${contact.contactId}" />">Add New Email</a>
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
                            <td><c:out value="${email.type}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
