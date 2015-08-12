package org.mypackage.view;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mypackage.application.ApplicationDependencies;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.ContactsController;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;
import org.mypackage.model.Email;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public final class ContactsServlet extends HttpServlet {

    private ContactsController contactsController;

    public ContactsServlet() {
        this(ApplicationDependencies.CONTROLLER_FACTORY.createContactsController());
    }

    public ContactsServlet(ContactsController contactsController) {
        this.contactsController = contactsController;
    }

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
                request.setAttribute("contact", contact);
                List<Email> list = contactsController.retrieveAllEmails(contactId);
                request.setAttribute("emailList", list);
                request.getRequestDispatcher("/viewContact.jsp").forward(request, response);
            }
        } catch (DalException ex) {
            request.setAttribute("errorCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute("errorMessage", "There was a an internal database error.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        } catch (MalformedIdentifierException ex) {
            request.setAttribute("errorCode", HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("errorMessage", "An error occured because of a malformed id. Please use only numeric values.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }
}
