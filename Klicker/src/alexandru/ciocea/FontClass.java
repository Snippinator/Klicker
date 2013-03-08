package alexandru.ciocea;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

public class FontClass {
	
	Typeface typeface;
	SharedPreferences getPrefs;
	String fontName;
	
	public FontClass(){
			
	}	
	
	public void setFont(TextView element){
		
		//get selected font preferences
		getPrefs = PreferenceManager.getDefaultSharedPreferences(element.getContext());
		fontName = getPrefs.getString("list", "default");
		
		if(!fontName.equals("default")){
			
			Context context = element.getContext();	    
			element.setTypeface(getFontFromAsset(context));
		}
	}
	
	private Typeface getFontFromAsset(Context context){		
		typeface = Typeface.createFromAsset(context.getAssets(), fontName);
		return typeface;
	}

}
