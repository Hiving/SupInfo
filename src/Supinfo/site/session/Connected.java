package Supinfo.site.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import Supinfo.site.beans.User;

public class Connected {

    public boolean isConnected( HttpServletRequest request ) {
        HttpSession session = request.getSession();

        if ( session.getAttribute( "user" ) == null ) {
            return false;
        } else {
            return true;
        }

    }

    public void setSession( HttpServletRequest request, User user ) {
        HttpSession session = request.getSession();
        session.setAttribute( "user", user );
    }
    
    public User getUserSession(HttpServletRequest request){
    	return new User();
    }

}
