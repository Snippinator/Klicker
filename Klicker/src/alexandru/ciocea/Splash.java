package alexandru.ciocea;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Splash extends Activity{

	//MediaPlayer ourSong;
	//SharedPreferences getPrefs;
	MyFramework myFramework = null;
	FontClass fontClass = null;
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		initVars();
		/*
		init sound and start playin
		ourSong = MediaPlayer.create(Splash.this, R.raw.sound);
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean music = getPrefs.getBoolean("checkbox", true);
		
		
		if(music == true){
			ourSong.start();
		}
		*/		
		
		//Create a Thread that starts an intent after timer is over
		Thread timer = new Thread(){
			
			@Override
			public void run(){
				
				try{
					sleep(1000);					
				}catch(InterruptedException e){
					//e.printStackTrace();
				}finally{
					//the reference of the Intent is the same as the one in the manifest
				/*
					Intent openStartingPoint = new Intent("alexandru.ciocea.");
					startActivity(openStartingPoint);
				*/
					startActivity(myFramework.switchView(Splash.this, "ActivityOverview"));
				}
			}
		};		
		timer.start();
	}
	
	private void initVars(){
		
		fontClass= new FontClass();
		myFramework = new MyFramework();
		
	}

	@Override
	protected void onPause() {
		
		super.onPause();
		//end this activity
		//ourSong.stop();
		finish();
	}	
}
