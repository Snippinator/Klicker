package alexandru.ciocea;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.TextView;

public class ActivityOverview extends Activity implements OnDrawerCloseListener {

	Button handler;
	EditText activityDefinition, activityDuration, activitySubUnits;
	SlidingDrawer slideDrawer;
	MyFramework myFramework = null;
	FontClass fontClass = null;
	UserInformation userInfo;
	ViewGroup linearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activityoverview);
		initVars();

	}

	private void initVars() {

		fontClass = new FontClass();
		myFramework = new MyFramework();
		userInfo = userInfo.getInstance();
		userInfo.setUserId("10");

		linearLayout = (ViewGroup) findViewById(R.id.llActOver);

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

		createActivities();

	}

	private void createActivities() {
		ActivityDB entry = new ActivityDB(ActivityOverview.this);
		entry.open();
		entry.getActivities(userInfo.getUserId(), this.linearLayout, this);
		entry.close();
	}

	@Override
	public void onDrawerClosed() {
		String definition = activityDefinition.getText().toString();
		String duration = activityDuration.getText().toString();
		String subunits = activitySubUnits.getText().toString();

		if (!definition.equals("") && !duration.equals("")) {

			ActivityDB entry = new ActivityDB(ActivityOverview.this);

			entry.open();

			if (entry.checkActivityDefinition("definition=" + definition)) {
				myFramework.createDialogNeutral(ActivityOverview.this,
						"Obacht", "Die Aktivität mit der Bezeichnung: "
								+ definition + ", ist bereits vorhanden");
			} else {

				// entry.update();
				entry.saveActivity(userInfo.getUserId(), definition, duration,
						subunits);
				entry.close();

				activityDefinition.setText("");
				activityDuration.setText("");
				activitySubUnits.setText("");

				createActivities();
			}
		} else {

			myFramework
					.createDialogNeutral(
							ActivityOverview.this,
							"Obacht",
							"Sie haben entweder die Bezeichnung oder die Dauer der Aktivit&auml;t vergessen anzugeben.");
		}

	}

}
