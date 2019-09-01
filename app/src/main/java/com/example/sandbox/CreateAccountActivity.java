package com.example.sandbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Credentials;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import models.User;
import network.API;
import network.GenericStatusResponse;
import network.LoginResponse;
import network.RetroFitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private Button CreateAccountButton;
    private EditText InputCreateUsername;
    private EditText InputCreatePassword;
    private EditText InputCreateEmail;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        CreateAccountButton = findViewById(R.id.ConfirmCreateAccountButton);
        CreateAccountButton.setOnClickListener(this);

        InputCreateUsername = findViewById(R.id.InputCreateUsername);
        InputCreatePassword = findViewById(R.id.InputCreatePassword);
        InputCreateEmail = findViewById(R.id.InputCreateEmail);

        api = RetroFitClientInstance.getRetrofitInstance().create(API.class);
    }

    @Override
    public void onClick(View view) {
        createAccount();
    }

    private void goToLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void createAccount() {
        String Username = InputCreateUsername.getText().toString();
        String Password = InputCreatePassword.getText().toString();
        String Email    = InputCreateEmail.getText().toString();

        Call createAccountCall = api.createAccount(Username, Password, Email);

        createAccountCall.enqueue(new Callback<GenericStatusResponse>() {
            @Override
            public void onResponse(Call<GenericStatusResponse> call, Response<GenericStatusResponse> response) {
                Integer Status = response.body().getStatus();
                if (Status == 0) {
                    Log.d("Account", "Created");
                    goToLogin();
                } else {
                    String error = response.body().getError();
                    Toast.makeText(CreateAccountActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenericStatusResponse> call, Throwable t) {
                Log.e("LoginScreen", t.toString());
                Log.e("LoginScreen", "Error: cant call API");
            }
        });
    }

}

