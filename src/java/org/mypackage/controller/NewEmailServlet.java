
package org.mypackage.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private String excMessage;
    
    
    private ContactRepository contactRepository;
    
    public NewEmailServlet() {
        this(ApplicationDependencies.REPOSITORY_FACTORY);
    }
    
    public NewEmailServlet(RepositoryFactory repositoryFactory) {
        this.contactRepository = repositoryFactory.createContactRepository();
    }
    
    
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        request.getRequestDispatcher("newEmail.jsp").forward(request, response);
    }

    
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Email email = new Email();
        //email.setEmailid(Integer.parseInt(request.getParameter("emailid")));
        email.setAddress(request.getParameter("address"));
        email.setType(request.getParameter(("email_type")));
        email.setContid(Integer.parseInt(request.getParameter("contid")));
        
        try {
            
            this.contactRepository.addEmail(email);
            String redirectUrl = this.getServletContext().getContextPath()  +"/newEmail";
            response.sendRedirect(redirectUrl);
        } catch (DalException ex) {
            excMessage = "Error Adding the email";
            DalException addNewEmailServletExc = new DalException(excMessage, ex);
            
            Logger.getLogger("Error in servlet method processRequest(HttpServletRequest request, HttpServletResponse response) at " +NewEmailServlet.class.getName()).log(Level.SEVERE,"Message returned: " + excMessage, addNewEmailServletExc);
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
