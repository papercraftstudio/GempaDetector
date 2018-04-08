package com.dytra.quakerx;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.FileNotFoundException;

public class MainActivity extends Activity {

    private GempaAdapter xAdapter;
    private ListView sitesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("StackSites", "OnCreate()");
        setContentView(R.layout.activity_main);
        sitesList = findViewById(R.id.sitesList);
        if (isNetworkAvailable()) {
            Log.i("StackSites", "starting download Task");
            SitesDownloadTask download = new SitesDownloadTask();
            download.execute();
        } else {
            xAdapter = new GempaAdapter(getApplicationContext(), -1, SitesXmlPullParser.getStackSitesFromFile(MainActivity.this));
            sitesList.setAdapter(xAdapter);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class SitesDownloadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Downloader.DownloadFromUrl(
                        "http://data.bmkg.go.id/gempaterkini.xml",
                        openFileOutput("datagempa.xml", Context.MODE_PRIVATE)
                );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            xAdapter = new GempaAdapter(
                    MainActivity.this,
                    -1,
                    SitesXmlPullParser.getStackSitesFromFile(MainActivity.this)
            );
            sitesList.setAdapter(xAdapter);
            Log.i("StackSites", "adapter size = " + xAdapter.getCount());
        }
    }
}