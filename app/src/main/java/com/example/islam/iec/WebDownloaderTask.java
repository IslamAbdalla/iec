package com.example.islam.iec;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by islam on 10/3/16.
 */


public class WebDownloaderTask  extends AsyncTask<String, Void, String> {
    private static final String BASE_URL = "http://192.168.43.155/wp-json/iec-api/v1";
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
            fragment.clearEvents();
            try {
                JSONObject response = new JSONObject(s);
                if (0 == response.optInt("status") ) {
                    JSONArray eventsJSONArray = response.optJSONArray("events");
                    ArrayList<Event> latestEventsList = new ArrayList<>();
                    latestEventsList = parseEvents(eventsJSONArray, latestEventsList);

                    fragment.setEvents(latestEventsList);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private ArrayList<Event>  parseEvents(JSONArray eventsJSONArray, ArrayList<Event> latestEventsList) throws JSONException {
        int separator = 1;
        for (int index = 0; index < eventsJSONArray.length(); index++) {
            JSONObject event = eventsJSONArray.getJSONObject(index);
            if (separator == 1 && !event.optBoolean("upcoming")){
                latestEventsList.add(new Event("Separator"));
                separator = 0;
            }
            latestEventsList.add(new Event(event.optString("name"),
                    event.optString("location"),
                    event.optString("location"),
                    event.optString("date"),
                    event.optString("details"),
                    R.drawable.startup_weekend_khartoum,
                    (index == 0)
            ));
        }
        return latestEventsList;
    }


}
