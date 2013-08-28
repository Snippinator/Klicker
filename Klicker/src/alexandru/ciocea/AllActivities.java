package alexandru.ciocea;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AllActivities extends ListActivity {

	String activityDefinitions[];
	ArrayList<Activities> activitiesFromDatabase;
	UserInformation userInfo = UserInformation.getInstance();
	MyFramework myFramework;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		initVars();

		//normal listAdapter
		/*setListAdapter(new ArrayAdapter<String>(AllActivities.this,
				android.R.layout.simple_list_item_1, activityDefinitions));*/
		
		//list adapter to change font
		setListAdapter(new MyArrayAdapter<String>(AllActivities.this, activityDefinitions));
	}

	private void initVars() {

		myFramework = new MyFramework();

		// get activities from database
		ActivityDB entries = new ActivityDB(this);
		entries.open();
		activitiesFromDatabase = entries.getActivities(userInfo.getUserId());
		entries.close();

		// set string array of listview to same size as activities inside the db
		activityDefinitions = new String[activitiesFromDatabase.size()];

		// insert definition of each activity in activityDefinition for listview
		for (int i = 0; i < activitiesFromDatabase.size(); i++) {
			Activities a = new Activities();
			a = activitiesFromDatabase.get(i);
			activityDefinitions[i] = a.getDefinition();
			Log.e("DEFINITION", a.getDefinition());
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent i = myFramework.switchView(AllActivities.this, "ActivityDetail");

		// pass through details of activity to activitydetail
		Activities a = activitiesFromDatabase.get(position);

		i.putExtra("activityId", a.getId());
		i.putExtra("activitiyDefinition", a.getDefinition());
		i.putExtra("activityDuration", a.getDuration());
		i.putExtra("activitySubunits", a.getSubunits());

		startActivity(i);
	}

}
