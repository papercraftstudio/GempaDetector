package com.dytra.quakerx;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends Activity {

	private GempaAdapter xAdapter;
	private ListView sitesList;
	private View view;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("StackSites", "OnCreate()");
		setContentView(R.layout.activity_main);

		//refference
		sitesList = (ListView) findViewById(R.id.sitesList);

		//cek koneksi
		if(isNetworkAvailable()){
			Log.i("StackSites", "starting download Task");
			SitesDownloadTask download = new SitesDownloadTask();
			download.execute();
		}else{
			xAdapter = new GempaAdapter(getApplicationContext(), -1, SitesXmlPullParser.getStackSitesFromFile(MainActivity.this));
			sitesList.setAdapter(xAdapter);
		}

	}

	//Helper method to determine if Internet connection is available.
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager
				= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	/*
     * AsyncTask that will download the xml file for us and store it locally.
     * After the download is done we'll parse the local file.
     */
	private class SitesDownloadTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			//Download the file
			try {
				Downloader.DownloadFromUrl("http://data.bmkg.go.id/gempaterkini.xml", openFileOutput("datagempa.xml", Context.MODE_PRIVATE));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result){
			//setup our Adapter and set it to the ListView.
			xAdapter = new GempaAdapter(MainActivity.this, -1, SitesXmlPullParser.getStackSitesFromFile(MainActivity.this));
			sitesList.setAdapter(xAdapter);
			Log.i("StackSites", "adapter size = "+ xAdapter.getCount());
		}
	}
}
