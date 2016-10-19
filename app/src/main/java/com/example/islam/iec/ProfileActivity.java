package com.example.islam.iec;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    private PrefManager prefManager;
    private User user;
    private Boolean updateMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        prefManager = new PrefManager(this);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.profile_fab);
//        if (fab != null) {
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Snackbar.make(view, "We will proceed with updating your info", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
//            });
//        }
        updateMode = false;

        if (prefManager.isLoggedIn()) {
            user = prefManager.getUser();
            TextView username = (TextView) findViewById(R.id.profile_username);
            TextView name = (TextView) findViewById(R.id.profile_name);
            TextView password = (TextView) findViewById(R.id.profile_password);
            TextView phone = (TextView) findViewById(R.id.profile_phone);
            TextView email = (TextView) findViewById(R.id.profile_email);
            TextView address = (TextView) findViewById(R.id.profile_address);
            TextView job = (TextView) findViewById(R.id.profile_job);

            if (username != null) {
                username.setText(user.getUsername());
            }
            if (name != null) {
                name.setText(user.getName());
            }
            if (password != null) {
                password.setText(user.getPassword());
            }
            if (phone != null) {
                phone.setText(user.getPhone());
            }
            if (email != null) {
                email.setText(user.getEmail());
            }
            if (address != null) {
                address.setText(user.getAddress());
            }
            if (job != null) {
                job.setText(user.getJob());
            }

        } else {
            Toast.makeText(this, "Please login", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    public void updateUserData(View view) {
    }
}
