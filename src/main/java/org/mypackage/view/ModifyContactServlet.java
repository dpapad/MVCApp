package org.mypackage.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mypackage.controller.ModifyContactController;
import org.mypackage.model.Contact;


/**
 *
 * @author dpa
 */
@WebServlet(name = "ModifyContactServlet", urlPatterns = {"/modifyContact"})
public class ModifyContactServlet extends HttpServlet {

    public ModifyContactController modifyContactController = new ModifyContactController();
    
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        
        String contactId = request.getParameter("contactId");
        String fullName = request.getParameter("fullname");
        String nickname = request.getParameter("nickname");
        String note = request.getParameter("notes");
        
        Contact contact = modifyContactController.modifyContact(contactId, fullName, nickname, note);

        request.setAttribute("contact", contact);
        
        String redirectUrl = this.getServletContext().getContextPath() + "/contacts/" + contact.getId();
        response.sendRedirect(redirectUrl);           

    }
    
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
         
        String id = request.getParameter("contactId");
        
        Contact contact = modifyContactController.retrieveContact(id);
        
        request.setAttribute("contact", contact);
       
        request.getRequestDispatcher("/modifyContact.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processGetRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processPostRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
