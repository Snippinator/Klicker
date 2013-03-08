package alexandru.ciocea;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class MyOnClickListener implements OnClickListener {

	MyFramework myFramework = new MyFramework();
	Context context;
	String id;
	UserInformation userInfo = UserInformation.getInstance();
	
	public MyOnClickListener(Context context, String id){
		this.context = context;
		this.id = id;
	}
	
	public void onClick(View v){
		
		//myFramework.createDialogNeutral(c, "TEST", "myOnClickListener");
		ActivityDB entry = new ActivityDB(context);
		entry.open();
		entry.saveExecution(userInfo.getUserId(), id);
		entry.close();
		
		
	}
	
}
