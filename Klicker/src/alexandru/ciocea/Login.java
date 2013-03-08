package alexandru.ciocea;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity implements OnClickListener{
	
	Button login, register;
	CheckBox saveData;
	EditText username, password;
	FontClass fontClass = null;
	MyFramework myFramework = null;
	TextView header;		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		initVars();     	    
	}
	
	private void initVars(){		
		
		fontClass = new FontClass();
		myFramework = new MyFramework();
		
		header = (TextView) findViewById(R.id.tvLoginHeader);
		fontClass.setFont(header);
		username = (EditText) findViewById(R.id.etLoginUsername);
		fontClass.setFont(username);
		password = (EditText) findViewById(R.id.etLoginPassword);
		fontClass.setFont(password);
		saveData = (CheckBox) findViewById(R.id.cbLoginKeepUserInformation);
		fontClass.setFont(saveData);
		login = (Button) findViewById(R.id.btLoginLogin);
		fontClass.setFont(login);
		login.setOnClickListener(this);
		register = (Button) findViewById(R.id.btLoginRegister);
		fontClass.setFont(register);
		register.setOnClickListener(this);
	}

	public void onClick(View v) {
		
		switch(v.getId()){
			
		case R.id.btLoginLogin:
			if(!username.getText().equals("") && !password.getText().equals("")){
				startActivity(myFramework.switchView(Login.this, "ActivityOverview"));
			}else{
				myFramework.createDialogNeutral(Login.this, "Eingabe Fehlerhaft", "Felder leer gelassen");
			}
			
			break;
		case R.id.btLoginRegister:
			startActivity(myFramework.switchView(Login.this, "Register"));	
			
			break;
		}
		
	}

}
