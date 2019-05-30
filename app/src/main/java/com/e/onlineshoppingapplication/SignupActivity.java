package com.e.onlineshoppingapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import API.API;
import model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import reusable.Reusable;

public class SignupActivity extends AppCompatActivity {
    private EditText etFname, etUsername, etLname, etPassword, etConPassword;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etFname = findViewById(R.id.etFname);
        etLname = findViewById(R.id.etLname);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConPassword = findViewById(R.id.etConPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()){
                    signUp();
                }
            }
        });
    }

    private void signUp(){
        String password = etPassword.getText().toString();
        String conPassword = etConPassword.getText().toString();
        String username = etUsername.getText().toString();
        String fname = etFname.getText().toString();
        String lname = etLname.getText().toString();

        if (password.equals(conPassword)) {

            Users user = new Users(fname, lname, username, password);

            API api = Reusable.getInstance().create(API.class);
            Call<Void> itemsCall = api.insertUser(user);

            itemsCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (!response.isSuccessful()){
                        Toast.makeText(SignupActivity.this, "Code : "+response.code(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(SignupActivity.this, "Successfully Added", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(SignupActivity.this, "Code : "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            etConPassword.setError("Please enter Confirm password");
            etConPassword.requestFocus();
        }

    }

    public boolean validation(){
        boolean flag = true;
        if (TextUtils.isEmpty(etFname.getText().toString())){
            etFname.setError("Please enter Name");
            etFname.requestFocus();
            flag = false;
        }
        else if (TextUtils.isEmpty(etUsername.getText().toString())){
            etUsername.setError("Please enter Username");
            etUsername.requestFocus();
            flag = false;
        }
        else if (TextUtils.isEmpty(etPassword.getText().toString())){
            etPassword.setError("Please enter Password");
            etPassword.requestFocus();
            flag = false;
        }
        else if (TextUtils.isEmpty(etConPassword.getText().toString())){
            etConPassword.setError("Please enter Confirm Password");
            etConPassword.requestFocus();
            flag = false;
        }
        else if (TextUtils.isEmpty(etLname.getText().toString())){
            etLname.setError("Please enter Email");
            etLname.requestFocus();
            flag = false;
        }
        return flag;
    }
}
