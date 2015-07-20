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
import org.mypackage.model.Email;

/**
 *
 * @author dpa
 */
@WebServlet(name = "NewEmailServlet", urlPatterns = {"/newEmail"})
public class NewEmailServlet extends HttpServlet {
    
    private ContactRepository contactRepository;
    
    public NewEmailServlet() {
        this(ApplicationDependencies.REPOSITORY_FACTORY);
    }
    
    public NewEmailServlet(RepositoryFactory repositoryFactory) {
        this.contactRepository = repositoryFactory.createContactRepository();
    }
    
    
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        request.getRequestDispatcher("/newEmail.jsp").forward(request, response);
    }

    
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Email email = new Email();
        email.setAddress(request.getParameter("address"));
        email.setType(request.getParameter(("email_type")));
        email.setContactId(Integer.parseInt(request.getParameter("contactId")));
        
        try {
            
            this.contactRepository.addEmail(email);
            String redirectUrl = this.getServletContext().getContextPath()  +"/viewContact?contactId=" + email.getContactId();
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
