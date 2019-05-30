package com.e.onlineshoppingapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import API.API;
import model.LoginSignupResponse;
import model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import reusable.Reusable;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnRegister, btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkUser() {
        API heroesAPI = Reusable.getInstance().create(API.class);

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        Users user = new Users(username, password);

        Call<LoginSignupResponse> userCall = heroesAPI.loginUser(user);

        userCall.enqueue(new Callback<LoginSignupResponse>() {
            @Override
            public void onResponse(Call<LoginSignupResponse> call, Response<LoginSignupResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Username or password incorrect",Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    if (response.body().getSuccess()){
                        Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_LONG).show();

//                        Url.Cookie = response.headers().get("Set-Cookie");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginSignupResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error : "+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }
}
