package com.e.onlineshoppingapplication;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import API.API;
import model.Item;
import model.Items;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import reusable.Reusable;

import static reusable.Reusable.BASE_URL;

public class SearchActivity extends AppCompatActivity {
    private EditText etId;
    private Button btnSearch;
    private TextView tvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etId = findViewById(R.id.etItemID);
        btnSearch = findViewById(R.id.btnSearch);
        tvSearch = findViewById(R.id.tvData);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void loadData() {




        API api = Reusable.getInstance().create(API.class);

        Call<Items>listCall = api.searchitem(etId.getText().toString());
        listCall.enqueue(new Callback<Items>() {

            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
//                Toast.makeText(SearchActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();

                if (response.isSuccessful()){
                    Items item = response.body();

                    String content = "";

                    content += "Name:" +item.getName() + "\n";
                    content += "Description:" +item.getDesc() + "\n";
                    content += "Price:" +item.getPrice() +"\n";

                    tvSearch.setText(content);
                }else{
                    Toast.makeText(SearchActivity.this, response.code()+"", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Items> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "error"+ t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
