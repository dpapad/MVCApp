<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style.css" rel="stylesheet" type="text/css"/>
        <title>Employee Update Page</title>
    </head>
    <body>
        <%@include file="header.jsp"%>
        
        <div id="mystyle" class="myform">
            <form id="form" name="form" action="UpdateContactServlet" method="post">
                <c:set var="contact" value="contact" />
                <h1>Update Contact ID:<c:out value="${contact.contid}"/></h1>
                <p>Modify the following information to update employee ID:<c:out value="${contact.contid}"/></p>
                
                <label><input type="hidden" name="contid" id="contid" value="${contact.contid}"/><span class="small"></span></label>                    
                
                <label>Full Name<span class="small">Enter full name</span></label>
                <input type="text" name="fullname" id="fullname" value="${contact.fullname}"/>
                
                <label>Nickname<span class="small">Enter nickname</span></label>
                <input type="text" name="nickname" id="nickname" value="${contact.nickname}"/>
                
                <label>Notes<span class="small">Enter notes</span></label>
                <input type="text" name="notes" id="notes" value="${contact.notes}"/>
                
                <button type="submit">Update Employee</button>
                
                <div class="spacer"></div>
            </form>
                
                
            </div>
                
            </form>
                
            
        </div>   
    </body>
</html>

