package com.example.arno.cluego;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arno.cluego.Helpers.RequestHelper;
import com.example.arno.cluego.Objects.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class LoginActivity extends AppCompatActivity implements Serializable {
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    SharedPreferences sharedpreferences;
    public static final String MY_PREFS_NAME = "UserInfo";
    String baseUrl;

    CallbackManager callbackManager;
    RequestHelper requestHelper = new RequestHelper();

    public User user = new User();

    // UI references.
    Button btnSignIn ;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private TextView tv;

    public ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn = findViewById(R.id.email_sign_in_button);
        spinner = findViewById(R.id.progressBarLogin);
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        tv = findViewById(R.id.logging);

        btnSignIn.setOnClickListener(Login);

        baseUrl = getResources().getString(R.string.baseUrl);

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
        tv.setText("Logging in...");
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString();

        mRequestQueue = Volley.newRequestQueue(this);
        String url= baseUrl + "user/inlog/" + email+"/"+ password;
        Log.d("loginactivity", "attemptLogin: " + url);

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LoginActivity", response);
                tv.setTextColor(Color.GREEN);

                try{
                    JSONObject userObj = new JSONObject(response);

                    user.setUserId(userObj.getInt("userId"));
                    user.setEmail(userObj.getString("email"));
                    user.setUsername(userObj.getString("username"));
                    user.setCluesFound(userObj.getInt("cluesFound"));
                    user.setDistanceWalked(userObj.getInt("distanceWalked"));
                    user.setGamesPlayed(userObj.getInt("gamesPlayed"));

                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("userId", String.valueOf(user.getUserId()));
                    editor.putString("userName", user.getUsername());
                    editor.commit();

                    spinner.setVisibility(View.INVISIBLE);

                    Intent i = new Intent(LoginActivity.this, StartGameActivity.class);
                    i.putExtra("userDataPackage",user);
                    startActivity(i);
                }catch(JSONException e)
                {
                    Log.d("JSonErr", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setTextColor(Color.RED);
                tv.setText(requestHelper.ParseError(error));

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
            Intent mainActivity = new Intent(LoginActivity.this, StartGameActivity.class);
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
        Intent i = new Intent(LoginActivity.this, StartGameActivity.class);
        startActivity(i);
        //login(); //TODO UNCOMMENT IF YOU WANT TO BE AUTO LOGGED IN THROUGH FACEBOOK. (DOES NOT WORK, UNABLE TO GET USERNAME THIS WAY)
    }

    public void openRegisterForm(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


}