package network;

import java.util.ArrayList;

import models.Task;
import models.Task_Collection;

public class TodoListDetailResponse {
    private int status;
    private String error;
    private ArrayList<Task> tasks;

    public TodoListDetailResponse(int status, String error, ArrayList<Task> tasks) {
        this.status = status;
        this.error = error;
        this.tasks = tasks;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
