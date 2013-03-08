package alexandru.ciocea;

import java.util.List;
import java.util.Stack;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;

public class ActivityOverview extends Activity implements OnClickListener {

	Button handler, allActivities, activitesToday, createActivity;
	EditText activityDefinition, activityDuration, activitySubUnits;
	SlidingDrawer slideDrawer;
	MyFramework myFramework = null;
	FontClass fontClass = null;
	UserInformation userInfo;
	ViewGroup linearLayout;
	String definition = "";
	int duration = 0, subunits = 0;

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
		userInfo.setUserId(10);
		
		myFramework.getDisplayMetrics(getWindowManager());

		linearLayout = (ViewGroup) findViewById(R.id.llActOver);
		createActivity = (Button) findViewById(R.id.btActOverCreateActivity);
		fontClass.setFont(createActivity);
		createActivity.setOnClickListener(this);

		allActivities = (Button) findViewById(R.id.btActOverAllActivities);
		fontClass.setFont(allActivities);
		allActivities.setOnClickListener(this);

		activitesToday = (Button) findViewById(R.id.btActOverActivitiesToday);
		fontClass.setFont(activitesToday);
		activitesToday.setOnClickListener(this);

		createActivities();

	}

	private void createActivities() {

		ActivityDB entry = new ActivityDB(ActivityOverview.this);
		entry.open();
		entry.getActivities(userInfo.getUserId(), this.linearLayout, this);
		entry.close();
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {

		super.onCreateOptionsMenu(menu);
		MenuInflater popUp = getMenuInflater();
		popUp.inflate(R.menu.cool_menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.itemMenuAppInfo:
			startActivity(myFramework.switchView(ActivityOverview.this,
					"AppInfo"));
			break;
		/*
		 * case R.id.itemMenuMailMeSomething: //sendMail();
		 * //startActivity(myFramework.switchView(ActivityOverview.this,
		 * "sendMailDemo"));
		 * startActivity(myFramework.switchView(ActivityOverview.this,
		 * "MailSenderActivity")); break;
		 */

		case R.id.itemMenuPreferences:
			startActivity(myFramework.switchView(ActivityOverview.this,
					"Preferences"));
			break;
		case R.id.itemMenuExit:
			finish();
			break;
		}

		return false;
	}

	/*
	 * @Override protected void onPause() { // TODO Auto-generated method stub
	 * super.onPause();
	 * 
	 * definition = activityDefinition.getText().toString();
	 * 
	 * if (!activityDuration.getText().toString().equals("")) { duration =
	 * Integer.parseInt(activityDuration.getText().toString()); }
	 * 
	 * if (!activitySubUnits.getText().toString().equals("")) { subunits =
	 * Integer.parseInt(activitySubUnits.getText().toString()); }
	 * 
	 * }
	 */

	private void sendMail() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("*/*");
		// i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(crashLogFile));
		i.putExtra(Intent.EXTRA_EMAIL, new String[] { "cioalex@gmail.com" });
		i.putExtra(Intent.EXTRA_SUBJECT, "Crash report");
		i.putExtra(Intent.EXTRA_TEXT, "Some crash report details");

		startActivity(createEmailOnlyChooserIntent(i, "Send via email"));

	}

	public Intent createEmailOnlyChooserIntent(Intent source,
			CharSequence chooserTitle) {
		Stack<Intent> intents = new Stack<Intent>();
		Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
				"info@domain.com", null));
		List<ResolveInfo> activities = getPackageManager()
				.queryIntentActivities(i, 0);

		for (ResolveInfo ri : activities) {
			Intent target = new Intent(source);
			target.setPackage(ri.activityInfo.packageName);
			intents.add(target);
		}

		if (!intents.isEmpty()) {
			Intent chooserIntent = Intent.createChooser(intents.remove(0),
					chooserTitle);
			chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
					intents.toArray(new Parcelable[intents.size()]));

			return chooserIntent;
		} else {
			return Intent.createChooser(source, chooserTitle);
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.btActOverCreateActivity:

			startActivity(myFramework.switchView(ActivityOverview.this,
					"CreateActivity"));

			break;

		case R.id.btActOverAllActivities:
			startActivity(myFramework.switchView(ActivityOverview.this,
					"AllActivities"));
			break;
			
		case R.id.btActOverActivitiesToday:
			//TODO include screen for done activities of a day.24
			break;
		}

	}
}
