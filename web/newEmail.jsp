<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        
        <div id="mystyle" class="myform">
            <form id="form" name="form"  method="post" action="addEmail">
                <h1>Email</h1>
                <p>To add a new Email enter following information</p>


                <label>Address<span class="small">Enter Email Address</span></label>
                <input type="text" name="address" id="address" />

                <label>Type</label>
                <select name="email_type" id="email_type">
                    <option id="personal" name="personal" value="Personal">Personal</option>
                    <option id="work" name="work" value="Work">Work</option>
                </select>

                <input type="hidden" name="contactId" id="contactId" value="${param.contactId}" />
                
                <button type="submit">Add New Email</button>
                <div class="spacer"></div>
            </form>
        </div>
    </body>
</html>
