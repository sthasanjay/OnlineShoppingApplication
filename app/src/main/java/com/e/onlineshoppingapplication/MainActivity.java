package com.e.onlineshoppingapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import API.API;
import adapter.ItemsAdapter;
import model.Items;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import reusable.Reusable;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvItems;
    private Button bthInsert;
    List<Items> itemList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvItems= findViewById(R.id.rvItems);
        bthInsert = findViewById(R.id.bthInsert);

        bthInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

        API Api = Reusable.getInstance().create(API.class);

        Call<List<Items>> listCall = Api.getAllItems();

        listCall.enqueue(new Callback<List<Items>>() {
            @Override
            public void onResponse(Call<List<Items>> call, Response<List<Items>> response) {

                generateList(response.body());

            }

            @Override
            public void onFailure(Call<List<Items>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error : "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void generateList(List<Items> body) {
        List<Items> itemList = body;
        List<Items> contactList = new ArrayList<>();

        for (Items item: itemList){
            String image = item.getImage();
            contactList.add(new Items(item.getName(), item.getDesc(), item.getImage(), item.getPrice()));
        }
        ItemsAdapter itemAdapter = new ItemsAdapter(this, contactList);
        rvItems.setAdapter(itemAdapter);
        rvItems.setLayoutManager(new GridLayoutManager(this, 1));
    }
}
