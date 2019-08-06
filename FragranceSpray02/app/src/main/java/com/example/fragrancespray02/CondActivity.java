package com.example.fragrancespray02;

import android.Manifest;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;

public class CondActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_cond);

        Toolbar toolbar = (Toolbar) findViewById(R.id.condition_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);    // 타이틀 없음
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   // 뒤로가기 버튼

        final TextView textview_address = (TextView) findViewById(R.id.textview);

        onConditionWeb();
        onControlWeb();
    }

    private void onConditionWeb() {
        WebView conditionWeb = (WebView) findViewById(R.id.condition_web);
        WebSettings webSettings = conditionWeb.getSettings();    // mobile web setting
        webSettings.setJavaScriptEnabled(true);     // 자바스크립트 허용
        webSettings.setLoadWithOverviewMode(true);      // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정

        conditionWeb.loadUrl("http://192.168.55.243/read_set.php");      // DB에 저장된 분사기 상태를 보여줌
    }

    private void onControlWeb() {
        WebView controlWeb = (WebView) findViewById(R.id.control_web);
        WebSettings webSettings2 = controlWeb.getSettings();    // mobile web setting
        webSettings2.setJavaScriptEnabled(true);     // 자바스크립트 허용
        webSettings2.setLoadWithOverviewMode(true);      // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정

        controlWeb.loadUrl("http://192.168.55.243:8000/serial");      // DB에 저장된 분사기 상태를 보여줌
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
/*
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
*/
    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


}
