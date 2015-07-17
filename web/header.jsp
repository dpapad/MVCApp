<%-- 
    Document   : header
    Created on : Jul 7, 2015, 3:42:41 PM
    Author     : dpa
--%>

<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body>
    <center>
        <div id="mystyle" style="border: none;">
            <h1>Contacts Web Application</h1>
            <p><b>MVC app with JSP/Servlets/MySQL</b><br/>
                <%=new Date()%></br>  </br>
                <a href="contAddNew.jsp">Add New Contact</a> &NegativeThickSpace; |
            <form action="GetAllContactsServlet">View Contact
                <input type="submit" value="View Contacts" />
            </form>>
            
            </p>
        </div>
    </center>
</body>
</html>
