package com.e.onlineshoppingapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import API.API;
import model.ImageResponse;
import model.Items;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import reusable.Reusable;

public class AddItemActivity extends AppCompatActivity {
    private EditText etName, etDesc, etPrice;
    private ImageView imgItem;
    private Button btnSave, btnViewData;
    String imagePath;
    String imageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        etName = findViewById(R.id.etName);
        etDesc= findViewById(R.id.etDesc);
        etPrice= findViewById(R.id.etPrice);
        imgItem= findViewById(R.id.imgItem);
        btnSave = findViewById(R.id.btnSave);
        btnViewData = findViewById(R.id.btnViewData);

        imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (data == null){
                Toast.makeText(AddItemActivity.this, "Please Select Image",Toast.LENGTH_LONG).show();
            }
        }
        Uri uri = data.getData();
        imagePath = getRealPathFromUri(uri);
        previewImage(imagePath);
    }

    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }

    private void previewImage(String imagePath) {
        File imgFile = new File(imagePath);
        if (imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgItem.setImageBitmap(myBitmap);
        }
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null,null,null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    private void save() {
        saveImageOnly();
        String name = etName.getText().toString();
        String desc = etDesc.getText().toString();
        double price = Double.parseDouble(etPrice.getText().toString());

        Items heroes = new Items(name, desc, imageName, price);

        API Api = Reusable.getInstance().create(API.class);
        Call<Void> itemsCall = Api.addItem(heroes);

        itemsCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AddItemActivity.this, "Code : " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(AddItemActivity.this, "Successfully Added", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddItemActivity.this, "Code : " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void saveImageOnly(){
        File file = new File(imagePath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestBody);
        API itemsAPI1 = Reusable.getInstance().create(API.class);
        Call<ImageResponse> responseBodyCall = itemsAPI1.uploadImage(body);

        StrictMode();

        try {

            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            Log.d("mero", "saveImageOnly: " + imageResponseResponse.body().toString());
            imageName = imageResponseResponse.body().getFileName();
        }
        catch (IOException e)
        {
            Toast.makeText(AddItemActivity.this, "error : "+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

}
