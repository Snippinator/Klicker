package alexandru.ciocea;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.util.Log;

public class DatabaseCalls {

	private String mysqlPage = "http://www.6guys1garden.de/android_MYSQL_request.php";
	private String mysqlDatabase = "db142179x1779912";
	private String mysqlServer = "mysql12.1blu.de";
	private String mysqlUsername = "s142179_1779912";
	private String mysqlPassword = "1s2btb4m";
	private String mysqlQuery;
	StringBuilder sb;
	InputStream is;

	public DatabaseCalls(String query) {

		sendQuery(query);

	}

	private void sendQuery2(String query){
		
		    
	}

	private void sendQuery(String query) {

		// Initialize the class with the information for the identification
		MYSQL_Request request = new MYSQL_Request(mysqlPage, mysqlDatabase,
				mysqlServer, mysqlUsername, mysqlPassword);
		JSONObject data;
		// Set the request
		request.setRequest(mysqlQuery);

		// Execute the request
		request.getServerData();

		// While a result will be available
		while (request.getNextEntry()) {
			// Get the JSON value
			data = request.getJsonValue();

			try {
				Log.d("result", data.getString("username"));
			} catch (Exception e) {

			}
		}
	}

}
