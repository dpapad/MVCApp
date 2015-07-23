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
import org.mypackage.dal.RepositoryFactory;
import org.mypackage.model.Contact;

/**
 *
 * @author dpa
 */
@WebServlet(name = "NewContactServlet", urlPatterns = {"/newContact"})
public class NewContactServlet extends HttpServlet {

    private ContactRepository contactRepository;
    
    public NewContactServlet() {
        this(ApplicationDependencies.REPOSITORY_FACTORY);
    }
    
    public NewContactServlet(RepositoryFactory repositoryFactory) {
        this.contactRepository = repositoryFactory.createContactRepository();
    }
    
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("newContact.jsp").forward(request, response);
    }
    
    
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Contact cont = new Contact();
        cont.setFullName(request.getParameter("fullname"));
        cont.setNickname(request.getParameter("nickname"));
        cont.setNotes(request.getParameter("notes"));
    
        try {
            int contactId = this.contactRepository.addContact(cont);
            String redirectUrl = this.getServletContext().getContextPath() + "/contacts/" + contactId;
            response.sendRedirect(redirectUrl);           
        } catch (DalException ex) {
            
        }
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
    }// </editor-fold>

}
