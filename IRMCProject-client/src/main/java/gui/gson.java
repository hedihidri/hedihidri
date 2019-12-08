package gui;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.naming.NamingException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class gson {

	public static void main(String[] args) throws NamingException, IOException {
		String address = "26 avenue Dargouth Pacha  Tunis, Tunisia (1007)";
		address= replace(address);
		String url = "https://maps.googleapis.com/maps/api/geocode/json?address=26%20avenue%20Dargouth%20Pacha%20Tunis,%20Tunisia%20(1007)&key=AIzaSyCdlW2e1RbLtrdV98yZStWKuyOiXi1BD6E";

		JsonElement jelement = new JsonParser().parse(jsonGetRequest(url));
		JsonObject jobject = jelement.getAsJsonObject();

		JsonArray jarray = jobject.getAsJsonArray("results");
		jobject = jarray.get(0).getAsJsonObject();

		jobject = jobject.getAsJsonObject("geometry");
		jobject = jobject.getAsJsonObject("location");

		String lat = jobject.get("lat").getAsString();
		String lng = jobject.get("lng").getAsString();
		System.out.println(lat + "," + lng);

		// System.out.println(jsonGetRequest(url));
	}

	public static String replace(String str) {
		return str.replaceAll(" ", "%20");
	}

	private static String streamToString(InputStream inputStream) {
		String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
		return text;
	}

	public static String jsonGetRequest(String urlQueryString) {
		String json = null;
		try {
			URL url = new URL(urlQueryString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("charset", "utf-8");
			connection.connect();
			InputStream inStream = connection.getInputStream();
			json = streamToString(inStream); // input stream to string
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return json;
	}
}
