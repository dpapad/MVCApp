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
        request.setAttribute("mailCategories", Email.Category.values());
        request.getRequestDispatcher("/newEmail.jsp").forward(request, response);
    }

    //Fixed    
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Email email = new Email();
        email.setAddress(request.getParameter("address"));
        byte categoryValue = Byte.parseByte(request.getParameter("category"));
        email.setCategory(Email.Category.forValue(categoryValue));
        int contid = Integer.parseInt(request.getParameter("contactId"));
        email.setfContactId(contid);
        
        try {
            if (!(this.contactRepository.checkIfEmailExists(email))) {
                this.contactRepository.addEmail(email);
                String redirectUrl = this.getServletContext().getContextPath()  +"/contacts/" + email.getfContactId();
                response.sendRedirect(redirectUrl); 
            }                                      
            else {                
                request.getSession().setAttribute("errorMessage", "This address already exists. Please enter a new one.");
              
                response.sendRedirect(request.getHeader("Referer"));                
            }
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
