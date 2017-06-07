package Supinfo.site.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Supinfo.site.beans.User;
import Supinfo.site.forms.ListedUser;
import Supinfo.site.session.Connected;

/**
 * Servlet implementation class profil
 */
@WebServlet( "/profil" )
public class profil extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public profil() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        User f1 = new User();
        f1.setFirstName( "Albert" );
        f1.setLastName( "Coco" );
        f1.setUsername( "1060" );

        User f2 = new User();
        f2.setFirstName( "Yoo" );
        f2.setLastName( "Polka" );
        f2.setUsername( "1022" );

        ArrayList<User> users = new ArrayList<User>();
        users.add( f1 );
        users.add( f2 );

        //request.setAttribute( "friends", users );

        Connected connected = new Connected();
        boolean test = connected.isConnected( request );
        if ( test ) {
        	ListedUser list = new ListedUser();
        	try {
				List<User> listUserConnected = list.getUsersConnected(request);
				request.setAttribute("friends", listUserConnected);
			} catch (SQLException e) {
				e.printStackTrace();
			}
            this.getServletContext().getRequestDispatcher( "/WEB-INF/Profil.jsp" ).forward( request, response );
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
        // TODO Auto-generated method stub
        this.getServletContext().getRequestDispatcher( "/WEB-INF/Profil.jsp" ).forward( request, response );
    }

}
