package org.mypackage.view;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mypackage.application.ApplicationDependencies;
import org.mypackage.controller.ContactsController;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContactsController contactsController = new ContactsController();
        
        String contactId = (String) request.getAttribute("contactId");
        
        if (contactId == null) {
            
            //this.viewContactList(request, response);
            
            List<Contact> list = contactsController.retrieveAllContacts();
            request.setAttribute("contactsList", list);
            request.getRequestDispatcher("/contacts.jsp").forward(request, response);
        }
        else {
            
            //this.viewContactDetails(request, response, contactId);
            
            Contact contact = contactsController.getContact(contactId);
            request.setAttribute("contact", contact);
            List<Email> list = contactsController.retrieveAllEmails(contactId);
            request.setAttribute("emailList", list);
            request.getRequestDispatcher("/viewContact.jsp").forward(request, response);
        }
    }
    
//    private void viewContactList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
//            List<Contact> list = this.contactRepository.getAllContacts();
//            request.setAttribute("contactsList", list);
//            request.getRequestDispatcher("/contacts.jsp").forward(request, response);
//        } 
//        catch (DalException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//    
//    private void viewContactDetails(HttpServletRequest request, HttpServletResponse response, String contactId) throws ServletException, IOException {
//        int id = Integer.parseInt(contactId);
//        
//        try{            
//            request.setAttribute("contact", contactRepository.getContactById(id));
//            List<Email> list = contactRepository.getAllEmailsByContactId(id);
//            request.setAttribute("emailList", list);
//            request.getRequestDispatcher("/viewContact.jsp").forward(request, response);
//        } 
//        catch (DalException ex) {
//            
//        }
//    }
}
