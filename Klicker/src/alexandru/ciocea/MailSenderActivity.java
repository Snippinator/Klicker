package alexandru.ciocea;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MailSenderActivity extends Activity implements OnClickListener {

	MyFramework myFramework;
	Button send;
	EditText address;
	GMailSender mailsender;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initVars();
	}

	private void initVars() {

		address = (EditText) this.findViewById(R.id.address);
		send = (Button) this.findViewById(R.id.send);
		send.setOnClickListener(this);
		
		try {
			mailsender = new GMailSender("cioalex@gmail.com","Gs2btb4m");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.send:

			String mailAddress = address.getText().toString();
			new SendEmail().execute("cioalex@gmail.com");

			//onPause();
			break;
		}

	}

	class SendEmail extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				mailsender.sendMail("This is Subject", "This is Body",
						"cioalex@gmail.com", params[0]);

				return true;
			} catch (Exception e) {
				Log.e("SendMail", e.getMessage(), e);
				
			}
			return false;
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		myFramework.switchView(MailSenderActivity.this, "ActivityOverview");
		finish();

	}
}