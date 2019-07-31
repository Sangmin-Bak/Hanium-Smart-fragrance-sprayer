package com.example.fragrancespray02;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*
 *  분사할 향기 및 분사 시간을 설정하는 액티비티
 *  차후 데이터베이스와 연동하여 설정 값을 데이터베이스에 저장
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    HttpConnection httpConn = HttpConnection.getInstance();

    String set;         // 자동 분사 여부 데이터 저장
    String firstFrag, secondFrag, thirdFrag; // 세 가지의 향기 데이터를 저장
    String area;
    String weather;

    final Calendar cal = Calendar.getInstance();

    int mHour, mMinute;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

     //   final Calendar cal = Calendar.getInstance();

        Switch outoMode = (Switch) findViewById(R.id.outoMode);
        outoMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    set = "ON";
                } else {
                    set = "OFF";
                }
                sendData(set);
            }
        });

        TextView fragrance = (TextView) findViewById(R.id.fragrance_set);
        TextView time = (TextView) findViewById(R.id.time_set);

        fragrance.setOnClickListener((SettingActivity) this);
        time.setOnClickListener((SettingActivity) this);
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fragrance_set:
                View fragranceView = View.inflate(SettingActivity.this, R.layout.dialog_fragrance_set, null);
                final EditText firstEditText = (EditText) fragranceView.findViewById(R.id.firstEt);
                final EditText secondEditText = (EditText) fragranceView.findViewById(R.id.secondEt);
                final EditText thirdEditText = (EditText) fragranceView.findViewById(R.id.thirdEt);

                AlertDialog.Builder fragranceBuilder = new AlertDialog.Builder(SettingActivity.this);
                fragranceBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firstFrag = firstEditText.getText().toString();
                        secondFrag = secondEditText.getText().toString();
                        thirdFrag = thirdEditText.getText().toString();
                        sendFrag(firstFrag, secondFrag, thirdFrag);
                        Toast.makeText(SettingActivity.this, firstFrag + ", " + secondFrag + ", " + thirdFrag, Toast.LENGTH_SHORT).show();
                    }
                });
                fragranceBuilder.setTitle("원하는 향기를 입력하세요.");
                fragranceBuilder.setView(fragranceView);
                fragranceBuilder.show();
                break;

            case R.id.time_set:
                TimePickerDialog dialog = new TimePickerDialog(SettingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {

                        String msg = String.format("%d 시 %d 분", hour, min);
                        Toast.makeText(SettingActivity.this, msg, Toast.LENGTH_SHORT).show();
                        mHour = hour;
                        mMinute = min;

                        sendTime(hour, min);
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
                dialog.show();
                break;

            case R.id.area_set:
                View locationView = View.inflate(SettingActivity.this, R.layout.dialog_location_set, null);
                final EditText areaText = (EditText) locationView.findViewById(R.id.locationEt);

                AlertDialog.Builder areaBuilder = new AlertDialog.Builder(SettingActivity.this);
                areaBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        area = areaText.getText().toString();
                        sendArea(area);
                        Toast.makeText(SettingActivity.this, "현재 사는 곳 : " + area, Toast.LENGTH_SHORT).show();
                    }
                });
                areaBuilder.setTitle("현재 위치");
                areaBuilder.setView(locationView);
                areaBuilder.show();
                break;

            case R.id.weather_set:
                View weatherView = View.inflate(SettingActivity.this, R.layout.dialog_weather, null);
                final EditText weatherText = (EditText) weatherView.findViewById(R.id.weatherEt);

                AlertDialog.Builder weatherBuilder = new AlertDialog.Builder(SettingActivity.this);
                weatherBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        weather = weatherText.getText().toString();
                        sendWeather(weather);
                        Toast.makeText(SettingActivity.this, "날씨 : " + weather, Toast.LENGTH_SHORT).show();
                    }
                });
                weatherBuilder.setTitle("날씨 설정");
                weatherBuilder.setView(weatherView);
                weatherBuilder.show();
                break;

            default:
                break;
        }
    }

    // 자동 분사 여부와 선택한 향기를 전송
    private void sendData(final String set) {
        new Thread() {
            public void run() {
                httpConn.request_SettingTable(set, callback);
            }
        }.start();
    }
    // 입력한 향기 종류를 전송
    private void sendFrag(final String firstFrag, final String secondFrag, final String thirdFrag) {
        new Thread() {
            public void run() {
                httpConn.fragrance_SettingTable(firstFrag, secondFrag, thirdFrag, callback);
            }
        }.start();
    }
    // 자동 분사 시간을 전송
    private void sendTime(final int hour, final int min) {
        new Thread() {
            public void run() {
                httpConn.time_SettingTable(hour, min, callback);
            }
        }.start();
    }
    //현재 자택이 위치한 지역을 전송
    private void sendArea(final String  area) {
        new Thread() {
            public void run() {
                httpConn.area_SettingTable(area, callback);
            }
        }.start();
    }
    // 날씨 전송, 이건 나중에 없앨 거임, 테스트 용도
    private void sendWeather(final String weather) {
        new Thread() {
            public void run() {
                httpConn.weather_SettingTable(weather, callback);
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}