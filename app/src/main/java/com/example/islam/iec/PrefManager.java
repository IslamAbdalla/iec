package com.example.islam.iec;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by islam on 9/27/16.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "main_pref";

    private static final String IS_FIRST_TIME_LAUNCHED = "IsFirstTimeLaunch";
    private static final String IS_LOGGED_IN = "IsLoggedIn";

    // User data
    private static final String USERNAME = "Username";
    private static final String USER_EMAIL = "UserEmail";
    private static final String USER_PHONE = "UserPhone";
    private static final String USER_ADDRESS = "UserAddress";
    private static final String USER_JOB = "UserJob";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCHED, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCHED, true);
    }

    public void setIsLoggedIn(boolean IsLoggedIn) {
        editor.putBoolean(IS_LOGGED_IN, IsLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    public void setUser(User user){
        editor.putString(USERNAME, user.getUsername());
        editor.putString(USER_EMAIL, user.getEmail());
        editor.putString(USER_PHONE, user.getPhone());
        editor.putString(USER_ADDRESS, user.getAddress());
        editor.putString(USER_JOB, user.getJob());
        editor.apply();
    }

    public User getUser() {
        return new User(pref.getString(USER_PHONE, "No data"),
                        pref.getString(USER_PHONE, "No data"),
                        pref.getString(USER_PHONE, "No data"),
                        pref.getString(USER_PHONE, "No data"),
                        pref.getString(USER_PHONE, "No data")
                        );
    }
}
