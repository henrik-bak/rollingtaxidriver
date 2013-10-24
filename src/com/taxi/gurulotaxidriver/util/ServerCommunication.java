package com.taxi.gurulotaxidriver.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class ServerCommunication {

	public InputStream retrieveStream(String url) {

		DefaultHttpClient client = new DefaultHttpClient();
		
		HttpParams httpParameters = new BasicHttpParams();
		int timeoutConnection = 5;
		int timeoutSocket = 10;
		
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection * 1000);
		HttpConnectionParams.setSoTimeout        (httpParameters, timeoutSocket * 1000);

		HttpGet getRequest = new HttpGet(url);

		try {

			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.w(getClass().getSimpleName(), "Error " + statusCode
						+ " for URL " + url);
				HttpEntity exceptionEntity = new StringEntity("Server exception");
				return exceptionEntity.getContent();
			}

			HttpEntity getResponseEntity = getResponse.getEntity();
			return getResponseEntity.getContent();

		} catch (IOException e) {
			getRequest.abort();
			Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
		}

		return null;

	}
	
	public InputStream postStream(String url, String json_string) {

		DefaultHttpClient client = new DefaultHttpClient();

		HttpPost post = new HttpPost(url);

		try {
			
			if (json_string != null && !json_string.equals("")) {
				post.setHeader("Content-Type", "application/json");
				post.setEntity(new StringEntity(json_string));
			}
			
			HttpResponse postresponse = client.execute(post);
			final int statusCode = postresponse.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.w(getClass().getSimpleName(), "Error " + statusCode
						+ " for URL " + url);
				HttpEntity exceptionEntity = new StringEntity("Server exception");
				return exceptionEntity.getContent();
			}

			HttpEntity postResponseEntity = post.getEntity();
			return postResponseEntity.getContent();

		} catch (IOException e) {
			post.abort();
			Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
		}

		return null;

	}
	
}
