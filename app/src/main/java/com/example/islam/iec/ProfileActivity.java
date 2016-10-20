package com.example.islam.iec;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    private PrefManager prefManager;
    private User user;
    private Boolean updated;

    private TextView username ;
    private TextView name  ;
    private TextView password ;
    private TextView phone ;
    private TextView email ;
    private TextView address  ;
    private TextView job  ;

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
        updated = false;

        if (prefManager.isLoggedIn()) {
            user = prefManager.getUser();
             username = (TextView) findViewById(R.id.profile_username);
             name = (TextView) findViewById(R.id.profile_name);
             password = (TextView) findViewById(R.id.profile_password);
             phone = (TextView) findViewById(R.id.profile_phone);
             email = (TextView) findViewById(R.id.profile_email);
             address = (TextView) findViewById(R.id.profile_address);
             job = (TextView) findViewById(R.id.profile_job);

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

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final EditText input = new EditText(ProfileActivity.this);
        LinearLayout.LayoutParams lp;
        LayoutInflater inflater = getLayoutInflater();
        switch (view.getId()) {


            case R.id.profile_password_layout:
                alertDialogBuilder.setMessage("Update your password");
                final View dialogView = inflater.inflate(R.layout.password_dialog, null);
                alertDialogBuilder.setView(dialogView)

                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (!((EditText) dialogView.findViewById(R.id.dialog_old_password)).getText().toString().equals(user.getPassword())) {
                            Toast.makeText(ProfileActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        } else if (((EditText) dialogView.findViewById(R.id.dialog_new_password)).getText().toString().length() < 6){
                            Toast.makeText(ProfileActivity.this, "Password should at least be 6 digits", Toast.LENGTH_SHORT).show();
                        } else if (!((EditText) dialogView.findViewById(R.id.dialog_new_password)).getText().toString().equals(((EditText) dialogView.findViewById(R.id.dialog_confirm_password)).getText().toString())){

                            Toast.makeText(ProfileActivity.this, "New passwords do not match", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(ProfileActivity.this, "Password updated", Toast.LENGTH_SHORT).show();
                        password.setText(((EditText) dialogView.findViewById(R.id.dialog_new_password)).getText());
                        user.setPassword(((EditText) dialogView.findViewById(R.id.dialog_new_password)).getText().toString());
                        }
                    }
                })

                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                break;
            case R.id.profile_phone_layout:
                alertDialogBuilder.setMessage("Update your phone");
                lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                alertDialogBuilder.setView(input);
                alertDialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(ProfileActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                        phone.setText(input.getText());
                        user.setPhone(input.getText().toString());
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                break;

            case R.id.profile_name_layout:
                alertDialogBuilder.setMessage("Update your full name");
                lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                alertDialogBuilder.setView(input);
                alertDialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(ProfileActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                        name.setText(input.getText().toString());
                        user.setName(input.getText().toString());
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                break;


            case R.id.profile_email_layout:
                alertDialogBuilder.setMessage("Update you email");
                lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                alertDialogBuilder.setView(input);
                alertDialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(ProfileActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                        email.setText(input.getText().toString());
                        user.setEmail(input.getText().toString());
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


            case R.id.profile_address_layout:
                alertDialogBuilder.setMessage("Update your address");
                 lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialogBuilder.setView(input);
                alertDialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(ProfileActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                        address.setText(input.getText().toString());
                        user.setAddress(input.getText().toString());
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                break;

            case R.id.profile_job_layout:
                alertDialogBuilder.setMessage("Update you job");
                lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialogBuilder.setView(input);
                alertDialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(ProfileActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                        job.setText(input.getText().toString());
                        user.setJob(input.getText().toString());
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        }
        alertDialogBuilder.show();
    }
}
