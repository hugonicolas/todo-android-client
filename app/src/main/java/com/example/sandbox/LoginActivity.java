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
import network.LoginResponse;
import network.RetroFitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button LoginButton;
    private Button CreateAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        LoginButton = findViewById(R.id.LoginButton);
        CreateAccountButton = findViewById(R.id.CreateAccountButton);

       LoginButton.setOnClickListener(this);
       CreateAccountButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == LoginButton.getId()) {
            login();
        } else if (view.getId() == CreateAccountButton.getId()) {
            goToCreate();
        }
    }

    private void goToCreate() {
        Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
        startActivity(intent);
    }

    private void login() {
        Log.d("LoginScreen", "Trying to connect...");

        EditText UsernameInput = findViewById(R.id.InputLoginUsername);
        EditText PasswordInput = findViewById(R.id.InputLoginPassword);

        String Username = UsernameInput.getText().toString();
        String Password = PasswordInput.getText().toString();

        API api = RetroFitClientInstance.getRetrofitInstance().create(API.class);

        Call LoginCall = api.login(Username, Password);

        LoginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Integer Status = response.body().getStatus();
                if (Status == 0) {
                    String access_token = response.body().getUser().getAccessToken();
                    String refresh_token = response.body().getUser().getRefreshToken();

                    saveUserCrendentials(access_token, refresh_token);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                } else {
                    String error = response.body().getError();
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("LoginScreen", t.toString());
                Log.e("LoginScreen", "Error: cant call API");
            }
        });
    }

    private void saveUserCrendentials(String access_token, String refresh_token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(
                "access_token", access_token
        );
        editor.putString(
                "refresh_token", refresh_token
        );
        editor.commit();
    }
}

