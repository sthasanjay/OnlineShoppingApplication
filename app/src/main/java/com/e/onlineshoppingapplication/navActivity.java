package com.e.onlineshoppingapplication;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class navActivity extends AppCompatActivity {
    private ImageView imgSamsung, imgIphone, imgColor, imgCarbon, imgHawei, imgMarco;
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        imgSamsung = findViewById(R.id.imgSamsung);
        imgCarbon = findViewById(R.id.imgCarbon);
        imgColor = findViewById(R.id.imgColor);
        imgIphone = findViewById(R.id.imgIphone);
        imgHawei = findViewById(R.id.imgHawaei);
        imgMarco = findViewById(R.id.imgMacro);


        imgSamsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(navActivity.this, MobileDetailActivity.class);
                intent.putExtra("selectedName", "samsung");
                startActivity(intent);

            }
        });

        imgCarbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(navActivity.this, MobileDetailActivity.class);
                intent.putExtra("selectedName", "carbon");
                startActivity(intent);


            }
        });

        imgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(navActivity.this, MobileDetailActivity.class);
                intent.putExtra("selectedName", "color");
                startActivity(intent);

            }
        });

        imgIphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(navActivity.this, MobileDetailActivity.class);
                intent.putExtra("selectedName", "iphone");
                startActivity(intent);

            }
        });

        imgMarco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(navActivity.this, MobileDetailActivity.class);
                intent.putExtra("selectedName", "micromax");
                startActivity(intent);

            }
        });
        imgHawei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(navActivity.this, MobileDetailActivity.class);
                intent.putExtra("selectedName", "huawei");
                startActivity(intent);

            }
        });


       mDrawerlayout = (DrawerLayout)findViewById(R.id.activity_nav);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    }

