package org.mypackage.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mypackage.controller.NewContactController;

/**
 *
 * @author dpa
 */
@WebServlet(name = "NewContactServlet", urlPatterns = {"/newContact"})
public class NewContactServlet extends HttpServlet {

    
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/newContact.jsp").forward(request, response);
    }
    
    
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        NewContactController newContactController = new NewContactController();
        
        String fullName = request.getParameter("fullname");
        String nickname = request.getParameter("nickname");
        String notes = request.getParameter("notes");
    
            int c = newContactController.addNewContact(fullName, nickname, notes);
        
            String redirectUrl = this.getServletContext().getContextPath() + "/contacts/" + c;
            response.sendRedirect(redirectUrl);
                            
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
