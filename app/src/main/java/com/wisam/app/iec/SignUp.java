package com.wisam.app.iec;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUp extends AppCompatActivity {
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        prefManager = new PrefManager(this);

    }

    /**
     * Called when the register button is clicked
     * @param view the view
     */
    public void register(View view){

        EditText username = (EditText) findViewById(R.id.username_input);
        EditText phone = (EditText) findViewById(R.id.phone_input);
        EditText password = (EditText) findViewById(R.id.password_input);
        EditText confirmPassword = (EditText) findViewById(R.id.confirm_password_input);

        // Check fields

        if (username == null ||
            phone == null ||
            password == null ||
            confirmPassword == null) {
            Log.e("IEC", "register: Views not found." );
        }

        if (phone != null && username != null &&
            (username.getText().toString().length() == 0 ||
            phone.getText().toString().length() == 0)) {
            Toast.makeText(SignUp.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;

        }

        if (password != null &&
            password.getText().toString().length() < 6) {

            Toast.makeText(SignUp.this, "Password should at least be 6 characters.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (confirmPassword != null && password != null &&

            !password.getText().toString().equals(confirmPassword.getText().toString())) {
            Toast.makeText(SignUp.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }
        new WebDownloaderTask(this, WebDownloaderTask.REGISTER).execute("http://www.test.com/index.html");
    }

    public void completeLogin(){
        if (prefManager.isLoggedIn())
            Log.i("IEC", "completeLogin: Signed up and logged in :D");
        else Log.i("IEC", "completeLogin: Nope");
        finish();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
