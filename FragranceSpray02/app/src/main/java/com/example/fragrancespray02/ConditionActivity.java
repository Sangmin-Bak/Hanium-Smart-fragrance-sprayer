package com.example.fragrancespray02;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ConditionActivity extends AppCompatActivity {

    private String html;
    private DataInputStream dis;
    private DataOutputStream dos;

    private PrintWriter mOut;
    private Context mContext;

    HttpConnection httpConn = HttpConnection.getInstance();

    String return_msg;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);

        Toolbar toolbar = (Toolbar) findViewById(R.id.condition_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);    // 타이틀 없음
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   // 뒤로가기 버튼

        final TextView textview_address = (TextView) findViewById(R.id.textview);

        Button btn = (Button) findViewById(R.id.Button01);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_SHORT).show();
                return_msg = "1";
                sendData(return_msg);
            }
        });

        WebView webView = (WebView) findViewById(R.id.condition_web);
        WebSettings webSettings = webView.getSettings();    // mobile web setting
        webSettings.setJavaScriptEnabled(true);     // 자바스크립트 허용
        webSettings.setLoadWithOverviewMode(true);      // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정

        webView.loadUrl("http://192.168.55.242/read_set.php");      // DB에 저장된 분사기 상태를 보여줌
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendData(final String return_msg) {
        new Thread() {
            public void run() {
                httpConn.requestWebServer(return_msg, callback);
            }
        }.start();
    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            //Log.d(TAG, "콜백오류:"+e.getMessage());
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            //Log.d(TAG, "서버에서 응답한 Body:"+body);
        }
    };

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


}
