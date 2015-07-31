/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.view;

import java.io.IOException;
import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static javax.servlet.RequestDispatcher.ERROR_SERVLET_NAME;
import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;
import static javax.servlet.RequestDispatcher.INCLUDE_REQUEST_URI;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dev-dp
 */
public class AppExceptionHandler extends HttpServlet {

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
        String header;
        Throwable throwable = (Throwable) request.getAttribute(ERROR_EXCEPTION);
        Integer statusCode = (Integer) request.getAttribute(ERROR_STATUS_CODE);
        String servletName = (String) request.getAttribute(ERROR_SERVLET_NAME);
        if (servletName == null) {
            servletName = "Unknown";
        }
        String requestUri = (String) request.getAttribute(INCLUDE_REQUEST_URI);
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        if (statusCode != 500) {
            header = "Error Details";
            request.setAttribute("header", header);
            request.setAttribute("statusCode", statusCode);
            request.setAttribute("requestUri", requestUri);
        } else {
            header = "Exception Details";
            request.setAttribute("header", header);
            request.setAttribute("servletName", servletName);
            request.setAttribute("exceptionName", throwable.getClass().getName());
            request.setAttribute("requestUri", requestUri);
            request.setAttribute("exceptionMessage", throwable.getMessage());
        }

        request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
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
