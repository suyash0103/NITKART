package com.example.android.nitkart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    static GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 1;
    private ProgressDialog mProgress;
    EditText username, password, email_id, phone_number;
    Button login_button, register_button, test;
    String domain = "http://10.50.19.1:8000";
    String loginUrl = domain + "/user/login/";
    String registerUrl = domain +  "/user/register/";
    String testUrl = domain + "/user";
    String google_email = domain + "/user/email_id";
    String myPreferences = "myPreferences";
    String emailId = "email_id";
    String userName = "username";
    String passWord = "password";
    String phoneNumber = "phone_number";
    SharedPreferences sharedPreferences;
    GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        mProgress = new ProgressDialog(LoginActivity.this);
        mProgress.setTitle("Logging In...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email_id = (EditText) findViewById(R.id.email_id);
        phone_number = (EditText) findViewById(R.id.phone_number);
        login_button = (Button) findViewById(R.id.login_button);
        register_button = (Button) findViewById(R.id.register_button);
        test = (Button) findViewById(R.id.test);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mProgress.show();
                if (username.getText().toString().matches("")) {
                    Toast.makeText(LoginActivity.this, "Fill Username", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().matches("")) {
                    Toast.makeText(LoginActivity.this, "Fill Password", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                                    Log.d("Response", response);
                                    if(response.charAt(2) == 'S')
                                    {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(userName, username.getText().toString());
                                        editor.putString(passWord, password.getText().toString());
                                        editor.commit();
                                        Intent intent = new Intent(LoginActivity.this, AdsActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("username", username.getText().toString());
                            params.put("password", password.getText().toString());
                            return params;
                        }
                    };
                    SingletonRequestQueue.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
                }
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mProgress.show();
                if (username.getText().toString().matches("")) {
                    Toast.makeText(LoginActivity.this, "Fill Username", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().matches("")) {
                    Toast.makeText(LoginActivity.this, "Fill Password", Toast.LENGTH_SHORT).show();
                } else if (email_id.getText().toString().matches("")) {
                    Toast.makeText(LoginActivity.this, "Fill Email Id", Toast.LENGTH_SHORT).show();
                } else if (phone_number.getText().toString().matches("")) {
                    Toast.makeText(LoginActivity.this, "Fill Phone Number", Toast.LENGTH_SHORT).show();
                } else if (!phone_number.getText().toString().matches("") && phone_number.getText().toString().length() != 10) {
                    Toast.makeText(LoginActivity.this, "Fill Valid Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, registerUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                                    Log.d("Response", response);
                                    if(response.charAt(2) == 'S')
                                    {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(userName, username.getText().toString());
                                        editor.putString(passWord, password.getText().toString());
                                        editor.putString(emailId, email_id.getText().toString());
                                        editor.putString(phoneNumber, phone_number.getText().toString());
                                        editor.commit();
                                        Intent intent = new Intent(LoginActivity.this, AdsActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                    ) {
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
                    SingletonRequestQueue.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
                }
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, testUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                                Log.d("Response", response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("Error.Response", error.toString());
                            }
                        }
                );
                SingletonRequestQueue.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
            }
        });

        findViewById(R.id.sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.show();
                signIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                            Log.d("Response", response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email_id", gso);
                    return params;
                }
            };
            SingletonRequestQueue.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
            updateUI(account);
        } catch (ApiException e) {
            Log.w("Coudldn't sign in", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(LoginActivity.this, "Error Signing In", Toast.LENGTH_SHORT).show();
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            Intent intent = new Intent(LoginActivity.this, AdsActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
