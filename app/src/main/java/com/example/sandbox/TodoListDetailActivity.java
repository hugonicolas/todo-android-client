package com.example.sandbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import adapters.TodoListDetailAdapter;
import models.Task;
import network.API;
import network.RetroFitClientInstance;
import network.GenericStatusResponse;
import network.GenericStatusResponse;
import network.TodoListDetailResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.AccessToken;

public class TodoListDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private int todolistid;
    Button addTaskButton;
    CheckBox showDone;
    API api;
    AccessToken accessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist_details);

        //Getting todoid from previous activity
        final Intent intent = getIntent();
        int todolistid = intent.getIntExtra("todolistid", 0);
        this.todolistid = todolistid;

        this.api = RetroFitClientInstance.getRetrofitInstance().create(API.class);
        this.accessToken = new AccessToken(this);
        this.showDone = findViewById(R.id.checkboxFilterDone);
        this.addTaskButton = findViewById(R.id.addTaskButton);

        addTaskButton.setOnClickListener(this);
        showDone.setOnClickListener(this);

        String todolistname = intent.getStringExtra("todolistname");
        TextView todolistname_view = findViewById(R.id.todoListDetailsTitle);
        todolistname_view.setText(todolistname);
        reloadTaskList(todolistid);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == addTaskButton.getId()) {
            createTask();
        } else if (view.getId() == showDone.getId()) {
            reloadTaskList(this.todolistid);
        }

    }

    public void reloadTaskList(int todolistid) {
        Call GetTodoListDetails = api.getTodoListDetails(todolistid, accessToken.getHeaders());

        GetTodoListDetails.enqueue(new Callback<TodoListDetailResponse>() {
            @Override
            public void onResponse(Call<TodoListDetailResponse> call, Response<TodoListDetailResponse> response) {
                Integer Status = response.body().getStatus();
                if (Status == 0) {
                    List<Task> tasks = response.body().getTasks();
                    List<Task> filteredTask = filterByDone(tasks);
                    TodoListDetailAdapter adapter = new TodoListDetailAdapter(filteredTask, TodoListDetailActivity.this, TodoListDetailActivity.this);
                    RecyclerView recyclerView = findViewById(R.id.taskRecyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(TodoListDetailActivity.this));
                    recyclerView.setAdapter(adapter);
                } else {
                    String error = response.body().getError();
                    Toast.makeText(TodoListDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TodoListDetailResponse> call, Throwable t) {
                Log.e("TodoListDetailActivity", t.getMessage());
            }
        });
    }

    public void deleteTask(int id) {
        Call taskDelete = api.deleteTask(id, accessToken.getHeaders());

        taskDelete.enqueue(new Callback<GenericStatusResponse>() {
            @Override
            public void onResponse(Call<GenericStatusResponse> call, Response<GenericStatusResponse> response) {
                Integer Status = response.body().getStatus();
                if (Status == 0) {
                    reloadTaskList(todolistid);
                } else {
                    String error = response.body().getError();
                    Toast.makeText(TodoListDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenericStatusResponse> call, Throwable t) {
                Log.e("TodoListDetailActivity", t.getMessage());
            }
        });
    }

    public void toggleTask(int id) {
        Call taskToggle = api.toggleTask(id, accessToken.getHeaders());

        taskToggle.enqueue(new Callback<GenericStatusResponse>() {
            @Override
            public void onResponse(Call<GenericStatusResponse> call, Response<GenericStatusResponse> response) {
                Integer Status = response.body().getStatus();
                if (Status == 0) {
                    reloadTaskList(todolistid);
                } else {
                    String error = response.body().getError();
                    Toast.makeText(TodoListDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenericStatusResponse> call, Throwable t) {
                Log.e("TodoListDetailActivity", t.getMessage());
            }
        });


    }

    public void createTask() {
        EditText taskTextInput = findViewById(R.id.addTaskText);

        AccessToken accessToken = new AccessToken(this);

        API api = RetroFitClientInstance.getRetrofitInstance().create(API.class);
        Call CreateTask = api.createTask(todolistid,taskTextInput.getText().toString(), accessToken.getHeaders());

        CreateTask.enqueue(new Callback<GenericStatusResponse>() {
            @Override
            public void onResponse(Call<GenericStatusResponse> call, Response<GenericStatusResponse> response) {
                Integer Status = response.body().getStatus();
                if (Status == 0) {
                    reloadTaskList(todolistid);
                } else {
                    String error = response.body().getError();
                    Toast.makeText(TodoListDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenericStatusResponse> call, Throwable t) {
                Log.e("TodoListDetailActivity", t.getMessage());
            }
        });
    }

    public List<Task> filterByDone(List<Task> tasks) {
        if (this.showDone.isChecked()) {
            List<Task> filteredTask = new ArrayList<>();
            for (Task task: tasks) {
                if (task.getStatus() == 0) {
                    filteredTask.add(task);
                }
            }
            return filteredTask;
        }
        return tasks;
    }
}

