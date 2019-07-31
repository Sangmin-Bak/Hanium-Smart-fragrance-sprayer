package com.example.fragrancespray02;

import android.support.v7.app.AppCompatActivity;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*
 *  날씨 정보를 보여주는 화면
 */

public class WeatherActivity extends AppCompatActivity {
    TextView textView;
    Document doc = null;
    LinearLayout layout = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Toolbar toolbar = (Toolbar) findViewById(R.id.weather_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);    // 타이틀 없음
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   // 뒤로가기 버튼

        onWeatheredWeb();
    }

    private void onWeatheredWeb() {
        WebView weatherWeb = (WebView) findViewById(R.id.weather_web);
        WebSettings webSettings = weatherWeb.getSettings();    // mobile web setting
        webSettings.setJavaScriptEnabled(true);     // 자바스크립트 허용
        webSettings.setLoadWithOverviewMode(true);      // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정

        weatherWeb.loadUrl("http://192.168.55.243/weather.php");      // DB에 저장된 분사기 상태를 보여줌
    }
}
