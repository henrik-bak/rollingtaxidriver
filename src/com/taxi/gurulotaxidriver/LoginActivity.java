package com.taxi.gurulotaxidriver;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.taxi.gurulotaxidriver.model.Taxidriver;
import com.taxi.gurulotaxidriver.util.AlertDialogManager;
import com.taxi.gurulotaxidriver.util.ConnectionDetector;
import com.taxi.gurulotaxidriver.util.JsonResponse;
import com.taxi.gurulotaxidriver.util.ServerCommunication;
import com.taxi.gurulotaxidriver.util.ServerUtilities;

public class LoginActivity extends Activity {

	EditText license_field;
	EditText pass_field;
    AlertDialogManager alert = new AlertDialogManager();
	ConnectionDetector cd;
	Boolean isInternetPresent = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		isInternetPresent = cd.isConnectingToInternet();
		if (!isInternetPresent) {
			// Internet Connection is not present
			alert.showAlertDialog(LoginActivity.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		Button login = (Button) findViewById(R.id.login_btn);
		Button register = (Button) findViewById(R.id.register_btn);

		license_field = (EditText) findViewById(R.id.login_license);
		pass_field = (EditText) findViewById(R.id.login_pass_field);


		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String license = license_field.getText().toString();
				String pwd = pass_field.getText().toString();
				
				new LoginSrv(getApplicationContext(), license, pwd).execute();
				
			}
		});

		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(i);

			}
		});

	}

	class LoginSrv extends AsyncTask<Void, Void, JsonResponse> {

		private Context context;
		private String licensenumber;
		private String password;

		public LoginSrv(Context context, String licensenumber, String password) {
			this.context = context;
			this.licensenumber = licensenumber;
			this.password = password;
		}

		@Override
		protected JsonResponse doInBackground(Void...voids ) {

			ServerCommunication comm = new ServerCommunication();

			Gson gson = new Gson();
			Log.d("MAUNIKA", "/driver/login?licensenumber="+licensenumber+"&password="+password);
			InputStream source = comm.postStream(ServerUtilities.SERVER_ADDRESS+"/driver/login?licensenumber="+licensenumber+"&password="+password, null);

			Reader reader = new InputStreamReader(source);

			JsonResponse response = gson.fromJson(reader, JsonResponse.class);

			return response;

		}

		@Override
		protected void onPostExecute(JsonResponse result) {
			super.onPostExecute(result);
			if (result.getStatus().equals("SUCCESS")) {
				Gson gson = new Gson();
				Taxidriver driver = gson.fromJson(result.getData().toString(), Taxidriver.class);

				Intent i = new Intent( LoginActivity.this, MainActivity.class);
				i.putExtra("driverId", driver.getIdTaxiDriver());
				startActivity(i);
			} else {
				Log.d("MAUNIKA", "failed");
				alert.showAlertDialog(LoginActivity.this, "Login failure",
	                    "Please check the submitted name and password, or register!", false);
			}
		}

	}
}
