package com.example.arno.cluego;

import android.app.Activity;
import android.os.Bundle;
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
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {

    private static final String TAG = RegisterActivity.class.getName();
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
   // private String url= "https://azurewebsites.net/api/user";
private String url ="https://clugo.azurewebsites.net/api/user";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register_form);

        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);

        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                finish();
            }
        });
    }

    public static boolean containsOnlyNumbers(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }
    public String ValidatingUserInput(View view){
        EditText _username = findViewById(R.id.reg_username);
        EditText _password = findViewById(R.id.reg_password);
        EditText _dupPassword = findViewById(R.id.dup_password);
        EditText _email = findViewById(R.id.reg_email);
        String username = _username.getText().toString().trim();
        String password = _password.getText().toString().trim();
        String dupPassword = _dupPassword.getText().toString().trim();
        String email = _email.getText().toString().trim();
        String value;

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email))
            return  "One or more fields are empty";
        else if (containsOnlyNumbers(username)){
            Log.d(TAG, "ValidatingUserInput: " + containsOnlyNumbers(username));
            return "Username must contain letter"; }
        else if (password.equals(dupPassword) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password))
            return "Validate successful";
        else {
            _password.setText(null);
            _dupPassword.setText(null);
            return  "Passwords don't match!";
        }
    }

    public void RegisterNewUser(View view) {
        TextView tv = findViewById(R.id.link_to_login);
        if(ValidatingUserInput(view) == "Validate successful") {
            PostRequest();
            tv.setText("Validate successful, trying to register.");
        }
        else
            tv.setText(ValidatingUserInput(view));
        hideKeyBoard();
    }

    private void PostRequest() {
        EditText _username = findViewById(R.id.reg_username);
        EditText _password = findViewById(R.id.reg_password);
        EditText _dupPassword = findViewById(R.id.dup_password);
        EditText _email = findViewById(R.id.reg_email);
        String username = _username.getText().toString().trim();
        String password = _password.getText().toString().trim();
        String dupPassword = _dupPassword.getText().toString().trim();
        String email = _email.getText().toString().trim();

        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        params.put("email", email);
        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObj,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                TextView tv = findViewById(R.id.link_to_login);
                if (error instanceof TimeoutError) {
                    tv.setText("Request timed out, plz try again.");
                }else {
                    tv.setText("Username and/or Email already in use!");
                }
                tv.setTextColor(getResources().getColor(R.color.warningText));
            }
        });
        Volley.newRequestQueue(this).add(jsonObjReq);
    }

    public void hideKeyBoard(){
        try  {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

}