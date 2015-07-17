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
import org.mypackage.model.Contact;
import org.mypackage.model.Email;

/**
 *
 * @author dpa
 */
@WebServlet(name = "AddNewContactServlet", urlPatterns = {"/AddNewContactServlet"})
public class AddNewContactServlet extends HttpServlet {

    private String excMessage;
    private ContactRepository contactRepository;
    
    public AddNewContactServlet() {
        this(ApplicationDependencies.REPOSITORY_FACTORY);
    }
    
    public AddNewContactServlet(RepositoryFactory repositoryFactory) {
        this.contactRepository = repositoryFactory.createContactRepository();
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Contact cont = new Contact();
        //cont.setContid(Integer.parseInt(request.getParameter("contid")));
        cont.setFullname(request.getParameter("fullname"));
        cont.setNickname(request.getParameter("nickname"));
        cont.setNotes(request.getParameter("notes"));
    
        try {
            MysqlContactRepository contactRepository = new MysqlContactRepository(new MySqlConnectionProvider());
            contactRepository.addContact(cont);
            response.sendRedirect("contView.jsp");            
        } catch (DalException ex) {
            excMessage = "Error Adding the contact";
            DalException addNewContactServletExc = new DalException(excMessage, ex);
            
            Logger.getLogger("Error in servlet method processRequest(HttpServletRequest request, HttpServletResponse response) at " +AddNewContactServlet.class.getName()).log(Level.SEVERE,"Message returned: " + excMessage, addNewContactServletExc);
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
