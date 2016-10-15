package com.example.islam.iec;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;

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
    private String username, password;
    private ProgressDialog progressDialog;


    public WebDownloaderTask(Fragment fragment, int mAction) {
        fragmentWeakReference = new WeakReference<>(fragment);
        action = mAction;

    }
    public WebDownloaderTask(Activity activity, int mAction) {
        activityWeakReference = new WeakReference<>(activity);
        action = mAction;
    }

    @Override
    protected void onPreExecute() {
        switch (action){
            case LOG_IN:
                Activity loginActivity = activityWeakReference.get();
                if (loginActivity != null) {
                    progressDialog = ProgressDialog.show(loginActivity, "Signing in", "Please wait");
                    EditText usernameEditText = (EditText) loginActivity.findViewById(R.id.username_input);
                    EditText passwordEditText = (EditText) loginActivity.findViewById(R.id.password_input);
                    if (usernameEditText != null) {
                        username = usernameEditText.getText().toString();
                        password = passwordEditText.getText().toString();
                    }

                }

        }

    }

    @Override
    protected String doInBackground(String... urls) {
        String route;
        OkHttpClient client = new OkHttpClient();
        Request request;
        request = new Request.Builder()
                .url(BASE_URL )
                .build();

        switch (action) {
            case GET_EVENTS:
                route = "/get-events/";
                request = new Request.Builder()
                        .url(BASE_URL + route)
                        .build();
                break;
            case GET_TICKETS:
                route = "/ticket/";
                break;
            case LOG_IN:
                route = "/login/";
                request = new Request.Builder()
                        .url(BASE_URL + route)
                        .method("POST", RequestBody.create(null, new byte[0]))
                        //.header("Authorization", "Basic "+ Base64.encodeToString("wordpressuser:3IeOHORJOeCQq^%oUV".getBytes(),Base64.NO_WRAP))
                        .header("Authorization", "Basic "+ Base64.encodeToString((username + ":" + password).getBytes(),Base64.NO_WRAP))
                        .build();
                break;
            case REGISTER:
                route = "/register/";
                break;
            case BOOK:
                route = "/book/";
                break;
            default:
                route = "/";
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
            case LOG_IN:
                if (response != null && 0 == response.optInt("status")) {
                    JSONObject userJSON = response.optJSONObject("user");
                    User user = parseUser(userJSON);
                    if (user != null) {
                        Login loginActivity = (Login) activityWeakReference.get();
                        if (loginActivity != null) {
                            PrefManager prefManager = new PrefManager(loginActivity);
                            prefManager.setUser(user);
                            prefManager.setIsLoggedIn(true);
                            loginActivity.completeLogin();
//                            Log.i("IEC", "onPostExecute: prefManager " + prefManager.isFirstTimeLaunch());
                        }
                    }
                }
                else {
                    // TODO: Handle failed login attempt
                    return;
                }

                progressDialog.dismiss();
        }

    }

    private User parseUser(JSONObject userJSON) {
        try {
            return new User(
                    userJSON.getString("name"),
                    username,
                    password,
                    userJSON.getString("address"),
                    userJSON.getString("email"),
                    userJSON.getString("job"),
                    userJSON.getString("phone")
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
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
