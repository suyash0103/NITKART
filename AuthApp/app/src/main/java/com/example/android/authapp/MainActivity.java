package com.example.android.authapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText username, password, email_id, phone_number;
    Button loginButton, registerButton;
    TextView response;
    RequestQueue queue;
    String domain = "http://10.50.16.235";
    String loginUrl = domain + "/user/login";
    String registerUrl = domain + "/user/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email_id = (EditText) findViewById(R.id.email_id);
        phone_number = (EditText) findViewById(R.id.phone_number);
        loginButton = (Button) findViewById(R.id.login);
        registerButton = (Button) findViewById(R.id.register);
        response = (TextView) findViewById(R.id.response);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, loginUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String responseFinal) {
                                response.setText(responseFinal);
                                Log.i("tag2", responseFinal);
                                Toast.makeText(MainActivity.this, "GOT", Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("tag3", error.toString());
                        response.setText(error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", username.getText().toString());
                        params.put("password", password.getText().toString());
                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, registerUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String responseFinal) {
                                response.setText(responseFinal);
                                Log.i("tag2", responseFinal);
                                Toast.makeText(MainActivity.this, "GOT", Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("tag3", error.toString());
                        response.setText(error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", username.getText().toString());
                        params.put("password", password.getText().toString());
                        params.put("email_id", email_id.getText().toString());
                        params.put("phone_number", phone_number.getText().toString());
                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

    }
}
