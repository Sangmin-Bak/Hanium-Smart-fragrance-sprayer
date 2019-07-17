/**
   BasicHTTPClient.ino

    Created on: 24.05.2015

*/

#include <Arduino.h>

#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>

#include <ESP8266HTTPClient.h>

#include <WiFiClient.h>
#include <ArduinoJson.h>

ESP8266WiFiMulti WiFiMulti;

HTTPClient http;

void setup() {

  Serial.begin(115200);
  // Serial.setDebugOutput(true);

  Serial.println();
  Serial.println();
  Serial.println();

  for (uint8_t t = 4; t > 0; t--) {
    Serial.printf("[SETUP] WAIT %d...\n", t);
    Serial.flush();
    delay(1000);
  }

  WiFi.mode(WIFI_STA);
  WiFiMulti.addAP("sangmin", "samgupalpal");

  pinMode(D1, OUTPUT);
}

// 문제점 
// 웹서버 접속이 느림, 원할 때 마다 제어를 해야하는 공기청정기에는 부적절하다고 생각됨
// 접속 속도를 높이던가 소켓서버를 사용하던가 해야함

void loop() {
  String msg;
  char count = 0;
  // wait for WiFi connection
  if ((WiFiMulti.run() == WL_CONNECTED)) {

    WiFiClient client;

 //   HTTPClient http;

    Serial.print("[HTTP] begin...\n");
    if (http.begin(client, "http://192.168.55.242/read.php")) {  // HTTP

      msg = connect_web();
      if(msg == "ON"){ 
        digitalWrite(D1, HIGH);
      }
      else if(msg == "OFF") {
        digitalWrite(D1, LOW);
      }

    } else {
      Serial.printf("[HTTP} Unable to connect\n");
    }

    delay(5000);

    if (http.begin(client, "http://192.168.55.242/delete.php")) { // 데이터베이스 제어신호 데이터 삭제 페이지 요청

      msg = connect_web();
      
      http.end();
    } else {
      Serial.printf("[HTTP} Unable to connect\n");
    }
  }

  delay(1000);
}

String connect_web() {    // 웹서버 접속 여부에 따라 모터를 제어하는 함수
  Serial.print("[HTTP] GET...\n");
  // start connection and send HTTP header
  int httpCode = http.GET();

  // httpCode will be negative on error
  if (httpCode > 0) {
    // HTTP header has been send and Server response header has been handled
    Serial.printf("[HTTP] GET... code: %d\n", httpCode);

    // file found at server
    if (httpCode == HTTP_CODE_OK || httpCode == HTTP_CODE_MOVED_PERMANENTLY) {
        String payload = http.getString();
        Serial.println(payload);
        return payload;
    }
  } else {
    Serial.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
    return "fail";
  }
}
