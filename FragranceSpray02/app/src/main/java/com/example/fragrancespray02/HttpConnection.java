package com.example.fragrancespray02;

import android.widget.Toast;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpConnection {

    private OkHttpClient client;
    private static HttpConnection instance = new HttpConnection();
    public static HttpConnection getInstance() {
        return instance;
    }

    private HttpConnection() { this.client = new OkHttpClient(); }

    /** 웹 서버로 요청을 한다. */
    public void requestWebServer(String spray_status, Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("spray_status", spray_status)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.55.243/Control.php")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void request_SettingTable(String set, Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("switch", set)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.55.243/android_set.php")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void fragrance_SettingTable(String first, String second, String third, Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("first", first)
                .add("second", second)
                .add("third", third)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.55.243/fragrance_set.php")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void time_SettingTable(int hour, int min, Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("hour", String.valueOf(hour))
                .add("minute", String.valueOf(min))
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.55.243/time_set.php")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void area_SettingTable(String area, Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("city", area)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.55.243/City.php")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void weather_SettingTable(String weather, Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("weather", weather)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.55.243/weather_set.php")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
