package com.example.islam.iec;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private User registerUser;
    final static public String TAG = "IEC";


    public WebDownloaderTask(Fragment fragment, int mAction) {
        // If called from a fragment, such is the case when action is get events.
        fragmentWeakReference = new WeakReference<>(fragment);
        action = mAction;

    }
    public WebDownloaderTask(Activity activity, int mAction) {
        // If called from an activity, such is the case when action is sign in, register.
        activityWeakReference = new WeakReference<>(activity);
        action = mAction;
    }

    @Override
    protected void onPreExecute() {

        // Mainly to show the progress dialog
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
                        Log.d(TAG, "onPreExecute: Got username");
                    }

                } else {
                    Log.e("IEC", "onPreExecute: loginActivity is null." );
                }
                break;
            case REGISTER:
                Activity signUpActivity = activityWeakReference.get();
                registerUser = new User("","","","","","","");
                if (signUpActivity != null) {
                    progressDialog = ProgressDialog.show(signUpActivity, "Signing up", "Please wait");
                    EditText username = (EditText) signUpActivity.findViewById(R.id.username_input);
                    EditText phone = (EditText) signUpActivity.findViewById(R.id.phone_input);
                    EditText password = (EditText) signUpActivity.findViewById(R.id.password_input);

                    registerUser.setUsername(username.getText().toString());
                    registerUser.setPhone(phone.getText().toString());
                    registerUser.setPassword(password.getText().toString());

                }
                break;
        }

    }

    @Override
    protected String doInBackground(String... string) {
        String route;
        OkHttpClient client = new OkHttpClient();
        Request request;
        RequestBody requestBody;
        request = new Request.Builder()
                .url(BASE_URL )
                .build();

        Log.d(TAG, "onPreExecute: Action: " + action);
        switch (action) {
            case GET_EVENTS:
                route = "/get-events/";
                request = new Request.Builder()
                        .url(BASE_URL + route)
                        .build();
                break;
            case GET_TICKETS:
                route = "/ticket/";
                request = new Request.Builder()
                        .url(BASE_URL + route)
                        .header("Authorization", "Basic "+ Base64.encodeToString("wordpressuser:3IeOHORJOeCQq^%oUV".getBytes(),Base64.NO_WRAP))
//                        .header("Authorization", "Basic "+ Base64.encodeToString((username + ":" + password).getBytes(),Base64.NO_WRAP))
                        .build();
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
                requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("username", registerUser.getUsername())
                        .addFormDataPart("phone", registerUser.getPassword())
                        .addFormDataPart("password", registerUser.getPassword())
                        .build();

                route = "/register/";
                request = new Request.Builder()
                        .url(BASE_URL + route)
                        .method("POST", RequestBody.create(null, new byte[0]))
                        .post(requestBody)
                        .build();
                break;
            case BOOK:
                route = "/book/";
                String eventID = string[0];
                Log.d(TAG, "doInBackground: eventID: " + eventID);
                requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("event", eventID)
                        .build();
                request = new Request.Builder()
                        .url(BASE_URL + route)
                        .method("POST", RequestBody.create(null, new byte[0]))
                        .header("Authorization", "Basic "+ Base64.encodeToString("wordpressuser:3IeOHORJOeCQq^%oUV".getBytes(),Base64.NO_WRAP))
//                        .header("Authorization", "Basic "+ Base64.encodeToString((username + ":" + password).getBytes(),Base64.NO_WRAP))
                        .post(requestBody)
                        .build();

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
        Log.d("IEC", "onPostExecute: " + s);
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

                Login loginActivity = (Login) activityWeakReference.get();
                if (response != null){
                    if (0 == response.optInt("status")) {
                        JSONObject userJSON = response.optJSONObject("user");
                        User user = parseUser(userJSON);
                        if (user != null) {
                            if (loginActivity != null) {
                                PrefManager prefManager = new PrefManager(loginActivity);
                                prefManager.setUser(user);
                                prefManager.setIsLoggedIn(true);
                                loginActivity.completeLogin();
                                Toast.makeText(loginActivity, "Logged in successfully.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (!response.optString("error_msg").isEmpty()) {
                        Toast.makeText(loginActivity, "Failed to log in.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(loginActivity, "Unknown error occurred.", Toast.LENGTH_LONG).show();
                    }
                } else {
                        Toast.makeText(loginActivity, "Could not connect to the server.", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();

                break;

            case REGISTER:
                SignUp signUpActivity = (SignUp) activityWeakReference.get();
                if (response != null) {
                    if (0 == response.optInt("status")) {
                        PrefManager prefManager = new PrefManager(signUpActivity);
                        prefManager.setUser(registerUser);
                        prefManager.setIsLoggedIn(true);
                        signUpActivity.completeLogin();
                        Toast.makeText(signUpActivity, "Signed up successfully.", Toast.LENGTH_LONG).show();

                    } else if (!response.optString("error_msg").isEmpty()){
                        Toast.makeText(signUpActivity, response.optString("error_msg"), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(signUpActivity, "Unknown error occurred.", Toast.LENGTH_LONG).show();

                    }
                } else {
                        Toast.makeText(signUpActivity, "Could not connect to the server.", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
                break;
            case GET_TICKETS:
                Log.d(TAG, "onPostExecute: get Tickets" + s);
                MyEvents myEventsFragment = (MyEvents) fragmentWeakReference.get();
                if (myEventsFragment != null) {

                        if (response != null && 0 == response.optInt("status")) {
                            myEventsFragment.clearTickets();
                            JSONArray eventsJSONArray = response.optJSONArray("ticket");
                            ArrayList<EventTicket> eventsTicketsList;

                            try {
                                eventsTicketsList = parseTickets(eventsJSONArray);
                                myEventsFragment.setTickets(eventsTicketsList);
                            } catch (JSONException e) {
                                 e.printStackTrace();
                            }
                        }

                }

                break;
            case BOOK:
                Log.d(TAG, "onPostExecute: book" + s);
                MainActivity mainActivity = (MainActivity) activityWeakReference.get();

                mainActivity.addTicket();
                break;
        }

    }

    private ArrayList<EventTicket> parseTickets(JSONArray ticketsJSONArray) throws JSONException{
        ArrayList<EventTicket> eventTickets = new ArrayList<>();

        for (int index = 0; index < ticketsJSONArray.length(); index++) {
            JSONObject ticket = ticketsJSONArray.getJSONObject(index);
            eventTickets.add(new EventTicket(
                    ticket.optString("event"),
                    ticket.optString("reg_code")));
        }
        return eventTickets;
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
                    event.optString("id"),
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
