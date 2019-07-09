package com.example.httptest;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;


public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";
    ToggleButton ledButton;
    TextView led_tx;

    String ledStatus;

    JSONObject myJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        led_tx = (TextView) findViewById(R.id.led_tx);
        ledButton = (ToggleButton) findViewById(R.id.toggleButton);
        ledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  String ledStatus;
                if(ledButton.isChecked()) {
                    Toast.makeText(MainActivity.this, "ON", Toast.LENGTH_SHORT).show();
                    ledStatus = "ON";
                } else {
                    Toast.makeText(MainActivity.this, "OFF", Toast.LENGTH_SHORT).show();
                    ledStatus = "OFF";
                }

                String serverAddress = "192.168.55.242:3306/LED_Status.php";
                try {
                    HttpRequestTask requestTask = new HttpRequestTask(serverAddress);
                    led_tx.setText((CharSequence) myJSON);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class HttpRequestTask extends AsyncTask<String, Void, String> {
        private String serverAddress;
        Response response;

        public HttpRequestTask(String serverAddress) throws IOException {
            this.serverAddress = serverAddress;
        }

        @Override
        protected String doInBackground(String... params) {
            String val = params[0];
         //   final String url = "http://" + serverAddress + "/" + val;
            final String url = "http://" + serverAddress;

            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("ledStatus", ledStatus)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            client.newCall(request).enqueue(callback);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

       private Callback callback = new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {

           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
               try {
                   myJSON = new JSONObject(response.body().string());
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       };
    }
}
