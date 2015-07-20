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
import org.mypackage.dal.DalException;
import org.mypackage.dal.mysql.MySqlConnectionProvider;
import org.mypackage.dal.mysql.MysqlContactRepository;
import org.mypackage.model.Contact;


/**
 *
 * @author dpa
 */
@WebServlet(name = "ModifyContactServlet", urlPatterns = {"/modifyContact"})
public class ModifyContactServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private String excMessage;
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Contact contact = new Contact();
        contact.setContid(Integer.parseInt(request.getParameter("contid")));
        contact.setFullname(request.getParameter("fullname"));
        contact.setNickname(request.getParameter("nickname"));
        contact.setNotes(request.getParameter("notes"));
        
        request.setAttribute("contact", contact);
        
        try {
            MysqlContactRepository contactRepository = new MysqlContactRepository(new MySqlConnectionProvider());
            contactRepository.updateContact(contact);
            request.getRequestDispatcher("/viewContact.jsp").forward(request, response);
            
//            String redirectUrl = this.getServletContext().getContextPath() + "/modifyContact";
//            response.sendRedirect(redirectUrl);  
            
            //response.sendRedirect("contView.jsp"); 
        } catch (DalException ex) {
            
            excMessage = "Error Updating the contact";
            DalException updateContactServletExc = new DalException(excMessage, ex);
            
            Logger.getLogger("Error in servlet method processRequest(HttpServletRequest request, HttpServletResponse response) at " + ModifyContactServlet.class.getName()).log(Level.SEVERE,"Message returned: " + excMessage, updateContactServletExc);
        }
    }
    
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        Contact contact = new Contact();
        Integer id = Integer.parseInt(request.getParameter("contactId"));
        try {
            MysqlContactRepository contactRepository = new MysqlContactRepository(new MySqlConnectionProvider());
            contact = contactRepository.getContactById(id);
        } catch (DalException ex) {
            
        }      
                
        request.setAttribute("contact", contact);

        
        request.getRequestDispatcher("/modifyContact.jsp").forward(request, response);
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
        processGetRequest(request, response);
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
