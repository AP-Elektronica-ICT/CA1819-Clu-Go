package com.example.arno.cluegologin;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {

    private static final String TAG = RegisterActivity.class.getName();
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private String url= "http://192.168.2.3:45455/api/user";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register_form);

        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);


        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                finish();
            }
        });
    }

    public void ValidatingUserInput(View view){
        EditText _username = (EditText) findViewById(R.id.reg_username);
        EditText _password = (EditText) findViewById(R.id.reg_password);
        EditText _dupPassword = (EditText) findViewById(R.id.dup_password);
        EditText _email = (EditText) findViewById(R.id.reg_email);
        String username = _username.getText().toString().trim();
        String password = _password.getText().toString().trim();
        String dupPassword = _dupPassword.getText().toString().trim();
        String email = _email.getText().toString().trim();
        String value;

        if (password.equals(dupPassword) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password))
            value = "Register successful";
        else if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email))
            value = "One or more fields are empty";

        else {
            value = "Passwords don't match!";
            _password.setText(null);
            _dupPassword.setText(null);
        }

        Toast toast = Toast.makeText(this, value, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void RegisterNewUser(View view) {
        ValidatingUserInput(view);
        PostRequest();
        hideKeyBoard();
    }


    private void PostRequest() {
        EditText _username = (EditText) findViewById(R.id.reg_username);
        EditText _password = (EditText) findViewById(R.id.reg_password);
        EditText _dupPassword = (EditText) findViewById(R.id.dup_password);
        EditText _email = (EditText) findViewById(R.id.reg_email);
        String username = _username.getText().toString().trim();
        String password = _password.getText().toString().trim();
        String dupPassword = _dupPassword.getText().toString().trim();
        String email = _email.getText().toString().trim();

        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        params.put("email", email);
        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                TextView tv = (TextView)findViewById(R.id.link_to_login);
                Toast.makeText(getApplicationContext(), "Register successful!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonObjReq);
    }

    public void getUserList(){
        mRequestQueue = Volley.newRequestQueue(this);

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG,"Response: " + response.toString());
                TextView tv = (TextView)findViewById(R.id.link_to_login);
                tv.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error: " + error.toString());
            }
        });

        mRequestQueue.add(stringRequest);
    }

    public void hideKeyBoard(){
        try  {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

}