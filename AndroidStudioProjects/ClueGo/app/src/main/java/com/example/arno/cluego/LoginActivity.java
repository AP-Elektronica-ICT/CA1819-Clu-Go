package com.example.arno.cluego;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;


public class LoginActivity extends AppCompatActivity{
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private String url= "https://clugo.azurewebsites.net/api/user";
    CallbackManager callbackManager;

    // UI references.
    private Button btnSignIn, btnDev;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    public ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn = findViewById(R.id.email_sign_in_button);
        btnDev = findViewById(R.id.btn_go_to_main);
        spinner = findViewById(R.id.progressBarLogin);
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);

        btnSignIn.setOnClickListener(Login);
        btnDev.setOnClickListener(DevMove);
    }
    
    private View.OnClickListener Login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            attemptLogin();
            spinner.setVisibility(View.VISIBLE);
            hideKeyBoard();
        }
    };

    private View.OnClickListener DevMove = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mainActivity = new Intent(LoginActivity.this, GuessActivity.class);
            startActivity(mainActivity);
        }
    };

    private void attemptLogin() {
        TextView tv = findViewById(R.id.logging);
        tv.setText("Logging in...");
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString();

        mRequestQueue = Volley.newRequestQueue(this);
        String url= "https://clugo.azurewebsites.net/api/user/inlog/"+email+"/"+password;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LoginActivity", response);

                TextView tv = findViewById(R.id.logging);
                tv.setText(response);
                String value = response;

                editor.putString("UID", value);
                editor.apply();

                spinner.setVisibility(View.INVISIBLE);

                Intent i = new Intent(LoginActivity.this, StartGameFragment.class);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                TextView tv = findViewById(R.id.logging);
                if (error instanceof TimeoutError)
                    tv.setText("Login timed out, please try again.");
                else
                    tv.setText("Username or pasword incorrect!");

                Log.i("Error.response", error.toString());
                spinner.setVisibility(View.INVISIBLE);
            }
        });
        mRequestQueue.add(stringRequest);
    }

    public void facebookLogic(){
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Intent mapsActivity = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mapsActivity);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn){
            Intent mainActivity = new Intent(LoginActivity.this, StartGameFragment.class);
            startActivity(mainActivity);
        }
    }

    public void hideKeyBoard(){
        try  {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    public void signInFacebook(View view) {
        Intent i = new Intent(LoginActivity.this, StartGameFragment.class);
        startActivity(i);
        //login();
    }

    public void openRegisterForm(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}

