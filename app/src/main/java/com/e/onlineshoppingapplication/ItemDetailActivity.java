package com.e.onlineshoppingapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemDetailActivity extends AppCompatActivity {
    CircleImageView circleImg;
    TextView tvName, tvPrice, tvDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        circleImg = findViewById(R.id.imgProfile);
        tvName= findViewById(R.id.tvName);
        tvPrice= findViewById(R.id.tvPrice);
        tvDescription= findViewById(R.id.tvDescription);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){

            try {

                URL url = new URL(bundle.getString("image"));
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                circleImg.setImageBitmap(bmp);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            tvName.setText("Name: "+bundle.getString("name"));
            tvPrice.setText("Price: "+bundle.getString("price"));
            tvDescription.setText(bundle.getString("description"));
        }
    }
}
