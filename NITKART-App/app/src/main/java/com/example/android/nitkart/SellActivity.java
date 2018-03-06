package com.example.android.nitkart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class SellActivity extends AppCompatActivity implements Imageutils.ImageAttachmentListener {
    ImageView iv_attachment;

    //For Image Attachment

    private Bitmap bitmap;
    private String file_name;
    Button submitBtn;

    Imageutils imageutils;

    public EditText sellerName, sellerEmail, sellerPhone, sellerBlock, sellerRoom, timePeriod,productPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        imageutils = new Imageutils(this);


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
        Toast.makeText(this, "posting add", Toast.LENGTH_SHORT)
                .show();
        String getSellerName, getSellerEmailId, getSellerPhone;
        int getTimePeriod, getSellerBlock, getSellerRoom,getProductPrice;


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

}
