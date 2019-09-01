package utils;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class AccessToken {

    private AppCompatActivity activity;
    private String access_token;

    public AccessToken(AppCompatActivity activity) {
        this.activity = activity;
        try {
            SharedPreferences pref = activity.getApplicationContext().getSharedPreferences("MyPref", activity.MODE_PRIVATE);
            access_token = pref.getString("access_token", "");
        } catch (Exception e) {
        }
    }

    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-todo-access-token", access_token);
        return headers;
    }
}
