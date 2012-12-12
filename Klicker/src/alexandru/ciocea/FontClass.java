package alexandru.ciocea;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class FontClass {
	
	Typeface typeface;
	
	public FontClass(){
	
		return;		
	}
	
	public void setFont(TextView element){
		Context context = element.getContext();	    
	    element.setTypeface(getFontFromAsset(context));	    
	}
	
	private Typeface getFontFromAsset(Context context){		
		typeface = Typeface.createFromAsset(context.getAssets(), "beware.ttf");
		return typeface;
	}

}
