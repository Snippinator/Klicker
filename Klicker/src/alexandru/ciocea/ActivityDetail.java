package alexandru.ciocea;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityDetail extends Activity {

	TextView tvDefinition, tvDurationSingle, tvDurationMultiplied, tvSubunits;
	ListView lvExecution;
	String executions[];
	ArrayList<Execution> allExecutionsFromDatabase;
	ArrayAdapter<String> adapter;
	UserInformation userInfo = UserInformation.getInstance();
	MyFramework myFramework;
	FontClass fontClass;

	// Extras from intent
	String definition;
	int activityId, durationSingle, durationMultiplied, subunits;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitydetail);

		initVars();
		callDatabase();

	}

	private void initVars() {

		myFramework = new MyFramework();
		fontClass = new FontClass();

		// load extras into variables
		definition = myFramework.getExtrasString("activitiyDefinition",
				getIntent().getExtras());
		activityId = myFramework.getExtrasInt("activityId", getIntent()
				.getExtras());
		durationSingle = myFramework.getExtrasInt("activityDuration",
				getIntent().getExtras());
		subunits = myFramework.getExtrasInt("activitySubunits", getIntent()
				.getExtras());

		// init ui elements
		tvDefinition = (TextView) findViewById(R.id.tvActDetailDefinition);
		fontClass.setFont(tvDefinition);
		tvDurationSingle = (TextView) findViewById(R.id.tvActDetailDurationSingle);
		fontClass.setFont(tvDurationSingle);
		tvDurationMultiplied = (TextView) findViewById(R.id.tvActDetailDurationMultiplied);
		fontClass.setFont(tvDurationMultiplied);
		tvSubunits = (TextView) findViewById(R.id.tvActDetailSubunits);
		fontClass.setFont(tvSubunits);
		lvExecution = (ListView) findViewById(R.id.lvActDetail);

	}

	private void callDatabase() {

		// get executions from db
		ActivityDB entries = new ActivityDB(this);
		entries.open();
		allExecutionsFromDatabase = entries.getSpecificExecutions(
				userInfo.getUserId(), activityId);
		entries.close();

		// insert extras into textviews
		tvDefinition.setText(definition);
		tvDurationSingle.setText("Dauer: " + durationSingle);
		tvDurationMultiplied.setText("Dauer gesamt: " + durationSingle
				* allExecutionsFromDatabase.size());
		tvSubunits.setText("Einheiten: " + subunits);

		// set list array to same size as allExecutionsFromDatabase
		executions = new String[allExecutionsFromDatabase.size()];

		// insert definition of each execution in executions for listview
		for (int i = 0; i < allExecutionsFromDatabase.size(); i++) {
			Execution e = new Execution();
			e = allExecutionsFromDatabase.get(i);
			
			//change format of timestamp
			e.setTimestamp(changeExecution(e.getTimestamp()));
			
			executions[i] = e.getTimestamp();
			Log.e("DEFINITION", "" + e.getTimestamp());
		}
		
		

		// init array adapter
		adapter = new MyArrayAdapter<String>(ActivityDetail.this, executions);

		// add adapter and onClickListener to listview
		lvExecution.setAdapter(adapter);

		lvExecution.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				/*TODO: HAS TO BE FIXED
				myFramework.createToast(ActivityDetail.this, ((TextView) view).getText().toString());
				*/
			}

		});

	}

	private String changeExecution(String timestamp) {
		
		String newTimestamp = "";
		int hours = 0;
		SimpleDateFormat oldDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat newDate = new SimpleDateFormat("dd. MMMM yyyy,\n HH:mm");
		Date olderOne = new Date();
		try {
			olderOne = oldDate.parse(timestamp);
			hours = olderOne.getHours();
			olderOne.setHours(hours + 1);
			newTimestamp = newDate.format(olderOne);
		} catch (ParseException e) {
			Log.e("PASRING DATE", e.getMessage());
			e.printStackTrace();
			newTimestamp = timestamp;
		}
		
		
		return newTimestamp;
	}

}
