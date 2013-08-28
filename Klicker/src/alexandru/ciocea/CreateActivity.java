package alexandru.ciocea;

import org.xml.sax.Attributes;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;

public class CreateActivity extends Activity implements OnClickListener{

	EditText activityDefinition, activityDuration, activitySubUnits;
	Button backToOverview, saveActivity;
	FontClass fontClass;
	MyFramework myFramework;
	UserInformation userInfo = UserInformation.getInstance();
	String definition = "";
	int duration = 0, subunits = 0, buttonHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createactivity);
		initVars();
	}

	private void initVars() {

		fontClass = new FontClass();
		myFramework = new MyFramework();
		userInfo = userInfo.getInstance();
		userInfo.setUserId(10);

		activityDefinition = (EditText) findViewById(R.id.etCreateActDefinition);
		fontClass.setFont(activityDefinition);
		activityDuration = (EditText) findViewById(R.id.etCreateActDuration);
		fontClass.setFont(activityDuration);
		activitySubUnits = (EditText) findViewById(R.id.etCreateActSubUnits);
		fontClass.setFont(activitySubUnits);
		backToOverview = (Button) findViewById(R.id.btCreateActBack);
		fontClass.setFont(backToOverview);
		backToOverview.setOnClickListener(this);
		saveActivity = (Button) findViewById(R.id.btCreateActSave);
		fontClass.setFont(saveActivity);
		saveActivity.setOnClickListener(this);
		saveActivity.getHeight();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.btCreateActBack:

			// go back to overview
			startActivity(myFramework.switchView(CreateActivity.this,
					"ActivityOverview"));

			break;
		case R.id.btCreateActSave:
			// if button is pressed, check if definition and duration has been
			// clicked
			if (!activityDefinition.getText().toString().equals("")) {
				definition = activityDefinition.getText().toString();
				definition = myFramework.stringWithoutSpaces(definition);
			}

			if (!activityDuration.getText().toString().equals("")) {
				duration = Integer.parseInt(activityDuration.getText()
						.toString());
				// duration = myFramework.stringWithoutSpaces(duration);
			}

			if (!activitySubUnits.getText().toString().equals("")) {
				subunits = Integer.parseInt(activitySubUnits.getText()
						.toString());
				// subunits = myFramework.stringWithoutSpaces(subunits);

			}

			// if all afforded textfields have been completed
			if (!definition.equals("") && !(duration == 0)) {

				ActivityDB entry = new ActivityDB(CreateActivity.this);

				entry.open();

				if (entry
						.isDefinitionExistent(definition, userInfo.getUserId())) {
					myFramework.createDialogNeutral(CreateActivity.this,
							"Obacht", "Die Aktivität mit der Bezeichnung: "
									+ definition + ", ist bereits vorhanden");
					entry.close();
				} else {

					// entry.update();
					entry.saveActivity(userInfo.getUserId(), definition,
							duration, subunits);
					entry.close();

					activityDefinition.setText("");
					activityDuration.setText("");
					activitySubUnits.setText("");

					startActivity(myFramework.switchView(CreateActivity.this,
							"ActivityOverview"));

				}

				// if duration or definition is missing call a dialog
			} else if (definition.equals("") && (duration == 0)) {
				myFramework.createDialogNeutral(CreateActivity.this, "Aber, ...",
						"...da steht ja gar nichts");
			} else if (definition.equals("") || (duration == 0)) {
				myFramework
						.createDialogNeutral(
								CreateActivity.this,
								"Obacht",
								"Sie haben entweder die Bezeichnung oder die Dauer der Aktivität vergessen anzugeben.");
			}
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.itemMenuExit:
			finish();
			break;
		}

		return false;
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	/*
	 * @Override protected void onResume() {
	 * 
	 * super.onResume(); activityDefinition.setText(definition);
	 * activitySubUnits.setText(subunits); }
	 * 
	 * @Override protected void onPause() { // TODO Auto-generated method stub
	 * super.onPause(); definition = activityDefinition.getText().toString();
	 * 
	 * if (!activityDuration.getText().toString().equals("")) { duration =
	 * Integer.parseInt(activityDuration.getText().toString()); }
	 * 
	 * if (!activitySubUnits.getText().toString().equals("")) { subunits =
	 * Integer.parseInt(activitySubUnits.getText().toString()); } }
	 */

	

}
