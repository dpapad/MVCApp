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
            <form id="form" name="form" action="modifyContact" method="post">
                
                
                <h1>Update Contact ID:<c:out value="${contact.contactId}"/></h1>
                <p>Modify the following information to update employee ID:<c:out value="${contact.contactId}"/></p>
                
                
                <label><input type="hidden" name="contactId" id="contactId" value="${contact.contactId}"/><span class="small"></span></label>                    
                
                <label>Full Name<span class="small">Enter full name</span></label>
                <input type="text" name="fullname" id="fullname" value="${contact.fullname}"/>
                
                <label>Nickname<span class="small">Enter nickname</span></label>
                <input type="text" name="nickname" id="nickname" value="${contact.nickname}"/>
                
                <label>Notes<span class="small">Enter notes</span></label>
                <input type="text" name="notes" id="notes" value="${contact.notes}"/>
                
                <button type="submit">Modify Contact</button>
                
                <div class="spacer"></div>
            </form>                                
        </div>   
    </body>
</html>

