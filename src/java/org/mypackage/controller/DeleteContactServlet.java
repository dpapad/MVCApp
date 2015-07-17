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

/**
 *
 * @author dpa
 */
@WebServlet(name = "DeleteContactServlet", urlPatterns = {"/DeleteContactServlet"})
public class DeleteContactServlet extends HttpServlet {
    
    private String excMessage;

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
        int id = Integer.parseInt(request.getParameter("delId"));
        try{
            MysqlContactRepository contactRepository = new MysqlContactRepository(new MySqlConnectionProvider());
            contactRepository.deleteContactById(id);
            response.sendRedirect("contView.jsp");
        } catch  (DalException ex) {
            excMessage = "Error Deleting the contact";
            DalException deleteContactServletExc = new DalException(excMessage, ex);
            
            Logger.getLogger("Error in servlet method processRequest(HttpServletRequest request, HttpServletResponse response) at " + DeleteContactServlet.class.getName()).log(Level.SEVERE,"Message returned: " + excMessage, deleteContactServletExc);
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
