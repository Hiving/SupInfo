package Supinfo.site.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Supinfo.site.forms.UserForms;
import Supinfo.site.session.Connected;

@WebServlet( "/StripesDispatcher" )
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public DispatcherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
    	System.out.println("sefsfdfldslmfdss***********************************");
        out.println("YOOOOO");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        UserForms userForms = new UserForms();
        boolean test;
        try {
            test = userForms.validatorLogin( request );

            if ( test ) {
                response.sendRedirect( "/Supinfo/profil" );
            } else {
                this.getServletContext().getRequestDispatcher( "/WEB-INF/Login.jsp" ).forward( request, response );
            }
        } catch ( SQLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}