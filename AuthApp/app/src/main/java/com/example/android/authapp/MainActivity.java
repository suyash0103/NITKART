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

    EditText username, password;
    Button login;
    TextView response;
    RequestQueue queue;
    String url1 = "http://127.0.0.1:8000/api-token-auth";
    String url2 = "http://192.168.43.2:8000/api-token-auth/";
    String url3 = "http://169.254.171.210:8000/api-token-auth";
    String url4 = "http://192.168.99.1:8000/api-token-auth";
    String url5 = "http://192.168.43.2:8000/admin";
    String url6 = "http://169.254.171.210:8000/admin/";
    String node = "http://192.168.43.2:3000/api/hotels";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        response = (TextView) findViewById(R.id.response);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, node,
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
                });
// {
//                    @Override
//                    protected Map<String, String> getParams() {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("username", username.getText().toString());
//                        params.put("password", password.getText().toString());
//                        return params;
//                    }
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    Map<String,String> params = new HashMap<String, String>();
//                    params.put("Content-Type","application/x-www-form-urlencoded");
//                    return params;
//                }};
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

    }
}
