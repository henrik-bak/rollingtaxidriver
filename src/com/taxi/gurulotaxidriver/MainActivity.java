package com.taxi.gurulotaxidriver;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.taxi.gurulotaxidriver.util.AlarmManagerBroadcastReceiver;
import com.taxi.gurulotaxidriver.util.AlertDialogManager;
import com.taxi.gurulotaxidriver.util.ConnectionDetector;
import com.taxi.gurulotaxidriver.util.GPSTracker;
import com.taxi.gurulotaxidriver.util.JsonResponse;
import com.taxi.gurulotaxidriver.util.ResponseCodes;
import com.taxi.gurulotaxidriver.util.ServerCommunication;
import com.taxi.gurulotaxidriver.util.ServerUtilities;

public class MainActivity extends Activity {

	Button btn_free;
	Button btn_occupied;
	private static Integer selectedButton=-1;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	AlertDialogManager alert = new AlertDialogManager();
	GPSTracker gps;
	private String location;
	private String driverId;
	private TextView infoTV;
	Gson gson = new Gson();
	AlarmManagerBroadcastReceiver am;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		isInternetPresent = cd.isConnectingToInternet();
		if (!isInternetPresent) {
			// Internet Connection is not present
			alert.showAlertDialog(MainActivity.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		gps = new GPSTracker(getApplicationContext());

		// check if GPS location can get
		/*
        if (gps.canGetLocation()) {
            location=gps.getLatitude()+";"+gps.getLongitude();
        } else {
            // Can't get user's current location
            alert.showAlertDialog(MainActivity.this, "GPS Status",
                    "Couldn't get location information. Please enable GPS",
                    false);
            gps.showSettingsAlert();
            // stop executing code by return
            return;
        }
		 */
		location = "testlocation";
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if(extras == null) {
				driverId= null;
			} else {
				driverId= extras.getString("driverId");
			}
		} else {
			driverId= (String) savedInstanceState.getSerializable("driverId");
		}

		am = new AlarmManagerBroadcastReceiver();
		btn_free = (Button) findViewById(R.id.btn_free);
		btn_occupied = (Button) findViewById(R.id.btn_occupied);
		btn_occupied.setEnabled(false);
		infoTV = (TextView) findViewById(R.id.infoTV);

		initOnClickListeners();
	}

	private void initOnClickListeners() {

		btn_free.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectedButton=1;
				btn_occupied.setEnabled(true);
				btn_free.setEnabled(false);

				new StatusSrv(getApplicationContext(), "update",  driverId, location);
			}
		});

		btn_occupied.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectedButton=2;
				btn_occupied.setEnabled(false);
				btn_free.setEnabled(true);

				new StatusSrv(getApplicationContext(), "delete", driverId, location);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class StatusSrv extends AsyncTask<Void, Void, JsonResponse>{

		private ProgressDialog dialog;
		private Context context;
		private String driverid;
		private String location;
		private String action;

		public StatusSrv(Context context, String action, String driverid, String location) {
			this.context = context;
			this.driverid = driverid;
			this.location = location;
			this.action = action;
		}

		@Override
		protected JsonResponse doInBackground(Void... params) {
			JsonResponse resp = null;
			try {
				ServerCommunication comm = new ServerCommunication();
				InputStream source;

				if (action.equals("update")) {
					source = comm.postStream(ServerUtilities.SERVER_ADDRESS+"/freetaxi/update?taxidriverid="+driverid+"&location="+location, null);
				} else {
					source = comm.postStream(ServerUtilities.SERVER_ADDRESS+"/freetaxi/delete?taxidriverid="+driverid, null);
				}

				Gson gson = new Gson();

				Reader reader = new InputStreamReader(source);

				resp = gson.fromJson(reader, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
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

			if (action.equals("update")) { 
				if (result.getStatus().equals("FAILED")) {
					infoTV.setText("Error! Error code: "+result.getMessage());
					return;
				}

				infoTV.setText("Success! Waiting for new order...");

				if(am != null){
					am.setDriverId(driverId);
					am.SetAlarm(getApplicationContext());
				}
			}

			if (action.equals("delete")) { 
				if (result.getStatus().equals("FAILED")) {
					infoTV.setText("Error! Error code: "+result.getMessage());
					return;
				}

				infoTV.setText("Success! Not accepting orders.");
				if (am != null) {
					am.CancelAlarm(getApplicationContext());
				}
			}

		}
	}	// End of class SearchSrv here

}
