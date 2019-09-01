package com.example.sandbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapters.MainViewAdapter;
import models.TodoList;
import network.API;
import network.GenericStatusResponse;
import network.ListTodoListResponse;
import network.LoginResponse;
import network.RetroFitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HeaderMap;
import utils.AccessToken;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button createTodoListButton;
    EditText createTodoListEditText;
    AccessToken accessToken;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TextView View;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createTodoListButton = findViewById(R.id.todoListCreateButton);
        createTodoListButton.setOnClickListener(this);

        createTodoListEditText = findViewById(R.id.todoListCreate);

        accessToken = new AccessToken(this);
        api = RetroFitClientInstance.getRetrofitInstance().create(API.class);

        generateList();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == createTodoListButton.getId()) {
            createTodoList();
        }
    }

    private void generateList() {
        Call GetTodoList = api.getTodoList(accessToken.getHeaders());
        GetTodoList.enqueue(new Callback<ListTodoListResponse>() {
            @Override
            public void onResponse(Call<ListTodoListResponse> call, Response<ListTodoListResponse> response) {
                Integer Status = response.body().getStatus();
                if (Status == 0) {
                    List<TodoList> todolists = response.body().getTodos();
                    RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
                    MainViewAdapter adapter = new MainViewAdapter(MainActivity.this, todolists, MainActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(adapter);

                } else {
                    String error = response.body().getError();
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListTodoListResponse> call, Throwable t) {
                Log.e("LoginScreen", t.toString());
                Log.e("LoginScreen", "Error: cant call API");
            }
        });
    }

    private void createTodoList() {
        String name = createTodoListEditText.getText().toString();
        Call createTodoListCall = api.createTodoList(name, accessToken.getHeaders());
        createTodoListCall.enqueue(new Callback<GenericStatusResponse>() {
            @Override
            public void onResponse(Call<GenericStatusResponse> call, Response<GenericStatusResponse> response) {
                Integer Status = response.body().getStatus();
                if (Status == 0) {
                    generateList();
                } else {
                    String error = response.body().getError();
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
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
