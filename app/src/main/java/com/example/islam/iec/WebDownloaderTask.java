package com.example.islam.iec;

import android.os.AsyncTask;
import android.util.Log;

import com.loopj.android.http.*;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by islam on 10/3/16.
 */


public class WebDownloaderTask  extends AsyncTask<String, Void, String> {
    private static final String BASE_URL = "http://192.168.43.155/wp-json/iec-api/v1";

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
        Log.i("IEC", "onPostExecute: " + s);
        super.onPostExecute(s);

    }


}
