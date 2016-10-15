package com.example.islam.iec;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by islam on 10/3/16.
 */


public class WebDownloaderTask  extends AsyncTask<String, Void, String> {
    private static final String BASE_URL = "http://192.168.43.155/wp-json/iec-api/v1";
    WeakReference<Fragment> fragmentWeakReference;
    WeakReference<Activity> activityWeakReference;
    final static public int GET_EVENTS = 0, GET_TICKETS = 1, LOG_IN = 2, REGISTER = 3, BOOK = 4;
    private int action;


    public WebDownloaderTask(Fragment fragment, int mAction) {
        fragmentWeakReference = new WeakReference<>(fragment);
        action = mAction;

    }
    public WebDownloaderTask(Activity activity, int mAction) {
        activityWeakReference = new WeakReference<>(activity);
        action = mAction;
    }

    @Override
    protected String doInBackground(String... urls) {
        String route;
        OkHttpClient client = new OkHttpClient();
        Request request;

        switch (action) {
            case GET_EVENTS:
                route = "/get-events/";
                break;
            case GET_TICKETS:
                route = "/ticket/";
                break;
            case LOG_IN:
                route = "/login/";
                break;
            case REGISTER:
                route = "/register/";
                break;
            case BOOK:
                route = "/book/";
                break;
            default:
                route = "/";
        }

        // GET request
        if (action == GET_EVENTS) {
            request = new Request.Builder()
                    .url(BASE_URL + route)
                    .build();
        } else
        // POST request
        if (action == LOG_IN){
            request = new Request.Builder()
                    .url(BASE_URL + route)
                    .method("POST", RequestBody.create(null, new byte[0]))
                    .header("Authorization", "Basic d29yZHByZXNzdXNlcjozSWVPSE9SSk9lQ1FxXiVvVVY=")
                    .build();

        } else {
            request = new Request.Builder()
                    .url(BASE_URL + route)
                    .build();
        }
        try {
            Log.i("IEC", "doInBackground: "+ request.toString());
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
            else
            Log.i("IEC", "doInBackground: "+response.body().string());
        } catch (Exception e) {
            return e.toString();
        }
    return "Download failed";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("IEC", "onPostExecute: " + s);
        JSONObject response = null;
        try {
            response = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        switch (action) {
            case GET_EVENTS:
                LatestEvents fragment = (LatestEvents) fragmentWeakReference.get();
                if (fragment != null) {
                    try {
                        if (response != null && 0 == response.optInt("status")) {
                            fragment.clearEvents();
                            JSONArray eventsJSONArray = response.optJSONArray("events");
                            ArrayList<Event> latestEventsList = new ArrayList<>();
                            latestEventsList = parseEvents(eventsJSONArray, latestEventsList);

                            fragment.setEvents(latestEventsList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;


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
                    event.optString("image"),
                    (index == 0)
            ));
        }
        return latestEventsList;
    }
}
