package Supinfo.site.mysql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class ConnexionMysql {
    String     url         = "jdbc:mysql://163.172.58.60:3306/Supinfo";
    String     utilisateur = "root";
    String     motDePasse  = "root";
    Connection connexion   = null;
    Statement  statement   = null;
    ResultSet  resultat    = null;

    public Connection executerTests() {

        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch ( ClassNotFoundException e ) {

        }

        try {
            connexion = (Connection) DriverManager.getConnection( url, utilisateur, motDePasse );

        } catch ( SQLException e ) {

        }
        return connexion;
    }

    public void closeBase() {

        if ( connexion != null ) {
            try {
                connexion.close();
            } catch ( SQLException ignore ) {
            }
        }
    }

}
