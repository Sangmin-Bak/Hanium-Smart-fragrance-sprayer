/*
    This sketch sends a string to a TCP server, and prints a one-line response.
    You must run a TCP server in your local network.
    For example, on Linux you can use this command: nc -v -l 3000
*/

#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>

#ifndef STASSID
#define STASSID "sangmin"
#define STAPSK  "samgupalpal"
#endif

#define LED D1

const char* ssid     = STASSID;
const char* password = STAPSK;

const char* host = "192.168.55.242";
const uint16_t port = 9999;

char* arr = "ON";

// Use WiFiClient class to create TCP connections
WiFiClient client;

ESP8266WiFiMulti WiFiMulti;

void setup() {
  Serial.begin(115200);

  // We start by connecting to a WiFi network
  WiFi.mode(WIFI_STA);
  WiFiMulti.addAP(ssid, password);

  Serial.println();
  Serial.println();
  Serial.print("Wait for WiFi... ");

  while (WiFiMulti.run() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  delay(500);

  Serial.print("connecting to ");
  Serial.print(host);
  Serial.print(':');
  Serial.println(port);


  client.connect(host, port);
  client.println("hello from ESP8266");

  pinMode(D1, OUTPUT);
}

void loop() {
  short count = 0;
  /*
  if (!client.connect(host, port)) {
    Serial.println("connection failed");
    Serial.println("wait 5 sec...");
    delay(5000);
    return;
  }
  */
 
  // This will send the request to the server
  // client.println("hello from ESP8266");  
  
  if(client.connected()) {
    while(client.available()) {
      // 전송된 데이터가 있을 경우 데이터를 읽어들인다.
      
//      char inData = client.read();
      // 전달되는 명령어(문자열)을 배열에 저장
      char inData[2] = {0};
      for(char i = 0; i < 2; i++)
        inData[i] = client.read();
      // 읽어온 데이터를 저장

      digitalWrite(D1, LOW); 

      // 읽어온 문자열과 명령어를 비교
      for(char i = 0; i < 2; i++) {
        if(inData[i] == arr[i])
           count++; // 일치하는 명령어일 때 count증가
      }

      if(count == 2) {     // 설정된 명령어가 ON이므로 count가 2라면 전송이 제대로 이루어진 것
        digitalWrite(D1, HIGH);
       // digitalWrite(D5, HIGH);   //   
       // analogWrite(D5, 250);   // 모터 핀(pwm 최대)
        delay(3000);
        digitalWrite(D1, LOW);    // 3초 후 다시 꺼짐
      }
      /*
      else if(inData == 'N') {     // 수신되는 값 바꿀 것
        digitalWrite(D1, HIGH);
       // digitalWrite(D5, HIGH);   //   
       // analogWrite(D5, 250);   // 모터 핀(pwm 최대)
        delay(3000);
        digitalWrite(D1, LOW);
      }
      else if(inData == 'F') {     // 수신되는 값 바꿀 것
        digitalWrite(D1, LOW);   
       // analogWrite(D5, 0);   // 모터 핀(pwm 최대)
      }*/
    }
  }

  //read back one line from server
//  Serial.println("receiving from remote server");
//  String line = client.readStringUntil('\r');
//  Serial.println(line);
/*
  Serial.println("closing connection");
  client.stop();

  Serial.println("wait 5 sec...");
  delay(5000);*/
}
