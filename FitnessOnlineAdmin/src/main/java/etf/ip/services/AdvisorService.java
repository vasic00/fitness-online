package etf.ip.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import etf.ip.model.Advisor;

public class AdvisorService {
	
	private static final String URLString =  "http://localhost:8082/FitnessOnline/advisor";
	
	public List<Advisor> getAll() {
		try {
			URL url = new URL(URLString);
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
				return Arrays.asList(gson.fromJson(response.toString(), Advisor[].class));
			}
			else return new ArrayList<>();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public Advisor openAdvisorAccount(Advisor advisor) {
		try {
			URL url = new URL(URLString);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			OutputStream os = con.getOutputStream();
			Gson gson = new Gson();
			os.write(gson.toJson(advisor).getBytes());
			os.flush();
			os.close();
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuffer response = new StringBuffer();
				String line = null;
				while ((line = br.readLine())!= null) {
					response.append(line);
				}
				br.close();
				return gson.fromJson(response.toString(), Advisor.class);
			}
			else return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
