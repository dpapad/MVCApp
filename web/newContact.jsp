<%-- 
    Document   : contAddNew
    Created on : Jul 7, 2015, 3:45:25 PM
    Author     : dpa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style.css" rel="stylesheet" type="text/css"/>
        <title>New Contact</title>
    </head>
    <body>
        <%@include file="header.jsp"%>
        <div id="mystyle" class="myform">
            <form id="form" name="form" action="newContact" method="post">
                <h1>Contact</h1>
                <p>To add new Contact enter following information</p>

                <label>Full Name<span class="small">Enter Full Name</span></label>
                <input type="text" name="fullname" id="fullname" />

                <label>Nickname<span class="small">Enter Nickname</span></label>
                <input type="text" name="nickname" id="nickname" />

                <label>Notes<span class="small">Enter Notes</span></label>
                <input type="text" name="notes" id="notes" />

                <button type="submit">Add New Contact</button>
                <div class="spacer"></div>
            </form>
        </div> 
    </body>
</html>
