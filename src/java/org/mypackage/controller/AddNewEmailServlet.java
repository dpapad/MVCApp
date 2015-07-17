/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.mypackage.dal.mysql.MySqlConnectionProvider;
import org.mypackage.dal.mysql.MysqlContactRepository;
import org.mypackage.model.Email;

/**
 *
 * @author dpa
 */
@WebServlet(name = "AddNewEmailServlet", urlPatterns = {"/AddNewEmailServlet"})
public class AddNewEmailServlet extends HttpServlet {
    
    private String excMessage;
    
    
    private ContactRepository contactRepository;
    
    public AddNewEmailServlet() {
        this(ApplicationDependencies.REPOSITORY_FACTORY);
    }
    
    public AddNewEmailServlet(RepositoryFactory repositoryFactory) {
        this.contactRepository = repositoryFactory.createContactRepository();
    }
    

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Email email = new Email();
        //email.setEmailid(Integer.parseInt(request.getParameter("emailid")));
        email.setAddress(request.getParameter("address"));
        email.setType(request.getParameter(("email_type")));
        email.setContid(Integer.parseInt(request.getParameter("contid")));
        
        try {
            MysqlContactRepository contactRepository = new MysqlContactRepository(new MySqlConnectionProvider());
            contactRepository.addEmail(email);
            response.sendRedirect("contDetails.jsp");
        } catch (DalException ex) {
            excMessage = "Error Adding the email";
            DalException addNewEmailServletExc = new DalException(excMessage, ex);
            
            Logger.getLogger("Error in servlet method processRequest(HttpServletRequest request, HttpServletResponse response) at " +AddNewEmailServlet.class.getName()).log(Level.SEVERE,"Message returned: " + excMessage, addNewEmailServletExc);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
