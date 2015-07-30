package org.mypackage.view;

import java.io.IOException;
import java.util.List;
import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;
import static javax.servlet.RequestDispatcher.ERROR_REQUEST_URI;
import static javax.servlet.RequestDispatcher.ERROR_SERVLET_NAME;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mypackage.controller.impl.ContactsControllerImpl;
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
        this.contactsController = new ContactsControllerImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //ContactsController contactsController = new ContactsControllerImpl();

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
            request.setAttribute("exception", ERROR_EXCEPTION);
            request.setAttribute("servlet", ERROR_SERVLET_NAME);
            request.setAttribute("requestUri", ERROR_REQUEST_URI);
            request.setAttribute("errorMessage", ERROR_MESSAGE);

            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

}
