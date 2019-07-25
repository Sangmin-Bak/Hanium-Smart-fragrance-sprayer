package com.example.fragrancespray02;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*
 *  분사할 향기 및 분사 시간을 설정하는 액티비티
 *  차후 데이터베이스와 연동하여 설정 값을 데이터베이스에 저장
 */
public class SettingActivity extends AppCompatActivity {

    HttpConnection httpConn = HttpConnection.getInstance();

    String[] set = {"", "", "", "", ""};

    int mHour, mMinute;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        final Calendar cal = Calendar.getInstance();

        Switch outoMode = (Switch) findViewById(R.id.outoMode);
        outoMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    set[0] = "ON";
                } else {
                    set[0] = "OFF";
                }
                sendData(set);
            }
        });

        TextView time = (TextView) findViewById(R.id.time_set);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            }
        });
    }

    // 자동 분사 여부와 선택한 향기를 전송
    private void sendData(final String[] set) {
        new Thread() {
            public void run() {
                httpConn.request_SettingTable(set, callback);
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