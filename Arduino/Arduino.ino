#include <SPI.h>
#include <Ethernet.h>
#include <SD.h>

File userFile;

byte mac[] = { 0x90, 0xA2, 0xDA, 0x0D, 0x01, 0xD8 };

IPAddress arduino(192,168,0,50);
IPAddress server(192,168,0,100);

EthernetClient client;

/**
* Arduino Ethernet shield: pin 4
* Adafruit SD shields and modules: pin 10
* Sparkfun SD shield: pin 8
*/
const int chipSelect = 4;

String readString;

void setup() {
  Serial.begin(9600);
  
  Serial.println("Initilizing SD card...");
  pinMode(10, OUTPUT);
  
  if (!SD.begin(chipSelect)) {
    Serial.println("initilization failed!");
    return;
  }
  Serial.println("initilization done.");
  
  writeToFile("users.dat", "test:test");
  readFromFile("users.dat");
  
  Ethernet.begin(mac,arduino);
  Serial.println("Connecting...");
  if (client.connect(server, 13337)){
    Serial.println("Connected to Server");
  } else {
    Serial.println("Connecting failed");
  }
}

void writeToFile(char* file, String data) {
  userFile = SD.open(file, FILE_WRITE);
  if (userFile) {
    Serial.print("Writing to file...");
    userFile.println(data);
    userFile.close();
    Serial.println("Wrote.");
  } else {
    Serial.println("error opening file");
  }
}

void readFromFile(char* file) {
  userFile = SD.open(file);
  if(userFile) {
    Serial.println("file:");
    
    while (userFile.available()) {
      Serial.write(userFile.read());
    }
    
    userFile.close();
  } else {
    Serial.println("error opening file");
  }
}

void clientRead() { 
  if (client) {
    readString = "";
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        readString += c;
        if (c == '\n') {
          Serial.print("Message: " + readString);
          break;
        }
      }
    }
  }
}

void loop(){
  while(client.connected()) {
    client.println("ping");
    clientRead();
    delay(1000);
  }
  if (!client.connected()){
    Serial.println("Server disconnected!");
    client.stop();
    client.flush();
    while(true);
  }
}
