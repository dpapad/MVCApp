/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
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
public class GetAllEmailsServlet extends HttpServlet {

    private String excMessage;

    private ContactRepository contactRepository;

    public GetAllEmailsServlet() {
        this(ApplicationDependencies.REPOSITORY_FACTORY);
    }

    public GetAllEmailsServlet(RepositoryFactory repositoryFactory) {
        this.contactRepository = repositoryFactory.createContactRepository();
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GetAllServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetAllServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        //RequestDispatcher rd = null;
        MysqlContactRepository contactRepository = new MysqlContactRepository(new MySqlConnectionProvider());
        int id = Integer.parseInt(request.getParameter("contid"));
        try {
            List<Email> list = contactRepository.getAllEmailsByContactId(id);
            request.setAttribute("emailList", list);
            request.getRequestDispatcher("/emailsByContact.jsp").forward(request, response);
        }
        catch (DalException ex) {}

        }

    
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
