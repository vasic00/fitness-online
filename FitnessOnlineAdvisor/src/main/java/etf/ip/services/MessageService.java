package etf.ip.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import etf.ip.model.MessageDTO;

public class MessageService {
	
	private static final String URLString =  "http://localhost:8082/FitnessOnline/messages";
	
	public List<MessageDTO> getAll(String unread, String key) {
		try {
			URL url = new URL(URLString + "/filtered" + "?unread=" + unread + "&key=" + key);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuffer response = new StringBuffer();
				String line = null;
				while ((line = br.readLine())!= null) {
					response.append(line);
				}
				br.close();
				Gson gson = new Gson();
				return Arrays.asList(gson.fromJson(response.toString(), MessageDTO[].class));
			}
			else return new ArrayList<>();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public MessageDTO read(long id) {
		try {
			URL url = new URL(URLString + "/" + id);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuffer response = new StringBuffer();
				String line = null;
				while ((line = br.readLine())!= null) {
					response.append(line);
				}
				br.close();
				Gson gson = new Gson();
				return gson.fromJson(response.toString(), MessageDTO.class);
			}
			else return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean delete(long id) {
		try {
			URL url = new URL(URLString + "/" + id);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("DELETE");
			return con.getResponseCode() == HttpURLConnection.HTTP_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
