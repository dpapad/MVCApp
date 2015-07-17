<%@page import="org.mypackage.dal.mysql.MySqlConnectionProvider"%>
<%@page import="org.mypackage.dal.mysql.MysqlContactRepository"%>
<%@page import="org.mypackage.model.Contact"%>
<%@page import="org.mypackage.model.Email"%>

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
        <%
            int id = Integer.parseInt(request.getParameter("emailContId"));
            MysqlContactRepository contactRepository = new MysqlContactRepository(new MySqlConnectionProvider());
            Contact c = contactRepository.getContactById(id);
            
        %>
        
        <form method="post" action="AddNewEmailServlet">
            <div id="mystyle" class="myform">
                <form id="form" name="form"  method="post">
                    <h1>Email</h1>
                    <p>To add a new Email enter following information</p>
                    
                    
                    <label>Address<span class="small">Enter Email Address</span></label>
                    <input type="text" name="address" id="address" />
                    <!--
                    <label>Type<span class="small">Enter Email Type</span></label>
                    <input type="text" name="email_type" id="email_type" />
                    -->    
                    <input type="hidden" name="contid" id="contid" value="<%=String.valueOf(c.getContid())%>" />
                    
                    <label>Type</label>
                    <select name="email_type" id="email_type">
                        <option id="personal" name="personal" value="Personal">Personal</option>
                        <option id="work" name="work" value="Work">Work</option>
                    </select>
                    
                    <button type="submit"  action="contView.jsp">Add New Email</button>
                    <div class="spacer"></div>
                </form>
            </div>
        </form>
    </body>
</html>
