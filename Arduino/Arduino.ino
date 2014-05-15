#include <SPI.h>
#include <Ethernet.h>
#include <SD.h>

File userFile;

byte mac[] = { 0x90, 0xA2, 0xDA, 0x0D, 0x01, 0xD8 };

IPAddress arduino(192,168,0,50);
//IPAddress arduino(192,168,0,100);
IPAddress server(195,178,234,229);

EthernetClient client;

/**
 * Arduino Ethernet shield: pin 4
 * Adafruit SD shields and modules: pin 10
 * Sparkfun SD shield: pin 8
 */
const int chipSelect = 4;

String replyString;
String readString = String(100);


void setup() {
  Serial.begin(9600);

  Serial.println("Initilizing SD card...");
  pinMode(10, OUTPUT);

  if (!SD.begin(chipSelect)) {
    Serial.println("initilization failed!");
    return;
  }
  Serial.println("initilization done.");

  readFromFile("users.dat");

  Ethernet.begin(mac,arduino);
  Serial.println("Connecting...");
  connectToServer();
}

void connectToServer() {
  if (client.connect(server, 13338)) {
    Serial.println("Connected to server.");
  } 
  else {
    Serial.println("Failed to connect, retrying...");
    delay(1000); // make the arduino wait 1 seconds untill reconnect.
    connectToServer();
  }
}

void writeToFile(char* file, String data) {
  userFile = SD.open(file, FILE_WRITE);
  if (userFile) {
    Serial.print("Writing to file...");
    userFile.print(data);
    userFile.close();
    Serial.println("Wrote.");
  } 
  else {
    Serial.println("error opening file");
  }
}

void readFromFile(char* file) {
  userFile = SD.open(file);
  String str = "";
  if(userFile) {
    Serial.println("file:");
    String data;
    while (userFile.available()) {
      char c = userFile.read();
      str += c;
      if(c == '\n') {
        Serial.print(str);
        client.print(str);
        str = "";
      }
    }
    userFile.close();
  } 
  else {
    Serial.println("error opening file");
  }
}

int fileLength(char* file) {
  int usersFileLength = 0;
  userFile = SD.open(file);
  if(userFile) {
    while (userFile.available()) {
      char c = userFile.read();
      if(c == '\n') {
        usersFileLength++;
      }
    }
    userFile.close();
  } 
  else {
    Serial.println("error opening file");
  }
  return usersFileLength;
}

String clientRead() {
  readString = "";
  if (client) {
    while (client.connected()) {
      if (client.available() > 0) {
        char c = client.read();
        readString += c;
        if (c == '\n') {
          return readString;
          break;
        }
      }
    }
  }
  return readString;
}

void loop(){
  while(true) {
    while(client.connected()) {
      Serial.println("reading from client");
      replyString = clientRead();
      Serial.print(replyString);
      if (replyString.indexOf("sendusersdata") >= 0) {
        Serial.println("request for saving to users.dat");
        writeToFile("users.dat", clientRead());
        client.println("usersdataok");
      }
      if (replyString.indexOf("arduinodata") >= 0) {
        Serial.println("request for loading from users.dat");
        client.println("arduinodata");
        Serial.println(fileLength("users.dat"));
        client.println(fileLength("users.dat"));
        readFromFile("users.dat");
      }
      /*if (replyString.indexOf("ping") >= 0) {
        client.println("pong");
      }*/
      delay(1000);
    }
    if (!client.connected()){
      Serial.println("Server disconnected, retrying to connect...");
      client.stop();
      client.flush();
      connectToServer();
    }
  }
}

