package alexandru.ciocea;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

public class MyFramework extends Activity {

	public MyFramework() {

	}

	public void closeKeyboard(TextView tv) {

		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(tv.getWindowToken(), 0);
	}

	public void createDialogNeutral(Context c, String title, String message) {

		Dialog dialog = new Dialog(c);
		dialog.setTitle(title);

		TextView tvMessage = new TextView(c);
		tvMessage.setText(message);
		tvMessage.setPadding(10, 10, 10, 10);
		dialog.setContentView(tvMessage);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();

	}

	// TODO: DOESN'T WORK
	public void createToast(Context context, String toastMessage) {
		Toast.makeText(getApplicationContext(), toastMessage,
				Toast.LENGTH_SHORT).show();
		String a = toastMessage;
		if (a == "Foo")
			Toast.makeText(context, "Foobar! xD", Toast.LENGTH_SHORT).show();

	}

	// get metrics of the display
	// http://kuennetht.blogspot.de/2011/10/pixelgroe-ermitteln.html
	public void getDisplayMetrics(WindowManager wManager) {
		String tag = "METRICS";
		DisplayMetrics outMetrics = new DisplayMetrics();
		wManager.getDefaultDisplay().getMetrics(outMetrics);
		Log.d(tag, "The logical density of the dispay: " + outMetrics.density);
		Log.d(tag, "The screen density expressed as dots-per-inch: "
				+ outMetrics.densityDpi);
		Log.d(tag,
				"The exact physical pixels per inch of the screen in the X dimension: "
						+ outMetrics.xdpi);
		Log.d(tag,
				"The exact physical pixels per inch of the screen in the Y dimension: "
						+ outMetrics.ydpi);
	}

	// get string values from an intents extra
	public int getExtrasInt(String extraName, Bundle extra) {

		int extraValue = 0;

		if (extra != null) {
			extraValue = extra.getInt(extraName);
		}

		return extraValue;
	}

	// get string values from an intents extra
	public String getExtrasString(String extraName, Bundle extra) {

		String extraValue = "";

		if (extra != null) {
			extraValue = extra.getString(extraName);
		}

		return extraValue;
	}

	// stop programm for some "time"
	public void getSomeSleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Log.e("GET SOME SLEEP", "" + e);
		}
	}

	// hashing a password with SHA-256
	public byte[] hashPassword(String stringToHash) {
		MessageDigest mDigest = null;

		try {
			mDigest = MessageDigest.getInstance("SHA-256");
			mDigest.update(stringToHash.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			Log.e("SHA-256", "ALGORITHM NOT FOUND");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Log.e("ENCODING", "NOT SUPPORTED");
		}

		return mDigest.digest();
	}

	// generate a random value in specific range
	private int intRangeRandom(int max, int min) {
		int i;
		Random r = new Random();
		;
		do {
			i = r.nextInt(max);
		} while (i < min || i > max);

		return i;
	}

	// get rid of the title and make fullscreen
	public void makeFullscreen() {

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	// create intent to open mail program
	public void sendEmail(Context context) {

		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL, new String[] { "cioalex@gmail.com" });
		i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
		i.putExtra(Intent.EXTRA_TEXT, "body of email");
		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(context, "There are no email clients installed.",
					Toast.LENGTH_SHORT).show();
		}
	}

	// cut strings in front and at the end of a string if exist
	public String stringWithoutSpaces(String string) {

		String newString = string;

		if (newString.substring(0, 1).equals(" ")) {
			newString = newString.substring(1, newString.length());
		}

		if (newString.substring(newString.length() - 1, newString.length())
				.equals(" ")) {
			newString = newString.substring(0, newString.length() - 1);
		}

		return newString;
	}

	public Intent switchView(Context c, String destinationClass) {

		Class ourClass;
		Intent ourIntent = null;
		try {
			ourClass = Class.forName("alexandru.ciocea." + destinationClass);
			ourIntent = new Intent(c, ourClass);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return ourIntent;
	}

}