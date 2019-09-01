package network;

import java.util.ArrayList;

import models.TodoList;

public class ListTodoListResponse {
    private int status;
    private String error;
    private ArrayList<TodoList> todolists;

    public ListTodoListResponse(int status, String error, ArrayList<TodoList> todolists) {
        this.status = status;
        this.error = error;
        this.todolists = todolists;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public ArrayList<TodoList> getTodos() {
        return todolists;
    }
}
