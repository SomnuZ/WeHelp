package com.wmzl.wehelp;

import android.app.Activity;
import android.os.AsyncTask;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import as.leap.LASDataManager;
import as.leap.LASObject;
import as.leap.LASQuery;
import as.leap.LASQueryManager;
import as.leap.LASRole;
import as.leap.LASUser;
import as.leap.LASUserManager;
import as.leap.callback.FindCallback;
import as.leap.callback.GetCallback;
import as.leap.callback.LogInCallback;
import as.leap.callback.SaveCallback;
import as.leap.callback.SignUpCallback;
import as.leap.exception.LASException;


public class ActivityLogin extends Activity {
    private UserRegisterTask mRegTask = null;
    private UserLoginTask mLoginTask = null;
    EditText editTextUsername;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername=(EditText) findViewById(R.id.username);
        editTextPassword=(EditText) findViewById(R.id.password);

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        Button mLoginButton = (Button) findViewById(R.id.sign_in_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }
    public void attemptLogin(){
        if(mLoginTask!=null){return;}
        // Store values at the time of the login attempt.
        String email = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        mLoginTask=new UserLoginTask(email,password);
        mLoginTask.execute((Void) null);
        finish();
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mUsername = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return false;
//            }

            LASUserManager.logInInBackground(mUsername, mPassword, new LogInCallback<LASUser>(){
                public void done(LASUser user, LASException e) {
                    if (user != null) {
                        Toast.makeText(getApplicationContext(),
                                "Logged in Successfully!", Toast.LENGTH_SHORT)
                                .show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Log in failed!", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            });
            return true;
        }

        @Override
        protected void onPreExecute(){
            // Show Alert
            Toast.makeText(getApplicationContext(),
                    "Logging in..", Toast.LENGTH_SHORT)
                    .show();
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            mRegTask = null;
            // Show Alert
            if(success) {
//                Toast.makeText(getApplicationContext(),
//                        "Logged in Successfully!", Toast.LENGTH_SHORT)
//                        .show();
            }else {
//                Toast.makeText(getApplicationContext(),
//                        "Registered fail!", Toast.LENGTH_SHORT)
//                        .show();
            }
        }

        @Override
        protected void onCancelled() {
            mRegTask = null;
        }
    }

    public void attemptRegister(){
        if(mRegTask!=null){return;}
        // Store values at the time of the login attempt.
        String email = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        mRegTask=new UserRegisterTask(email,password);
        mRegTask.execute((Void) null);
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserRegisterTask(String email, String password) {
            mUsername = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return false;
//            }
            LASUser user = new LASUser();
            user.setUserName(mUsername);
            user.setPassword(mPassword);
//            user.setEmail(mEmail);

            LASUserManager.signUpInBackground(user, new SignUpCallback() {
                public void done(LASException e) {
                    if (e == null) {
                        // Hooray! Let them use the app now.
                    } else {
                        // Sign up didn't succeed. Look at the LASException
                        // to figure out what went wrong
                    }
                }
            });
            return true;
        }

        @Override
        protected void onPreExecute(){
            // Show Alert
            Toast.makeText(getApplicationContext(),
                    "Registering..", Toast.LENGTH_LONG)
                    .show();
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            mRegTask = null;
            // Show Alert
            if(success) {
                Toast.makeText(getApplicationContext(),
                        "Registered Successfully!", Toast.LENGTH_SHORT)
                        .show();
            }else {
                Toast.makeText(getApplicationContext(),
                        "Registration failed!", Toast.LENGTH_LONG)
                        .show();
            }
        }

        @Override
        protected void onCancelled() {
            mRegTask = null;
        }
    }

}
