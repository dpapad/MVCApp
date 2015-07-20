package org.mypackage.controller;

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

/**
 *
 * @author dpa
 */
public class GetAllContactsServlet extends HttpServlet {

    private ContactRepository contactRepository;

    public GetAllContactsServlet() {
        this(ApplicationDependencies.REPOSITORY_FACTORY.createContactRepository());
    }

    public GetAllContactsServlet(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Contact> list = this.contactRepository.getAllContacts();
            request.setAttribute("contactsList", list);
            request.getRequestDispatcher("/contacts.jsp").forward(request, response);
        } 
        catch (DalException ex) {
            
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
