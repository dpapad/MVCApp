package org.mypackage.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.mypackage.controller.NewContactController;
import org.mypackage.dal.DalException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author dpa
 */
@WebServlet(name = "NewContactServlet", urlPatterns = {"/newContact"})
public class NewContactServlet extends HttpServlet {

    private final static Logger logger = Logger.getLogger(NewContactServlet.class);
    private NewContactController newContactController;

//    public NewContactServlet() {
//        this(ApplicationDependencies.CONTROLLER_FACTORY.createNewContactController());
//    }
    @Autowired
    public NewContactServlet(NewContactController newContactController) {
        this.newContactController = newContactController;
    }

    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/newContact.jsp").forward(request, response);
    }

    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullname");
        String nickname = request.getParameter("nickname");
        String notes = request.getParameter("notes");

        try {
            int c = newContactController.addNewContact(fullName, nickname, notes);

            String redirectUrl = this.getServletContext().getContextPath() + "/contacts/" + c;
            response.sendRedirect(redirectUrl);
        } catch (DalException ex) {
            logger.error("A database error occured while trying to create a contact with the following parameters:"
                    + "\nFull Name: " + fullName
                    + "\nNick name: " + nickname
                    + "\nNotes: " + notes, ex);
            request.setAttribute("errorCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute("errorMessage", "There was a an internal database error.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
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
