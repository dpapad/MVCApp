package org.mypackage.view;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mypackage.controller.ContactsController;
import org.mypackage.model.Contact;
import org.mypackage.model.Email;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public final class ContactsServlet extends HttpServlet {

   
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContactsController contactsController = new ContactsController();
        
        String contactId = (String) request.getAttribute("contactId");
        
        if (contactId == null) {
            
            List<Contact> list = contactsController.retrieveAllContacts();
            request.setAttribute("contactsList", list);
            request.getRequestDispatcher("/contacts.jsp").forward(request, response);
        }
        else {
            
            Contact contact = contactsController.getContact(contactId);
            request.setAttribute("contact", contact);
            List<Email> list = contactsController.retrieveAllEmails(contactId);
            request.setAttribute("emailList", list);
            request.getRequestDispatcher("/viewContact.jsp").forward(request, response);
        }
    }
    
}
