package com.example.islam.iec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    private PrefManager prefManager;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        prefManager = new PrefManager(this);

        if (prefManager.isLoggedIn()) {
            user = prefManager.getUser();
            TextView username = (TextView) findViewById(R.id.profile_username);
            TextView name = (TextView) findViewById(R.id.profile_name);
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
}
