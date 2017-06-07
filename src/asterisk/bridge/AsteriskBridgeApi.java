package asterisk.bridge;

import Supinfo.site.beans.User;

import java.net.*;
import java.io.*;

public class AsteriskBridgeApi {
	public static final String API_ADDR = "http://163.172.58.60:34567";
	public static final String API_KEY = "127ec4eeaf1471889307d163de51bd410aaecb88";
	
	public static void addUserOnSipNetwork(User user) {
		try {
			URL sipServer = new URL(String.format("%s/add-user?key=%s&user_id=%s&user_password=%s", AsteriskBridgeApi.API_ADDR, AsteriskBridgeApi.API_KEY, user.getSip_id(), user.getSip_password()));
			URLConnection yc = sipServer.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) 
			System.out.println(inputLine);
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
