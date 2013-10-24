package com.taxi.gurulotaxidriver.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;

import com.google.gson.Gson;
import com.taxi.gurulotaxidriver.LoginActivity;
import com.taxi.gurulotaxidriver.MainActivity;
import com.taxi.gurulotaxidriver.model.Taxiorder;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {
	
	private JsonResponse response;
	
	private String driverId;

	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "GURULOTAXI");
		//Acquire the lock
		wl.acquire();

		new PollOrderSrv(driverId).execute();
		
		if (response.getStatus().equals("SUCCESS")) {
			Gson gson = new Gson();
			Taxiorder order = gson.fromJson(response.getData().toString(), Taxiorder.class);
			
			Intent i =  new Intent(context, MainActivity.class);
			i.putExtra("Taxiorder", order);
			context.startActivity(i);
			
		}

		//Release the lock
		wl.release();

	}
	public void SetAlarm(Context context)
	{
		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		//After after 30 seconds
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 5 , pi); 
	}

	public void CancelAlarm(Context context)
	{
		Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}
	
	
	private class PollOrderSrv extends AsyncTask<Void, Void, JsonResponse>{

		private String driverid;

		public PollOrderSrv(String driverid) {
			this.driverid = driverid;
		}

		@Override
		protected JsonResponse doInBackground(Void... params) {
			JsonResponse resp = null;
			try {
				ServerCommunication comm = new ServerCommunication();
				InputStream source = comm.postStream(ServerUtilities.SERVER_ADDRESS+"/order/getOrder?id="+driverid,null);
        
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
			
			Log.d("MAUNIKA", result.toString());
			response=result;
		}
	}	// End of class SearchSrv here
	
	private class OrderSrv extends AsyncTask<Void, Void, JsonResponse>{

		private String orderId;
		private String action;

		public OrderSrv(String orderId, String action) {
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
			
			Log.d("MAUNIKA", result.toString());
			response=result;
		}
	}	// End of class SearchSrv here

}
