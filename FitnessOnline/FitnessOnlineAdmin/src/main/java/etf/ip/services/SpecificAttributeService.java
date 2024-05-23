package etf.ip.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import etf.ip.model.SpecificAttributeDTO;

public class SpecificAttributeService {
	private static final String URLString = "http://localhost:8082/FitnessOnline/attributes";
	
	public SpecificAttributeDTO update(SpecificAttributeDTO saDTO) {
		try {
			URL url = new URL(URLString);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json");
			OutputStream os = con.getOutputStream();
			Gson gson = new Gson();
			os.write(gson.toJson(saDTO).getBytes());
			os.flush();
			os.close();
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuffer response = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null) {
					response.append(line);
				}
				br.close();
				return gson.fromJson(response.toString(), SpecificAttributeDTO.class);
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
	
	public SpecificAttributeDTO add(SpecificAttributeDTO saDTO) {
		try {
			URL url = new URL(URLString);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			OutputStream os = con.getOutputStream();
			Gson gson = new Gson();
			os.write(gson.toJson(saDTO).getBytes());
			os.flush();
			os.close();
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuffer response = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null) {
					response.append(line);
				}
				br.close();
				return gson.fromJson(response.toString(), SpecificAttributeDTO.class);
			}
			else return null;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
