package Supinfo.site.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Supinfo.site.forms.UserForms;

/**
 * Servlet implementation class Deconnect
 */
@WebServlet( "/deconnect" )
public class deconnect extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public deconnect() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserForms forms = new UserForms();
        try {
			forms.deconnectUser(request);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        session.invalidate();

        response.sendRedirect( "/Supinfo/login" );

    }

}
