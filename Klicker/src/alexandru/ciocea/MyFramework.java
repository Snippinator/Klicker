package alexandru.ciocea;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class MyFramework extends Activity{
	
	public MyFramework(){
		
	}
	
	public void closeKeyboard(TextView tv){
		
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(tv.getWindowToken(), 0);
	}
	

	public void createDialogNeutral(Context c, String title, String message){
		
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
	
	
	public void getSomeSleep(int time){
		try{
			Thread.sleep(time);
		}catch(InterruptedException e){
			e.printStackTrace();
			Log.e("GET SOME SLEEP", ""  +e);
		}
	}
		
	public byte[] hashPassword(String stringToHash){
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
	
	public Intent switchView(Context c, String destinationClass){
		
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

/*
RelativeLayout.LayoutParams  layoutParam = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
		LayoutParams.WRAP_CONTENT);
if(rowCount < 1){
	layoutParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
	rowCount++;
}else{
	layoutParam.addRule(RelativeLayout.BELOW, lastButtonId);
}

lastButtonId = bt.getId();
*/
