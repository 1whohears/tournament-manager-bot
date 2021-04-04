package onewhohears.Tournament_Manager.Connection;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpConnect {
	
	public HttpConnect() { }
	
	public static void custom_connect(String request_method, String url2, String parameters) {
        try {
            URL url = new URL(url2);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(request_method);
            con.setDoOutput(true);
            con.getOutputStream().write(parameters.getBytes("UTF-8"));
            con.getInputStream();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return;
	}
	
	public static JSONObject getJsonObject(String url, String parameters) {
		String text = streamToString(getUrlInputStream("GET",url,parameters));
		JSONParser parser = new JSONParser();
        JSONObject json = new JSONObject();
        try { json = (JSONObject)parser.parse(text); }
        catch (ParseException e) { e.printStackTrace(); }
        return json;
	}
	
	public static JSONArray getJsonArray(String url, String parameters) {
		String text = streamToString(getUrlInputStream("GET",url,parameters));
		JSONParser parser = new JSONParser();
        JSONArray json = new JSONArray();
        try { json = (JSONArray)parser.parse(text); }
        catch (ParseException e) { e.printStackTrace(); }
        return json;
	}
	
	public static JSONObject getJsonObjectUserPass(String url2, String username, String password) {
        String text = null;
        try {
            URL url = new URL(url2);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            String credentials = username + ":" + password;
            String authValue = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
            connection.setRequestProperty("Authorization",authValue);
            connection.connect();
            InputStream inStream = connection.getInputStream();
            text = streamToString(inStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JSONParser parser = new JSONParser();
        JSONObject json = new JSONObject();
        try { json = (JSONObject)parser.parse(text); }
        catch (ParseException e) { e.printStackTrace(); }
        return json;
	}
	
	public static JSONArray getJsonArrayUserPass(String url2, String username, String password) {
        String text = null;
        try {
            URL url = new URL(url2);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            String credentials = username + ":" + password;
            String authValue = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
            connection.setRequestProperty("Authorization",authValue);
            connection.connect();
            InputStream inStream = connection.getInputStream();
            text = streamToString(inStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JSONParser parser = new JSONParser();
        JSONArray json = new JSONArray();
        try { json = (JSONArray)parser.parse(text); }
        catch (ParseException e) { e.printStackTrace(); }
        return json;
	}
	
	private static InputStream getUrlInputStream(String request_method, String url2, String parameters) {
		try {
            URL url = new URL(url2);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(request_method);
            con.setDoOutput(true);
            con.getOutputStream().write(parameters.getBytes("UTF-8"));
            InputStream inStream = con.getInputStream();
            return inStream;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		return null;
	}
	
	private static String streamToString(InputStream inputStream) {
        if (inputStream == null) return "";
		@SuppressWarnings("resource")
		String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }
}
