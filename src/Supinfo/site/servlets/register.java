package Supinfo.site.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Supinfo.site.forms.UserForms;
import Supinfo.site.session.Connected;

/**
 * Servlet implementation class register
 */
@WebServlet( "/register" )
public class register extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public register() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        Connected connected = new Connected();
        if ( connected.isConnected( request ) == true ) {
            response.sendRedirect( "/Supinfo/profil" );
        } else {
            this.getServletContext().getRequestDispatcher( "/WEB-INF/Register.jsp" ).forward( request, response );
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        UserForms userforms = new UserForms();
        try {
            userforms.validatorExsistence( request, true );
        } catch ( SQLException e ) {

            e.printStackTrace();
        }
        if ( userforms.getErreurs().isEmpty() ) {
            Connected connected = new Connected();
            connected.setSession( request, userforms.getUser() );
            response.sendRedirect( "/Supinfo/profil" );
        } else {

            this.getServletContext().getRequestDispatcher( "/WEB-INF/Register.jsp" ).forward( request, response );
        }

    }

}
