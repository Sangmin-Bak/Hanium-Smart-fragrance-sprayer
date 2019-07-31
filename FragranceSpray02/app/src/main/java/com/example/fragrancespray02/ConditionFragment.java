package com.example.fragrancespray02;

import android.Manifest;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;

public class ConditionFragment extends Fragment {

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

    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.condition_layout, container, false);

        onConditionWeb();
        onControlWeb();

        return v;
    }

    private void onConditionWeb() {
        WebView conditionWeb = (WebView) v.findViewById(R.id.condition_web);
        WebSettings webSettings = conditionWeb.getSettings();    // mobile web setting
        webSettings.setJavaScriptEnabled(true);     // 자바스크립트 허용
        webSettings.setLoadWithOverviewMode(true);      // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정

        conditionWeb.loadUrl("http://192.168.55.243/read_set.php");      // DB에 저장된 분사기 상태를 보여줌
    }

    private void onControlWeb() {
        WebView controlWeb = (WebView) v.findViewById(R.id.control_web);
        WebSettings webSettings2 = controlWeb.getSettings();    // mobile web setting
        webSettings2.setJavaScriptEnabled(true);     // 자바스크립트 허용
        webSettings2.setLoadWithOverviewMode(true);      // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정

        controlWeb.loadUrl("http://192.168.55.243:8000/serial");      // DB에 저장된 분사기 상태를 보여줌
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
}
