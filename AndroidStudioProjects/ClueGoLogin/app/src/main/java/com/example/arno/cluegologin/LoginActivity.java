package com.example.arno.cluegologin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //progressBar.setVisibility(View.VISIBLE);

        //===============Facebook Login====================================
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
        //====================================================================
        // Set up the login form.
        mEmailView = findViewById(R.id.email);

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               attemptLogin();
                final ProgressBar progressBar = findViewById(R.id.progress_bar);
                //progressBar.setVisibility(View.VISIBLE);
            }
        });
        
        Button devToMain = findViewById(R.id.btn_go_to_main);
        devToMain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(LoginActivity.this, GuessActivity.class);
                startActivity(mainActivity);
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

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
            }
        });
        mRequestQueue.add(stringRequest);
        hideKeyBoard();
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

