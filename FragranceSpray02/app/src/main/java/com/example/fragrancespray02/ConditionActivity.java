package com.example.fragrancespray02;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    String return_msg;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);

        final TextView textview_address = (TextView) findViewById(R.id.textview);

        // gps 활성화로 위치 정보를 받아온다.
//        gpsService = new GPSService(this);
//        String address =gpsService.getCurrentAddress(gpsService.getLatitude(), gpsService.getLongitude()); // 위도와 경도를 받아와 주소로 변환
//        textview_address.setText(address);

        Button btn = (Button) findViewById(R.id.Button01);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_SHORT).show();
                return_msg = "ON";
                sendData(return_msg);
            }
        });
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
}
