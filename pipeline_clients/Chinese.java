package com.isoco.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Chinese {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		HttpURLConnection connection;
		DataOutputStream outStream;

		try {
			String sentence = "";
			for (int i = 0; i < args.length; i++) {
				sentence = sentence.concat(args[i] + " ");
			}
			URL url = new URL(
					"http://sandbox-xlike.isoco.com/xlike-pipeline/services/xlike-zh");
			String body = "";
			body = URLEncoder.encode(sentence, "UTF-8");
			body = body.replace("+", "%20");
			body = "text=" + body + "&conll=false";

			// Opening connection
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(body.getBytes().length));
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			// Sending POST
			System.out.println("Sending POST...");
			outStream = new DataOutputStream(connection.getOutputStream());
			outStream.writeBytes(body);
			outStream.flush();
			outStream.close();

			// Receiving response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuffer response = new StringBuffer();
			String str;

			// Printing response
			while ((str = rd.readLine()) != null) {
				response.append(str);
			}
			rd.close();

			System.out.println(response);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
