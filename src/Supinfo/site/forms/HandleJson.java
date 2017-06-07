package Supinfo.site.forms;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Connection;

import Supinfo.site.mysql.ConnexionMysql;
import Supinfo.site.mysql.ManipMysql;


public class HandleJson {

    public JSONObject getListConnected(HttpServletRequest request) throws SQLException, JSONException {

        ConnexionMysql base = new ConnexionMysql();
        Connection connexion = base.executerTests();
        
        ManipMysql operations = new ManipMysql();
        
        JSONObject finale = operations.SelectList( connexion, request );
        
        base.closeBase();

        return finale;

    }

}