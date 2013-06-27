/**
 *  Copyright © Intelligent Software Components S.A. 2013 All Rights Reserved.
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>
 *  
 *  Authors: Alejandro Caparros    (acaparros@isoco.com)
 *  		 Esteban García-Cuesta (egarcia@isoco.com)
 *  
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class English {

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
					"http://sandbox-xlike.isoco.com/xlike-pipeline/services/xlike-en");
			String body = "";
			body = URLEncoder.encode(sentence, "UTF-8");
			body = body.replace("+", "%20");
			body = "text=" + body + "&target=relations&conll=false";

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
