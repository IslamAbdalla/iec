package com.example.islam.iec;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class Login extends AppCompatActivity {
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prefManager = new PrefManager(this);

    }

    public void signIn(View view){
        new WebDownloaderTask(this, WebDownloaderTask.LOG_IN).execute("http://www.test.com/index.html");
    }

    public void completeLogin(){
        if (prefManager.isLoggedIn())
            Log.i("IEC", "completeLogin: Logged in :D");
        else Log.i("IEC", "completeLogin: Nope");
        finish();
    }
}
