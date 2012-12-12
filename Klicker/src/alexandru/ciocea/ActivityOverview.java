package alexandru.ciocea;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;

public class ActivityOverview extends Activity implements OnClickListener, OnDrawerCloseListener{

	Button handler;
	EditText activityDefinition, activityDuration, activitySubUnits;
	SlidingDrawer slideDrawer;
	MyFramework myFramework = null;
	FontClass fontClass = null;
	UserInformation userInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activityoverview);
		
		initVars();
		
		
	}
	
	private void initVars(){
		
		fontClass = new FontClass();
		myFramework = new MyFramework();
		userInfo = userInfo.getInstance();
		
		activityDefinition = (EditText) findViewById(R.id.etActOverActivityDefinition);
		fontClass.setFont(activityDefinition);
		activityDuration = (EditText) findViewById(R.id.etActOverActivityDuration);
		fontClass.setFont(activityDuration);
		activitySubUnits = (EditText) findViewById(R.id.etActOverActivitySubUnits);
		fontClass.setFont(activitySubUnits);
		handler = (Button) findViewById(R.id.btActOverHandle);
		fontClass.setFont(handler);
		slideDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer1);
		slideDrawer.setOnDrawerCloseListener(this);
		
	}

	public void onClick(View v) {
		
		/*
		 *call dialog window to define activity in detail view
		 *pass through the activity definition 
		 *after defining the activity create a new button for 
		 *this activity and put it at the beginning of the list.
		 *Activities should be sorted in order of timestamp 
		 */
		switch(v.getId()){
		
		case R.id.btActOverHandle:
			
			break;
		}
	}

	@Override
	public void onDrawerClosed() {
		String definition = activityDefinition.getText().toString();
		String duration = activityDuration.getText().toString();
		String subunits = activitySubUnits.getText().toString();
		
		
		if(!definition.equals("") && !duration.equals("")){
			ActivityDB entry = new ActivityDB(ActivityOverview.this);
			entry.open();
			entry.saveActivity(userInfo.getUserId(), definition, duration, subunits);
			entry.close();
			
			activityDefinition.setText("");
			activityDuration.setText("");
			activitySubUnits.setText("");
		}
		
	}
	
}
