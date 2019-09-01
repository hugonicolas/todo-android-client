package models;

import java.util.ArrayList;

public class Task_Collection {
    private ArrayList<Task> tasks;

    public Task_Collection(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
