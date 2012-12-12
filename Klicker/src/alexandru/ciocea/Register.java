package alexandru.ciocea;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends Activity implements OnClickListener{

	Button register, backToLogin;
	EditText username, password, email;
	TextView header;
	MessageDigest mDigest;
	FontClass fontClass = null;
	MyFramework myFramework = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		initVars();
		
	}

	public void initVars(){
		
		fontClass = new FontClass();
		myFramework = new MyFramework();
		
		
		header = (TextView) findViewById(R.id.tvRegHeader);
		fontClass.setFont(header);
		username = (EditText) findViewById(R.id.etRegUsername);
		fontClass.setFont(username);
		password = (EditText) findViewById(R.id.etRegPassword);
		fontClass.setFont(password);
		email = (EditText) findViewById(R.id.etRegEmail);
		fontClass.setFont(email);
		register = (Button) findViewById(R.id.btRegFinish);
		fontClass.setFont(register);
		register.setOnClickListener(this);
		backToLogin = (Button) findViewById(R.id.btRegBack);
		fontClass.setFont(backToLogin);
		backToLogin.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		
		switch(v.getId()){ 
		
		case R.id.btRegFinish:
						
			String username = this.username.getText().toString();
			String password = this.password.getText().toString();
			String email = this.email.getText().toString();
			
			if(!username.equals("") && !password.equals("") && !email.equals("")){
				//Hash the password		
				
				byte[] hashedPassword = myFramework.hashPassword(password);
				
				ActivityDB entry = new ActivityDB(Register.this);
				entry.open();
				entry.saveUser(username, hashedPassword, email);
				entry.close();
				
				myFramework.createDialogNeutral(Register.this, "Ihre Anmeldung...", "...war erfolgreich! \n Bitte melden Sie sich nun mit Ihren Daten an.");
				
				startActivity(myFramework.switchView(Register.this, "Login"));
			}else{
				
				myFramework.createDialogNeutral(this, "Achtung!", "Sie haben nicht alle Felder ausgefüllt");
			}
					
			break;
		
		case R.id.btRegBack:
			startActivity(myFramework.switchView(Register.this, "Login"));
		}
		
		
	}
	
}
