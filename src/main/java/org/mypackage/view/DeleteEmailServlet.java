/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.DeleteEmailController;
import org.mypackage.dal.DalException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author dev-dp
 */
public class DeleteEmailServlet extends HttpServlet {

    private final static Logger logger = Logger.getLogger(DeleteEmailServlet.class);
    private DeleteEmailController deleteEmailController;

//    public DeleteEmailServlet() {
//        this(ApplicationDependencies.CONTROLLER_FACTORY.createDeleteEmailController());
//    }
    @Autowired
    public DeleteEmailServlet(DeleteEmailController deleteEmailController) {
        this.deleteEmailController = deleteEmailController;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String emailId = request.getParameter("emailId");
        String contactId = request.getParameter("contId");
        try {

            this.deleteEmailController.deleteEmail(emailId);

            String redirectUrl = this.getServletContext().getContextPath() + "/contacts/" + contactId;
            response.sendRedirect(redirectUrl);

        } catch (DalException ex) {
            logger.error("A database error occured while trying to delete contact with ID = " + contactId, ex);
            request.setAttribute("errorCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute("errorMessage", "There was a an internal database error.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        } catch (MalformedIdentifierException ex) {
            request.setAttribute("errorCode", HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("errorMessage", "An error occured because of a malformed id. Please use only numeric values.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
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
