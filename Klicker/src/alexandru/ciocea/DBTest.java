package alexandru.ciocea;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DBTest extends Activity implements OnClickListener{

	Button getResult;
	EditText query;
	TextView result;	
	
	private String mysqlPage = "http://www.6guys1garden.de/android_MYSQL_request.php";
	private String mysqlDatabase = "db142179x1779912";
	private String mysqlServer = "mysql12.1blu.de";
	private String mysqlUsername = "s142179_1779912";
	private String mysqlPassword = "1s2btb4m";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dbtest);
		
		initVars();
		
	}

	private void initVars() {
		
		query = (EditText) findViewById(R.id.etQuery);
		getResult = (Button) findViewById(R.id.btSendQuery);
		result = (TextView) findViewById(R.id.tvQueryOutput);
		getResult.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		
		//Initialize the class with the information for the identification
		MYSQL_Request request = new MYSQL_Request(mysqlPage, mysqlDatabase, mysqlServer, mysqlUsername, mysqlPassword);
		JSONObject data;
		//Set the request
		request.setRequest(query.getText().toString());

		//Execute the request
		request.getServerData();

		//While a result will be available
		while (request.getNextEntry()){
		    //Get the JSON value
		    data = request.getJsonValue();
		    
		    try{
		        Log.d("result", data.getString("testID"));
		    }catch (Exception e){
		    	
		    }
		}
	}
}
