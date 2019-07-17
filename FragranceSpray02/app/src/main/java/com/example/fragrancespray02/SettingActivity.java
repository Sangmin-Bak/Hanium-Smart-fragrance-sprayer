package com.example.fragrancespray02;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;

/*
 *  분사할 향기 및 분사 시간을 설정하는 액티비티
 *  차후 데이터베이스와 연동하여 설정 값을 데이터베이스에 저장
 */
public class SettingActivity extends PreferenceActivity {

    SwitchPreference switchPreference;
    EditTextPreference scent1, scent2, scent3;
    EditTextPreference time;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.activity_setting);
        addPreferencesFromResource(R.xml.setting_preference);

        switchPreference = (SwitchPreference) findPreference("switch");
        scent1 = (EditTextPreference) findPreference("first");
        scent2 = (EditTextPreference) findPreference("second");
        scent3 = (EditTextPreference) findPreference("third");
        time = (EditTextPreference) findPreference("time_setting");

    }

}