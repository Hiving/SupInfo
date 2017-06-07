package Supinfo.site.forms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import asterisk.bridge.AsteriskBridgeApi;

import com.mysql.jdbc.Connection;

import Supinfo.site.beans.User;
import Supinfo.site.mysql.ConnexionMysql;
import Supinfo.site.mysql.ManipMysql;
import Supinfo.site.session.Connected;

public class UserForms {
    private User         user    = new User();
    private List<String> erreurs = new ArrayList<String>();

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public List<String> getErreurs() {
        return erreurs;
    }

    public void setErreurs( List<String> erreurs ) {
        this.erreurs = erreurs;
    }

    public void validatorExsistence( HttpServletRequest request, boolean what ) throws SQLException {

        boolean test;
        user.generateSipCredentials();
        
        user.setUsername( request.getParameter( "username" ) );
        request.setAttribute( "username", user.getUsername() );

        user.setPassword( request.getParameter( "password" ) );
        request.setAttribute( "password", user.getPassword() );

        user.setEmail( request.getParameter( "email" ) );
        request.setAttribute( "email", user.getEmail() );

        user.setFirstName( request.getParameter( "firstname" ) );
        request.setAttribute( "firstname", user.getFirstName() );

        user.setLastName( request.getParameter( "lastname" ) );
        request.setAttribute( "lastname", user.getLastName() );

        user.setPhoneNumber( request.getParameter( "telephone" ) );
        request.setAttribute( "telephone", user.getPhoneNumber() );

        ConnexionMysql connexionMysql = new ConnexionMysql();
        Connection connexion = connexionMysql.executerTests();

        ManipMysql manipMysql = new ManipMysql();
        test = manipMysql.testExiste( connexion, user, request );

        if ( what == true ) {
            if ( test == true ) {
                manipMysql.createStat( connexion, user );
                connexionMysql.closeBase();
                AsteriskBridgeApi.addUserOnSipNetwork(user);
            } else {
                this.erreurs.add( "erreurs" );
            }
            connexionMysql.closeBase();
        } else {
            if ( test == true ) {
                manipMysql.updataProfil( connexion, user, request );
                connexionMysql.closeBase();

            } else {
                this.erreurs.add( "erreurs" );
            }
        }

    }

    public boolean validatorLogin( HttpServletRequest request ) throws SQLException {
        boolean test;

        request.setAttribute( "email", request.getParameter( "email" ) );

        ConnexionMysql connexionMysql = new ConnexionMysql();
        Connection connexion = connexionMysql.executerTests();

        ManipMysql manipMysql = new ManipMysql();
        test = manipMysql.testLogin( connexion, user, request );

        //connexionMysql.closeBase();

        if ( test ) {
            Connected connected = new Connected();
            manipMysql.updateStatutUser(connexion, user, request);
            connected.setSession( request, user );
            connexionMysql.closeBase();
            
            return true;
        } else {
            return false;
        }
    }
    public void deconnectUser(HttpServletRequest request) throws SQLException{
    	HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("user");
    	
    	ConnexionMysql connexionMysql = new ConnexionMysql();
    	Connection connexion = connexionMysql.executerTests();
    	
    	ManipMysql operations = new ManipMysql();
    	operations.updateStatutUsertoDeconnected(connexion, user);
    }
    
}
