<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style.css" rel="stylesheet" type="text/css"/>
        <title>Contact Details</title>
    </head>
    <body>
        <%@include file="header.jsp"%>
        
        <div>
            <table>
                <thead>
                    <tr>
                        <th>Contact ID</th>                        
                        <th>Full Name</th>
                        <th>Nickname</th>
                        <th>Notes</th>
                    </tr>
                </thead>
                <tbody>contact
                    <c:set var="contact" value="${contact}"/>
                    <tr>
                        <td><c:out value="${contact.contid}"/></td>
                        <td><c:out value="${contact.fullname}"/></td>  
                        <td><c:out value="${contact.nickname}" /></td>
                        <td><c:out value="${contact.notes}"/></td>
                        
                        
                        <td style="border: none;">
                            
                            <div>
                                <form method="post" action="emailAddNew.jsp">
                                    <input type="hidden" id="emailContId" name="emailContId" value="${contact.contid}"/> 
                                    <input type="submit" value="Add Email"/> 
                                </form>
                            </div>
                                    
                            <div>
                                <form method="post" action="GetAllEmailsServlet">
                                    <input type="hidden" id="contid" name="contid" value="${contact.contid}"/> 
                                    <input type="submit" value="Show Emails"/> 
                                </form>
                            </div>
                            <div>
                                <form method="post" action="UpdateContactServlet">
                                    <input type="hidden" id="updateId" name="updateId" value="${contact.contid}"/> 
                                    <input type="submit" value="Modify"/> 
                                </form>
                            </div>
                        </td>
                        <td style="border: none;">
                            <div>
                                <form method="post" action="DeleteContactServlet">
                                    <input type="hidden" id="delId" name="delId" value="${contact.contid}"/> 
                                    <input type="submit" value="Delete"/> 
                                </form>
                            </div>
                        </td>
                    </tr>
                
                    
                </tbody>
            </table>
        </div>
    </body>
</html>
