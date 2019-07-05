package com.example.haniumproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Locale;

/*
 *  앱 실행 시 가장 먼저 보이는 화면으로 서버와의 통신 및 위도, 경도를 주소로 변환하여 보여준다.
 */

public class FirstLayout extends Fragment {
    private String html;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    private String ip;      // 접속할 서버의 ip주소
    private int port;       // 접속할 서버의 포트번호(현재 접속할 서버의 포트는 9999)

    private PrintWriter mOut;

    private Context mContext;

    public GPSService gpsService;

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
    }

    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.first_layout, container, false);
        final TextView textview_address = (TextView) v.findViewById(R.id.textview);

        // gps 활성화로 위치 정보를 받아온다.
        gpsService = new GPSService((MainActivity)mContext);
        String address =gpsService.getCurrentAddress(gpsService.getLatitude(), gpsService.getLongitude()); // 위도와 경도를 받아와 주소로 변환
        textview_address.setText(address);

        Button btn = (Button) v.findViewById(R.id.Button01);

        final EditText IP = (EditText) v.findViewById(R.id.IP);         // 접속할 서버의 ip주소를 입력(차후 삭제할 예정)
        final EditText PORT = (EditText) v.findViewById(R.id.PORT);     // 접속할 서버의 포트번호를 입력(차후 삭제할 예정)
        Button connect = (Button) v.findViewById(R.id.btn_connect);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Socket making thread
                new Thread(new FirstLayout.ConnectThread(IP.getText().toString(), Integer.parseInt(PORT.getText().toString()))).start();
            }
        });

        // "send" button listener
        // 버튼을 누르면 데이터 전송
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        String return_msg = "ON";       // 향기를 분사할 명령
                        try {
                            dos.writeUTF(return_msg);
                        }catch (Exception e){
                            //e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
/*
        fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();
        transaction =fragmentManager.beginTransaction();

        Button weatherBtn = (Button) v.findViewById(R.id.fab);
        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.content_main, new WeatherLayout()).commit();
                transaction.addToBackStack(null); // 백 버튼을 누르면 처음 화면으로 돌아감
            }
        });
*/
        return v;
    }

    // 서버 접속 클래스
    public class ConnectThread implements Runnable {
        String ip;
        int port;

        public ConnectThread(String ip, int port) {
            this.ip = ip;           // 입력한 ip주소 저장
            this.port = port;       // 입력할 포트번호 저장
        }

        @Override
        public void run() {
            try {
                setSocket(ip, port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSocket(String ip, int port) throws IOException {
        try {
            socket = new Socket(ip, port);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

}