package models;

import androidx.annotation.NonNull;

public class Task {
    private int status;
    private int id;
    private String title;
    private String created;

    public Task(int id, String title, String created, int status) {
        this.id = id;
        this.title = title;
        this.created = created;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCreated() {
        return created;
    }

    public int getStatus() {return status;}

    @Override
    public String toString() {
        return title;
    }
}
