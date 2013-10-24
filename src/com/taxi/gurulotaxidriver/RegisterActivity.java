package com.taxi.gurulotaxidriver;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.taxi.gurulotaxidriver.dto.TaxiDTO;
import com.taxi.gurulotaxidriver.dto.TaxidriverDTO;
import com.taxi.gurulotaxidriver.model.Taxidriver;
import com.taxi.gurulotaxidriver.util.AlertDialogManager;
import com.taxi.gurulotaxidriver.util.ConnectionDetector;
import com.taxi.gurulotaxidriver.util.JsonResponse;
import com.taxi.gurulotaxidriver.util.ServerCommunication;
import com.taxi.gurulotaxidriver.util.ServerUtilities;

public class RegisterActivity  extends Activity {

	Button back;
	Button registration;
	EditText et_license;
	EditText et_pwd1;
	EditText et_pwd2;
	EditText et_phone;
	EditText et_name;
	EditText et_size;
	EditText et_companyName;
	CheckBox cb_airconditioned;
	Integer parsedSize;
	Integer parsedLicense;

	AlertDialogManager alert = new AlertDialogManager();
	ConnectionDetector cd;
	Boolean isInternetPresent = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		isInternetPresent = cd.isConnectingToInternet();
		if (!isInternetPresent) {
			// Internet Connection is not present
			alert.showAlertDialog(RegisterActivity.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		back = (Button) findViewById(R.id.reg_back);
		registration = (Button) findViewById(R.id.reg_reg_btn);
		et_license = (EditText) findViewById(R.id.reg_license);
		et_pwd1 = (EditText) findViewById(R.id.reg_pwd1);
		et_pwd2 = (EditText) findViewById(R.id.reg_pwd2);
		et_phone = (EditText) findViewById(R.id.reg_phone);

		et_name = (EditText) findViewById(R.id.reg_name);
		et_size = (EditText) findViewById(R.id.reg_size);
		et_companyName = (EditText) findViewById(R.id.reg_company);
		cb_airconditioned = (CheckBox) findViewById(R.id.reg_airconditioned);



		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();

			}
		});


		registration.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (validateInput()) {
					TaxidriverDTO driverDTO = new TaxidriverDTO(et_phone.getText().toString(), et_pwd1.getText().toString(), et_pwd2.getText().toString(), parsedLicense,
							new TaxiDTO(et_name.getText().toString(), parsedSize, cb_airconditioned.isChecked(), et_companyName.getText().toString()));
					RegDriverSrv reg = new RegDriverSrv(getApplicationContext(), driverDTO);
					reg.execute();
				}

			}
		});
	}

	private boolean validateInput() {
		clearErrors();
		boolean ret = false;

		boolean validationerror = false;

		String licenseNumber = et_license.getText().toString();
		String pwd1string = et_pwd1.getText().toString();
		String pwd2string = et_pwd2.getText().toString();
		String phonenumber = et_phone.getText().toString();

		String name = et_name.getText().toString();
		String companyName = et_companyName.getText().toString();
		String size = et_size.getText().toString();		

		if (licenseNumber.equals("")) {
			et_license.setError("This field can not be empty!");
			validationerror=true;
		}
		if (pwd1string.equals("")) {
			et_pwd1.setError("This field can not be empty!");
			validationerror=true;
		}
		if (pwd2string.equals("")) {
			et_pwd2.setError("This field can not be empty!");
			validationerror=true;
		}
		if (!pwd1string.equals(pwd2string)) {
			et_pwd1.setError("Password mismatch!");
			validationerror=true;
		}
		if (phonenumber.equals("")) {
			et_phone.setError("This field can not be empty!");
			validationerror=true;
		}
		if (name.equals("")) {
			et_name.setError("This field can not be empty!");
			validationerror=true;
		}
		if (companyName.equals("")) {
			et_companyName.setError("This field can not be empty!");
			validationerror=true;
		}
		if (size.equals("")) {
			et_size.setError("This field can not be empty!");
			validationerror=true;
		}

		try {
			parsedSize = Integer.parseInt(size);
		} catch (NumberFormatException ex) {
			et_size.setError("Only number!");
		}
		
		try {
			parsedLicense = Integer.parseInt(licenseNumber);
		} catch (NumberFormatException ex) {
			et_size.setError("Only number!");
		}

		if (validationerror) {
			return ret;
		} else {	
			ret = true;
			return ret;
		}
	}

	private void clearErrors() {
		et_license.setError(null);
		et_name.setError(null);
		et_companyName.setError(null);
		et_pwd1.setError(null);
		et_pwd2.setError(null);
		et_phone.setError(null);
		et_size.setError(null);
	}

	class RegDriverSrv extends AsyncTask<Void, Void, JsonResponse> {

		private ProgressDialog dialog;
		private Context context;
		TaxidriverDTO driverDTO;

		public RegDriverSrv(Context context, TaxidriverDTO driverDTO) {
			this.context = context;
			this.driverDTO = driverDTO;

		}

		@Override
		protected JsonResponse doInBackground(Void...voids ) {

			ServerCommunication comm = new ServerCommunication();

			Gson gson = new Gson();

			String json_string = gson.toJson(driverDTO);
			Log.d("MAUNIKA", json_string);

			InputStream source = comm.postStream(ServerUtilities.SERVER_ADDRESS+"/driver/register", json_string);


			Reader reader = new InputStreamReader(source);

			JsonResponse response = gson.fromJson(reader, JsonResponse.class);

			return response;

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(context);
			dialog.setCancelable(false);
			dialog.setMessage("Loading..");
			dialog.isIndeterminate();
			dialog.show();
		}

		@Override
		protected void onPostExecute(JsonResponse result) {
			super.onPostExecute(result);
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			if (result.getStatus().equals("SUCCESS")) {

				Gson gson = new Gson();
				
				Intent i = new Intent( RegisterActivity.this, MainActivity.class);
				Taxidriver driver = gson.fromJson(result.getData().toString(), Taxidriver.class);
				i.putExtra("driverId", driver.getIdTaxiDriver());
				startActivity(i);
			} else {
				Log.d("MAUNIKA", "failed");
				Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
