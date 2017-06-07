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
 * Servlet implementation class showProfil
 */
@WebServlet( "/showProfil" )
public class showProfil extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public showProfil() {

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        Connected connected = new Connected();
        boolean test = connected.isConnected( request );
        if ( test ) {
            this.getServletContext().getRequestDispatcher( "/WEB-INF/ShowProfil.jsp" ).forward( request, response );
        } else {
            response.sendRedirect( "/Supinfo/login" );
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
            userforms.validatorExsistence( request, false );
        } catch ( SQLException e ) {

            e.printStackTrace();
        }
        if ( userforms.getErreurs().isEmpty() ) {
            Connected connected = new Connected();
            connected.setSession( request, userforms.getUser() );
        }

        this.getServletContext().getRequestDispatcher( "/WEB-INF/ShowProfil.jsp" ).forward( request, response );

    }

}
