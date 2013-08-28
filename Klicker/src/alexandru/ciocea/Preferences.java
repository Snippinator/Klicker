package alexandru.ciocea;

import java.security.Key;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity{
	
	CheckBoxPreference cbPref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		//because the preferences has a specific view its called
		//with addPreferences instead of setContentView
		addPreferencesFromResource(R.xml.prefs);
		initVars();
	}

	private void initVars(){
		
		//cbPref = (CheckBoxPreference) findPreference();
	}
}
