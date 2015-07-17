
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style.css" rel="stylesheet" type="text/css" />
        <title>JSP Page</title>
    </head>
    <body>
         <%@include file="header.jsp"%>
        <div>
            <table>
                <thead>
                    <tr>
                        <th>Contact ID</th>
                        
                        <th>Email address</th>
                        
                        <th>Email Type</th>
                        
                    </tr>
                </thead>
                <tbody>
                    
                    <c:forEach var="email" items="${emailList}">
                        <tr>
                            <td><c:out value="${email.contid}"/></td>
                            <td><c:out value="${email.address}"/></td>
                            <td><c:out value="${email.type}"/></td>
                        </tr>
                    </c:forEach>
                   
                    
                    
                </tbody>
            </table>
        </div>
    </body>
</html>
