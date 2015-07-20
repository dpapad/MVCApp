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
import org.mypackage.dal.RepositoryFactory;
import org.mypackage.model.Email;

/**
 *
 * @author dpa
 */
public class ViewContactServlet extends HttpServlet {
    
    private ContactRepository contactRepository;
    
    public ViewContactServlet() {
        this(ApplicationDependencies.REPOSITORY_FACTORY);
    }
    
    public ViewContactServlet(RepositoryFactory repositoryFactory) {
        this.contactRepository = repositoryFactory.createContactRepository();
    }
    
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("contactId"));
        
        try{            
            request.setAttribute("contact", contactRepository.getContactById(id));
            List<Email> list = contactRepository.getAllEmailsByContactId(id);
            request.setAttribute("emailList", list);
            request.getRequestDispatcher("/viewContact.jsp").forward(request, response);
        } catch (DalException ex) {
            
        }
        
    }
    
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("viewContact.jsp").forward(request, response);
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
