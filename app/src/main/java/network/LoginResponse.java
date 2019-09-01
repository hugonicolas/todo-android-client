package network;

import models.User;

public class LoginResponse {
    private int status;
    private String error;
    private User user;

    public LoginResponse(int status, String error, User user) {
        this.status = status;
        this.error = error;
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public User getUser() {
        return user;
    }
}
