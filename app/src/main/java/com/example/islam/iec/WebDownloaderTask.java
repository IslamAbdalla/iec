package com.example.islam.iec;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by islam on 10/3/16.
 */


public class WebDownloaderTask  extends AsyncTask<String, Void, String> {
    private static final String BASE_URL = "http://192.168.0.114/wp-json/iec-api/v1";
    WeakReference<Fragment> fragmentWeakReference;


    public WebDownloaderTask(Fragment fragment) {
       // super();
        fragmentWeakReference = new WeakReference<>(fragment);

    }

    @Override
    protected String doInBackground(String... urls) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                        .url(BASE_URL + "/get-events/")
                        .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            return e.toString();
        }
    return "Download failed";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("IEC", "onPostExecute: " + s);

        LatestEvents fragment = (LatestEvents) fragmentWeakReference.get();
        if (fragment != null) {
            Log.i("IEC", "onPostExecute: not null");
            fragment.clearEvents();


        }

    }


}
