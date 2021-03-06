package com.taxi.gurulotaxidriver;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.taxi.gurulotaxidriver.model.Taxiorder;
import com.taxi.gurulotaxidriver.util.AlertDialogManager;
import com.taxi.gurulotaxidriver.util.JsonResponse;
import com.taxi.gurulotaxidriver.util.ServerCommunication;
import com.taxi.gurulotaxidriver.util.ServerUtilities;

public class OrderActivity extends Activity{

	Taxiorder order;
	Button accept;
	Button decline;

	AlertDialogManager alert = new AlertDialogManager();
	TextView tv_fname;
	TextView tv_lname;
	TextView tv_location;
	TextView tv_phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if(extras == null) {
				order= null;
			} else {
				order= (Taxiorder) extras.getSerializable("Taxiorder");
			}
		} else {
			order= (Taxiorder) savedInstanceState.getSerializable("Taxiorder");
		}

		tv_fname = (TextView) findViewById(R.id.tv_fname);
		tv_lname = (TextView) findViewById(R.id.tv_lname);
		tv_location = (TextView) findViewById(R.id.tv_location);
		tv_phone = (TextView) findViewById(R.id.tv_phone);

		tv_fname.setText(order.getClientuseridClientUser().getFirstName());
		tv_fname.setText(order.getClientuseridClientUser().getLastName());
		tv_fname.setText(order.getClientuseridClientUser().getPhoneNumber());
		tv_fname.setText(order.getLocation());

		accept = (Button) findViewById(R.id.btn_accept);
		decline = (Button) findViewById(R.id.btn_decline);

		initOnClickListeners();
	}

	private void initOnClickListeners() {

		accept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new OrderSrv(order.getIdOrder(), "ACCEPT").execute();

			}
		});

		decline.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new OrderSrv(order.getIdOrder(), "DECLINE").execute();
			}
		});
	}

	private class OrderSrv extends AsyncTask<Void, Void, JsonResponse>{

		private Integer orderId;
		private String action;

		public OrderSrv(Integer orderId, String action) {
			this.orderId = orderId;
			this.action = action;
		}

		@Override
		protected JsonResponse doInBackground(Void... params) {
			JsonResponse resp = null;
			try {
				ServerCommunication comm = new ServerCommunication();
				InputStream source;
				if (action.equals("ACCEPT")) {
					source = comm.postStream(ServerUtilities.SERVER_ADDRESS+"/order/acceptOrder?id="+orderId,null);
				} else {
					source = comm.postStream(ServerUtilities.SERVER_ADDRESS+"/order/declineOrder?id="+orderId,null);
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
		protected void onPostExecute(JsonResponse result) {
			super.onPostExecute(result);
			if (action.equals("ACCEPT"))
				if (result.getStatus().equals("SUCCESS")) {
					Toast.makeText(getApplicationContext(), "Order accepted successfully!",
							Toast.LENGTH_LONG).show();
				}

			if (action.equals("ACCEPT")) {
				if (result.getStatus().equals("SUCCESS")) {
					Toast.makeText(getApplicationContext(), "Order declined successfully!",
							Toast.LENGTH_LONG).show();
				}

				Intent i = new Intent(OrderActivity.this, MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT );
				startActivity(i);
			}
		}
	}	// End of class SearchSrv here

}
