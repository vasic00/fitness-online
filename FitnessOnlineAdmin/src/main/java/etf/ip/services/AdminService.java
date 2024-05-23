package etf.ip.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import etf.ip.model.Admin;


public class AdminService {
	private final static String URLString = "http://localhost:8082/FitnessOnline/admin";
	public Admin login(String username, String password) {
		try {
			URL url = new URL(URLString + "?username=" + username + "&password=" + password);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				Gson gson=new Gson();
				return gson.fromJson(response.toString(), Admin.class);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
