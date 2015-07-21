package org.mypackage.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mypackage.application.ApplicationDependencies;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;


/**
 *
 * @author dpa
 */
@WebServlet(name = "ModifyContactServlet", urlPatterns = {"/modifyContact"})
public class ModifyContactServlet extends HttpServlet {

    private ContactRepository contactRepository;

    public ModifyContactServlet() {
        this(ApplicationDependencies.REPOSITORY_FACTORY.createContactRepository());
    }

    public ModifyContactServlet(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
    
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Contact contact = new Contact();
        contact.setId(Integer.parseInt(request.getParameter("contactId")));
        contact.setFullName(request.getParameter("fullname"));
        contact.setNickname(request.getParameter("nickname"));
        contact.setNotes(request.getParameter("notes"));
        
        request.setAttribute("contact", contact);
        
        try {
            this.contactRepository.updateContact(contact);
            String redirectUrl = this.getServletContext().getContextPath() + "/contacts/" + contact.getId();
            response.sendRedirect(redirectUrl);           
        } catch (DalException ex) {
            
        }
    }
    
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        Contact contact = new Contact();
        Integer id = Integer.parseInt(request.getParameter("contactId"));
        
        try {
            contact = contactRepository.getContactById(id);
        } catch (DalException ex) {
            
        }      
                
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
