package models;

import com.google.gson.annotations.SerializedName;

public class User {

    private Integer userid;
    private String  username;
    private String  access_token;
    private String  refresh_token;

    public User(Integer userid, String username, String access_token, String refresh_token) {
        this.userid = userid;
        this.username = username;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public void setRefreshToken(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
