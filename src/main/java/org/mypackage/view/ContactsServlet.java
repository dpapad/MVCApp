package org.mypackage.view;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mypackage.application.ApplicationDependencies;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;
import org.mypackage.model.Email;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public final class ContactsServlet extends HttpServlet {

    private ContactRepository contactRepository;

    public ContactsServlet() {
        this(ApplicationDependencies.REPOSITORY_FACTORY.createContactRepository());
    }

    public ContactsServlet(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contactId = (String) req.getAttribute("contactId");
        
        if (contactId == null) {
            this.viewContactList(req, resp);
        }
        else {
            this.viewContactDetails(req, resp, contactId);
        }
    }
    
    private void viewContactList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Contact> list = this.contactRepository.getAllContacts();
            req.setAttribute("contactsList", list);
            req.getRequestDispatcher("/contacts.jsp").forward(req, resp);
        } 
        catch (DalException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private void viewContactDetails(HttpServletRequest request, HttpServletResponse response, String contactId) throws ServletException, IOException {
        int id = Integer.parseInt(contactId);
        
        try{            
            request.setAttribute("contact", contactRepository.getContactById(id));
            List<Email> list = contactRepository.getAllEmailsByContactId(id);
            request.setAttribute("emailList", list);
            request.getRequestDispatcher("/viewContact.jsp").forward(request, response);
        } 
        catch (DalException ex) {
            
        }
    }
}
