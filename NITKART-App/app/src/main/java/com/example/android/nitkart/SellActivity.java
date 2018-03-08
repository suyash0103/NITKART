package com.example.android.nitkart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SellActivity extends AppCompatActivity implements Imageutils.ImageAttachmentListener {
    ImageView iv_attachment;

    //For Image Attachment

    private Bitmap bitmap;
    private String file_name;
    Button submitBtn;
    String postAdUrl = MainActivity.domain + "/user/postAd/";
    Imageutils imageutils;
    public ActionBar toolbar;

    public EditText sellerName, sellerEmail, sellerPhone, sellerBlock, sellerRoom, timePeriod, productPrice, productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);



        toolbar = getSupportActionBar();
        toolbar.setTitle("Sell");

        imageutils = new Imageutils(this);

        productName = findViewById(R.id.product_name);
        sellerName = findViewById(R.id.seller_name);
        sellerEmail = findViewById(R.id.seller_email);
        sellerPhone = findViewById(R.id.seller_phone);
        sellerBlock = findViewById(R.id.seller_block);
        sellerRoom = findViewById(R.id.seller_room);
        timePeriod = findViewById(R.id.time_period);
        productPrice = findViewById(R.id.price);


        iv_attachment = findViewById(R.id.imageViewAttach);
        submitBtn = findViewById(R.id.submitBtn);
        iv_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageutils.imagepicker(1);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSubmitButton();
            }
        });

    }

    public void handleSubmitButton() {
        Toast.makeText(this, "posting ad", Toast.LENGTH_SHORT)
                .show();
        final String getSellerName, getSellerEmailId, getSellerPhone, getProductName, getProductPrice, getTimePeriod, getSellerBlock, getSellerRoom;
        getProductName = productName.getText().toString();
        getSellerName = sellerName.getText().toString();
        getSellerPhone = sellerPhone.getText().toString();
        getSellerEmailId = sellerEmail.getText().toString();
        getSellerBlock = (sellerBlock.getText().toString());
        getSellerRoom = (sellerRoom.getText().toString());
        getTimePeriod = (timePeriod.getText().toString());
        getProductPrice = productPrice.getText().toString();

        if(getProductName.equals("") || getSellerName.equals("") || getSellerPhone.equals("") || getSellerEmailId.equals("") || getSellerBlock.equals("") || getSellerRoom.equals("") || getTimePeriod.equals("") || getProductPrice.equals(""))
        {
            Toast.makeText(SellActivity.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
            return;
        }

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, postAdUrl, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                Toast.makeText(SellActivity.this, resultResponse, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                Toast.makeText(SellActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("product_name", getProductName);
                params.put("seller_name", getSellerName);
                params.put("seller_phone", getSellerPhone);
                params.put("seller_email", getSellerEmailId);
                params.put("seller_block", getSellerBlock);
                params.put("seller_room", getSellerRoom);
                params.put("time_period", getTimePeriod);
                params.put("product_price", getProductPrice);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("image", new DataPart(getProductName + ".jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), iv_attachment.getDrawable()), "image/jpeg"));
                return params;
            }
        };
        SingletonRequestQueue.getInstance(SellActivity.this).addToRequestQueue(multipartRequest);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageutils.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        imageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        this.bitmap = file;
        this.file_name = filename;
        iv_attachment.setImageBitmap(file);

        String path = Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        imageutils.createImage(file, filename, path, false);
    }

    public void onBackPressed(){
        Intent intent = new Intent(SellActivity.this, BottomNavigation.class);
        startActivity(intent);
        finish();
        toolbar = getSupportActionBar();
    }
}
