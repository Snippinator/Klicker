package alexandru.ciocea;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity{

	String classes[] = {"DBTest"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//get rid of the title and make fullscreen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		setListAdapter(new ArrayAdapter<String>(Menu.this, 
				android.R.layout.simple_list_item_1, classes));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		//get selected class and start this activity
		String selectedClass = classes[position];
		
		
		try {
			
			Class ourClass = Class.forName("alexandru.ciocea." + selectedClass);
			Intent ourIntent = new Intent(Menu.this, ourClass);
			startActivity(ourIntent);
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			System.err.println(e);
		}		
	}

	
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
	
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.cool_menu, menu);
		
		return true;
	}

	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// deleted super from this method
		
		switch(item.getItemId()){
		
		
		case R.id.aboutUs:
			Intent i = new Intent("alexandru.ciocea.ABOUT");
			startActivity(i);
			break;
		
		case R.id.preferences:
			Intent prefs = new Intent("alexandru.ciocea.PREFS");
			startActivity(prefs);
			break;
			
		case R.id.exit:
			finish();
			break;
		}
		
		//return anything, not important
		return false;
		
	}



}
