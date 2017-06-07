package Supinfo.site.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import Supinfo.site.beans.User;

public class ManipMysql {

    Statement statement = null;
    ResultSet resultat  = null;

    public void createStat( Connection connexion, User user ) throws SQLException {
        statement = (Statement) connexion.createStatement();

        String query = String.format(
                "INSERT INTO Persons (Username,Password,Email,Firstname,Lastname,PhoneNumber,sip_id, sip_password, Statut) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s', 'connected')",
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getSip_id(),
                user.getSip_password());

        statement.executeUpdate( query );
        this.closepart1( statement, resultat );
    }

    public boolean testExiste( Connection connexion, User user, HttpServletRequest request ) throws SQLException {
        int result = 0;
        int result1 = 0;

        statement = (Statement) connexion.createStatement();
        resultat = statement.executeQuery(
                String.format( "SELECT COUNT(*) AS total FROM Persons WHERE Email = '%s'", user.getEmail() ) );

        while ( resultat.next() ) {
            result = resultat.getInt( "total" );

        }

        resultat = statement.executeQuery(
                String.format( "SELECT COUNT(*) AS total FROM Persons WHERE Username = '%s'", user.getUsername() ) );

        while ( resultat.next() ) {
            result1 = resultat.getInt( "total" );

        }

        boolean test = true;

        if ( result != 0 ) {
            request.setAttribute( "emailExiste", "Ce mail Existe Deja" );
            test = false;
        }
        if ( result1 != 0 ) {
            request.setAttribute( "UsernameExiste", "Username already Exist" );
            test = false;
        }

        if ( test ) {
            return true;
        } else {
            return false;
        }
    }

    public boolean testLogin( Connection connexion, User user, HttpServletRequest request ) throws SQLException {
        statement = (Statement) connexion.createStatement();

        resultat = statement.executeQuery(
                String.format(
                        "SELECT Username,Password,Email,FirstName,LastName,PhoneNumber,sip_id,sip_password FROM Persons WHERE Email = '%s' AND Password = '%s'",
                        request.getParameter( "email" ), request.getParameter( "password" ) ) );

        while ( resultat.next() ) {
            user.setUsername( resultat.getString( "Username" ) );
            user.setPassword( resultat.getString( "Password" ) );
            user.setEmail( resultat.getString( "Email" ) );
            user.setFirstName( resultat.getString( "FirstName" ) );
            user.setLastName( resultat.getString( "LastName" ) );
            user.setPhoneNumber( resultat.getString( "PhoneNumber" ) );
            user.setSip_id(resultat.getString( "sip_id" ) );
            user.setSip_password( resultat.getString( "sip_password" ) );
        }

        if ( user.getEmail() == null ) {
            this.closepart1( statement, resultat );
            request.setAttribute( "erreurs", "username or email does exists" );
            return false;
        } else {
            this.closepart1( statement, resultat );
            return true;
        }

    }
    
    public List<User> selectUsersConnected( Connection connexion, HttpServletRequest request, List<User> listUserConnected ) throws SQLException {
        
    	statement = (Statement) connexion.createStatement();
    	HttpSession session = request.getSession();
    	User userSession = (User) session.getAttribute("user");
    	

        resultat = statement.executeQuery(
                String.format(
                        "SELECT Username,Email,FirstName,LastName,PhoneNumber,sip_id,sip_password,Statut FROM Persons WHERE Username != '%s' AND Statut = 'connected' OR 'absent' ", userSession.getUsername()));

        while ( resultat.next() ) {
        	User user = new User();
            user.setUsername( resultat.getString( "Username" ) );
            user.setEmail( resultat.getString( "Email" ) );
            user.setFirstName( resultat.getString( "FirstName" ) );
            user.setLastName( resultat.getString( "LastName" ) );
            user.setPhoneNumber( resultat.getString( "PhoneNumber" ) );
            user.setSip_id(resultat.getString( "sip_id" ) );
            user.setSip_password( resultat.getString( "sip_password" ));
            user.setStatut(resultat.getString("Statut") );
            listUserConnected.add(user);
        }
        
         return listUserConnected;

    }

    public void updateStatutUser( Connection connexion, User user, HttpServletRequest request ) throws SQLException {
        

        statement = (Statement) connexion.createStatement();

        String query = String.format(
                "UPDATE Persons SET Statut = 'connected' WHERE Email ='%s'",
                user.getEmail() 
                );

        statement.executeUpdate( query );

        this.closepart1( statement, resultat );

    }
    
public void updateStatutUsertoDeconnected( Connection connexion, User user ) throws SQLException {
        

        statement = (Statement) connexion.createStatement();

        String query = String.format(
                "UPDATE Persons SET Statut = 'disconnected' WHERE Email ='%s'",
                user.getEmail() 
                );

        statement.executeUpdate( query );

        this.closepart1( statement, resultat );

    }
    
    public void updataProfil( Connection connexion, User user, HttpServletRequest request ) throws SQLException {
        HttpSession session = request.getSession();
        User userSession = (User) session.getAttribute( "user" );

        statement = (Statement) connexion.createStatement();

        String query = String.format(
                "UPDATE Persons SET Username = '%s', Password = '%s', Email= '%s',Firstname= '%s',Lastname= '%s',PhoneNumber= '%s' WHERE Email ='%s' AND Username = '%s'",
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                userSession.getEmail(),
                userSession.getUsername() );

        statement.executeUpdate( query );

        this.closepart1( statement, resultat );

    }
    
    public JSONObject SelectList( Connection connexion,HttpServletRequest request )
            throws SQLException, JSONException {
    	
    	HttpSession session = request.getSession();
    	User userSession = (User) session.getAttribute("user");

        List<User> listUserConnected = new ArrayList<User>();

        JSONObject finale = new JSONObject();
        JSONArray initiale = new JSONArray();

        statement = (Statement) connexion.createStatement();

        resultat = statement.executeQuery(
        		String.format(
                        "SELECT Username,Email,FirstName,LastName,PhoneNumber,sip_id,sip_password,Statut FROM Persons WHERE Username != '%s' AND Statut = 'connected' OR 'absent' ", userSession.getUsername()));

        while ( resultat.next() ) {
            User user = new User();
            user.setUsername( resultat.getString( "Username" ) );
            user.setPassword( resultat.getString( "Statut" ) );
            user.setEmail( resultat.getString( "Email" ) );
            user.setFirstName( resultat.getString( "FirstName" ) );
            user.setLastName( resultat.getString( "LastName" ) );
            user.setPhoneNumber( resultat.getString( "PhoneNumber" ) );
            user.setSip_id(resultat.getString( "sip_id" ) );
            user.setSip_password( resultat.getString( "sip_password" ) );
            listUserConnected.add( user );
        }
        
        for ( User i : listUserConnected ) {
            JSONObject ini = new JSONObject();
            ini.put( "username", i.getUsername() );
            ini.put( "sip_id", i.getSip_id() );
            initiale.put( ini );
        }

        finale.put( "User", initiale );

        this.closepart1( statement, resultat );

        return finale;

    }
    
    public void closepart1( Statement statement, ResultSet resultat ) {
        if ( resultat != null ) {
            try {
                resultat.close();
            } catch ( SQLException ignore ) {
            }
        }

        if ( statement != null ) {
            try {
                statement.close();
            } catch ( SQLException ignore ) {
            }
        }

    }

}
