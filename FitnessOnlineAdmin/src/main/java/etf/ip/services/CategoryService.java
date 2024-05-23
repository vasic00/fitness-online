package etf.ip.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import etf.ip.model.CategoryDTO;

public class CategoryService {

	private static final String URLString = "http://localhost:8082/FitnessOnline/categories";
	
	public List<CategoryDTO> getAll() {
		try {
			URL url = new URL(URLString);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuffer response = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null) {
					response.append(line);
				}
				br.close();
				Gson gson = new Gson();
				return Arrays.asList(gson.fromJson(response.toString(), CategoryDTO[].class));
			}
			else return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public CategoryDTO findByName(String name) {
		try {
			name = name.replace(" ", "%20");
			URL url = new URL(URLString + "/" + name);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuffer response = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null) {
					response.append(line);
				}
				br.close();
				Gson gson = new Gson();
				return gson.fromJson(response.toString(), CategoryDTO.class);
			}
			else return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public CategoryDTO update(CategoryDTO category) {
		try {
			URL url = new URL(URLString);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json");
			OutputStream os = con.getOutputStream();
			Gson gson = new Gson();
			os.write(gson.toJson(category).getBytes());
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
				return gson.fromJson(response.toString(), CategoryDTO.class);
			}
			else return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public CategoryDTO add(CategoryDTO category) {
		try {
			URL url = new URL(URLString);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			OutputStream os = con.getOutputStream();
			Gson gson = new Gson();
			os.write(gson.toJson(category).getBytes());
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
				return gson.fromJson(response.toString(), CategoryDTO.class);
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
