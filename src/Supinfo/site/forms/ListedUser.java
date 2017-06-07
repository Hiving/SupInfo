package Supinfo.site.forms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mysql.jdbc.Connection;

import Supinfo.site.beans.*;
import Supinfo.site.mysql.ConnexionMysql;
import Supinfo.site.mysql.ManipMysql;

public class ListedUser {
	private List<User> listUserConnected = new ArrayList<User>();
	
	public List<User> getUsersConnected(HttpServletRequest request) throws SQLException{
		ConnexionMysql mysql = new ConnexionMysql();
		Connection connexion = mysql.executerTests();
		
		ManipMysql operations = new ManipMysql();
		operations.selectUsersConnected(connexion, request, this.listUserConnected);
		
		return this.listUserConnected;
	}
	
}
