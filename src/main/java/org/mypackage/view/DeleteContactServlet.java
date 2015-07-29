package org.mypackage.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mypackage.application.ApplicationDependencies;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;


/**
 *
 * @author dpa
 */
@WebServlet(name = "DeleteContactServlet", urlPatterns = {"/deleteContact"})
public class DeleteContactServlet extends HttpServlet {
    
    private ContactRepository contactRepository;

    public DeleteContactServlet() {
        this(ApplicationDependencies.REPOSITORY_FACTORY.createContactRepository());
    }

    public DeleteContactServlet(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("contactId"));
        
        try{
            this.contactRepository.deleteContactById(id);
            String redirectUrl = this.getServletContext().getContextPath() + "/contacts";
            response.sendRedirect(redirectUrl);
        } catch  (DalException ex) {
            
        }
    }
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
