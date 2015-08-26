package org.mypackage.view;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.application.errors.ResourceNotFoundException;
import org.mypackage.controller.ContactsController;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;
import org.mypackage.model.Email;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public final class ContactsServlet extends HttpServlet {

    // Create a logger for error logging
    private final static Logger logger = Logger.getLogger(ContactsServlet.class);

    @Autowired
    private ContactsController contactsController;

//    public ContactsServlet() {
//        this(ApplicationDependencies.CONTROLLER_FACTORY.createContactsController());
//    }
//    public ContactsServlet(ContactsController contactsController) {
//        this.contactsController = contactsController;
//    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String contactId = (String) request.getAttribute("contactId");
        try {
            if (contactId == null) {
                List<Contact> list = contactsController.retrieveAllContacts();
                request.setAttribute("contactsList", list);
                request.getRequestDispatcher("/contacts.jsp").forward(request, response);
            } else {
                Contact contact = contactsController.getContact(contactId);
                if (contact != null) {
                    request.setAttribute("contact", contact);
                    List<Email> list = contactsController.retrieveAllEmails(contactId);
                    request.setAttribute("emailList", list);
                    request.getRequestDispatcher("/viewContact.jsp").forward(request, response);
                } else {
                    throw new ResourceNotFoundException((Object) contactId);
                }
            }
        } catch (DalException ex) {
            logger.error("A database error occured.", ex);
            request.setAttribute("errorCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute("errorMessage", "There was a an internal database error.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        } catch (MalformedIdentifierException ex) {
            request.setAttribute("errorCode", HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("errorMessage", "An error occured because of a malformed id. Please use only numeric values.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        } catch (ResourceNotFoundException ex) {
            request.setAttribute("errorCode", HttpServletResponse.SC_NOT_FOUND);
            request.setAttribute("errorMessage", "Requested contact does not exist.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    public ContactsController getContactsController() {
        return contactsController;
    }

    public void setContactsController(ContactsController contactsController) {
        this.contactsController = contactsController;
    }

}
